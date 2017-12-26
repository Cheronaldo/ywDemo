var devSNCodeTest;                                  //测试用SN码
var client;                                         //MQTT连接客户
var protocolVersionDb;                              //数据库协议版本
var protocolVersionDev;                             //设备协议版本
var alarmData;                                      //报警数据
var totalPage;                                      //报警数据总页数
var totalRecords;                                   //报警数据总数
var pageNow = 1;                                    //目前页数
var sendTime = 0;
var id;
$(function() {
    getprotocolVersionDb(devSNCode);
    datapicker();
    //导航栏
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: 1,
        totalPages: 5,
        size:"small",
        alignment:"right",
        onPageClicked: function(e,originalEvent,type,page){
            pageNow = page;
            getAlarmData();
        }
    }
    $('#page-right').bootstrapPaginator(options);
    // getAlarmData();



    //编辑对话框取消点击事件
    $('#cancelSave').click(function(){
        hideModal();
    });

    $('#close-modal').click(function(){
        hideModal();
    });

    $('#search').click(function(){
        getAlarmData();
    });

    $('#saveAlarmInfoBtn').click(function(){
        let handleStatus = $("#handleStatus").val();
        let handleResult = $("#handleResult").val();
        let beginTime = $("#beginDatetime").val();
        let endTime = $("#endDatetime").val();
        let alarmState = $("#alarmState").get(0).checked;
        $.ajax({
            type : "POST",
            url : "/alarm/updateRecord",
            data:{
                "id" : id,
                "handleStatus" : handleStatus,
                "handleResult" : handleResult,
                "snCode" : devSNCode,
                "protocolVersion" : protocolVersionDb,
                "alarmHandled": alarmState,
                "oldDate" : beginTime,
                "newDate" : endTime,
                "page" : pageNow
            },
            dataType : "json",
            success : function(result) {
                if (result.code == "0") {
                    alert(result.msg);
                    totalPage = result.total;
                    totalRecords = result.records;
                    alarmData = result.rows;
                    displayData();
                    hideModal();
                } else {
                    alert(result.msg);
                }
            }
        });
    });
});

//日期选择器
function datapicker() {
    $('#beginDatetime').datetimepicker({
    });
    $("#openBeginTime").click(function(){
        $('#beginDatetime').datetimepicker("show");
    });
    $('#endDatetime').datetimepicker({
    });
    $("#openEndTime").click(function(){
        $('#endDatetime').datetimepicker("show");
    });
}

//拿到数据库里的协议版本
function getprotocolVersionDb(devSNCode){
    $.ajax({
        url: "/data/getProtocolVersion",
        cache: false,
        dataType:'json',
        data : {
            snCode: devSNCode
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                alert(result.msg);
                protocolVersionDb = result.data;
                initMqtt();
            }
            else {
                alert(result.msg);
            }
        },
        complete: function () {

        },
        error: function (data) {
            console.log(data);
            console.info("error: " + data.responseText);
        }
    });
}

//新建MQTT连接，并发去第一次message
function initMqtt() {
    // Create a client instance
    client = new Paho.MQTT.Client('47.94.242.70', 61623, "yiwei");

    // set callback handlers
    client.onConnectionLost = onConnectionLost;
    client.onMessageArrived = onMessageArrived;

    // connect the client
    client.connect({ userName:'admin', password:'password', onSuccess: onConnect });
}


function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
    // client.subscribe("/China/HuBei/#");
    // c_sub_topic = "/China/HuBei/"+devSNCode+"/#";
    c_sub_topic = "/"+devSNCode+"/#";
    client.subscribe(c_sub_topic);
    message_payloadString = "1";
    message = new Paho.MQTT.Message(message_payloadString);
    // devSNCodeTest = "HMITest001";       //测试用
    // message_destinationName = "/China/HuBei/" + devSNCode + "/DevRecv/cfg/req"
    message_destinationName = "/" + devSNCode + "/DevRecv/cfg/req"
    message.destinationName = message_destinationName;
    client.send(message);
}

// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    }
}

// called when a message arrives
function onMessageArrived(message) {
    console.log(message.destinationName);
    switch(message.destinationName){
        // case "China/HuBei/HMITest001/cgf/req":
        //
        //     break;
        // case "China/HuBei/" + devSNCode + "/DevSend/cfg/ack":
        case devSNCode + "/DevSend/cfg/ack":
            if(sendTime == 0){
                analProtocol(message.payloadString);
                sendTime++;
            }
            break;
    }
}

function analProtocol(protocol) {
    let tempProtocol = new Array();
    tempProtocol = protocol.split("_");
    protocolVersionDev = tempProtocol.shift();
    // protocolVersionDev = protocolVersionDb;       //测试用
    if(protocolVersionDev != protocolVersionDb){
        if(confirm("协议不匹配，是否返回至协议同步页面？")){
            window.location.href = "/device?isAsk=1";
        }
    }
    else {
        getAlarmData();
        pageNow = 1;
    }
}


//拿到报警数据
function getAlarmData() {
    let beginTime = $("#beginDatetime").val();
    let endTime = $("#endDatetime").val();
    let alarmState = $("#alarmState").get(0).checked;
    protocolVersionDb = "ywv1.1";           //测试用
    $.ajax({
        type : "POST",
        url : "/alarm/getAlarmList",
        data:{
            "snCode" : devSNCode,
            "protocolVersion" : protocolVersionDb,
            "alarmHandled": alarmState,
            "oldDate" : beginTime,
            "newDate" : endTime,
            "page" : pageNow
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                alert(result.msg);
                totalPage = result.total;
                totalRecords = result.records;
                alarmData = result.rows;
                displayData();
            } else {
                alert(result.msg);
            }
        }
    });
}

function displayData(){
    var listStr = "";
    for(var i=0;i<alarmData.length;i++){
        listStr += "<tr>";
        listStr +=  "<td width='15%'>" + alarmData[i].alarmTime + "</td>" +
            "<td width='15%'>" + alarmData[i].handleTime + "</td>" +
            "<td width='5%' class='dataName'>" + alarmData[i].dataName + "</td>" +
            "<td width='5%'>" + alarmData[i].actualValue + "</td>" +
            "<td width='20%'>" + alarmData[i].alarmInfo + "</td>" +
            "<td width='10%' class='handleStatus'>" + alarmData[i].handleStatus + "</td>" +
            "<td width='10%' class='handleResult'>" + alarmData[i].handleResult + "</td>" +
            "<td width='10%'><div id='" + alarmData[i].id + "' class='alarmBtn' style='display: " +
            chechResult(alarmData[i].handleResult) + "'><span class='glyphicon glyphicon-pencil'></span>修改</div></td>";
        listStr += "</tr>";
    }
    $(".alarm_load_list").empty().append(listStr);

    $('.alarmBtn').click(function(){
        id = this.id;
        let dataName = $(this).parent().parent().children(".dataName").html();
        let handleStatus = $(this).parent().parent().children(".handleStatus").html();
        let handleResult = $(this).parent().parent().children(".handleResult").html();
        $("#dataName").val(dataName);
        $("#handleStatus").val(handleStatus);
        $("#handleResult").val(handleResult);
        showModal();
    });
}

function hideModal(){
    $("#addModalAlarm").removeClass("showModal");
    $("#addModalAlarm").addClass("hide");
    $("body").css("overflow","visible");
}

function showModal(){
    $("#addModalAlarm").removeClass("hide");
    $("#addModalAlarm").addClass("showModal");
    $("body").css("overflow","hidden");
}

function chechResult(result) {
    if(result == "无") return "block";
    else return "none";
}