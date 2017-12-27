var devSNCodeTest;                                  //测试用SN码
var client;                                         //MQTT连接客户
var protocolVersionDb;                              //数据库协议版本
var protocolVersionDev;                             //设备协议版本
var thresholdData;                                  //阈值数据
var totalPage;                                      //阈值数据总页数
var totalRecords;                                   //阈值数据总数
var pageNow = 1;                                    //目前页数
var sendTime = 0;
var id;
var thresholdFlag = false;
var offsetNumber;

$(function() {
    getprotocolVersionDb(devSNCode);

    // 导航栏
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: 1,
        totalPages: 5,
        size:"small",
        alignment:"right",
        onPageClicked: function(e,originalEvent,type,page){
            pageNow = page;
            getThresholdData();
        }
    }
    $('#page-right').bootstrapPaginator(options);
    // getThresholdData();

    //编辑对话框取消点击事件
    $('#cancelSave').click(function(){
        hideModal();
    });

    $('#close-modal').click(function(){
        hideModal();
    });

    $('#saveThresholdInfoBtn').click(function(){
        checkInputRight();
        if(thresholdFlag) {
            let downThreshold = $("#downThreshold").val();
            let upThreshold = $("#upThreshold").val();
            $.ajax({
                type: "POST",
                url: "/threshold/update",
                data: {
                    "id": id,
                    "downThreshold": downThreshold,
                    "upThreshold": upThreshold,
                    "page": pageNow
                },
                dataType: "json",
                success: function (result) {
                    if (result.code == "0") {
                        alert(result.msg);
                        totalPage = result.total;
                        totalRecords = result.records;
                        thresholdData = result.data;
                        displayData();
                        hideModal();
                        sendDataToDev();
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    });
});

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
    message_destinationName = "/" + devSNCode + "/DevRecv/cfg/req";
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
        getThresholdData();
        pageNow = 1;
    }
}

//拿到报警数据
function getThresholdData() {
    // protocolVersionDb = "ywv1.1";           //测试用
    $.ajax({
        type : "POST",
        url : "/threshold/getList",
        data:{
            "snCode" : devSNCode,
            "protocolVersion" : protocolVersionDb,
            "page" : pageNow
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                alert(result.msg);
                totalPage = result.total;
                totalRecords = result.records;
                thresholdData = result.data;
                displayData();
            } else {
                alert(result.msg);
            }
        }
    });
}

function displayData(){
    var listStr = "";
    for(var i=0;i<thresholdData.length;i++){
        listStr += "<tr>";
        listStr +=  "<td width='5%' class='offsetNumber'>" + thresholdData[i].offsetNumber + "</td>" +
            "<td width='10%' class='dataName'>" + thresholdData[i].dataName + "</td>" +
            "<td width='10%' class='downThreshold'>" + thresholdData[i].downThreshold + "</td>" +
            "<td width='10%' class='upThreshold'>" + thresholdData[i].upThreshold + "</td>" +
            "<td width='5%'><div id='" + thresholdData[i].id + "' class='thresholdBtn'><span class='glyphicon glyphicon-pencil'></span>修改</div></td>";
        listStr += "</tr>";
    }
    $(".threshold_load_list").empty().append(listStr);
    $('.thresholdBtn').click(function(){
        id = this.id;
        offsetNumber = $(this).parent().parent().children(".offsetNumber").html();
        let dataName = $(this).parent().parent().children(".dataName").html();
        let downThreshold = $(this).parent().parent().children(".downThreshold").html();
        let upThreshold = $(this).parent().parent().children(".upThreshold").html();
        $("#dataName").val(dataName);
        $("#downThreshold").val(downThreshold);
        $("#upThreshold").val(upThreshold);
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

function checkInputRight() {
    let downThreshold = $("#downThreshold").val();
    let upThreshold = $("#upThreshold").val();
    if(!downThreshold || !upThreshold) {
        alert("输入不能为空！");
        thresholdFlag = false;
    }
    else{
        let reg = /^\-?([1-9]\d*(\.\d{1})?|0\.\d{1})$|^0$/;
        if(!reg.test(downThreshold) || !reg.test(upThreshold)) {
            alert("请输入整数或小数点后一位的小数");
            thresholdFlag = false;
        }
        else thresholdFlag = true;
    }
}

function sendDataToDev() {
    var payload = devSNCode + "_" + protocolVersionDb + "_" + offsetNumber + "_";
    for(let i in thresholdData){
        if(thresholdData[i].offsetNumber == offsetNumber){
            let str = thresholdData[i].downThreshold + "_" + thresholdData[i].upThreshold + "_";
            payload += str;
        }
    }
    payload += "jiaoyanhe";
    var destinationName = "/" + devSNCode + "/DevRecv/ThresoldUpdata";
    var message = new Paho.MQTT.Message(payload);
    message.destinationName = destinationName;
    client.send(message);
}