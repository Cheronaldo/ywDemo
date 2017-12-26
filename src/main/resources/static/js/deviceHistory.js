var devSNCodeTest;                                  //测试用SN码
var client;                                         //MQTT连接客户
var protocolVersionDb;                              //数据库协议版本
var protocolVersionDev;                             //设备协议版本
var headData;                                       //协议内容（历史数据栏名）
var historyData;                                    //历史数据
var totalPage;                                      //历史数据总页数
var totalRecords;                                   //历史数据总数
var pageNow;                                        //目前页数
var sendTime = 0;
$(function(){
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
                            getHistoryHead();
                                  }
    }
    $('#page-right').bootstrapPaginator(options);


    //点击搜索按钮
    $('#search').click(function(){
        getHistoryHead();
    });
    //点击导出数据按钮
    $('#exportData').click(function(){

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
    // message_payloadString = "HMITest002_ywv1.1_123456_1";
    message = new Paho.MQTT.Message(message_payloadString);
    // devSNCodeTest = "HMITest001";       //测试用
    // message_destinationName = "/China/HuBei/" + devSNCode + "/DevRecv/cfg/req"
    message_destinationName = "/"+devSNCode + "/DevRecv/cfg/req"
    // message_destinationName = "/sys/datasave";
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
    // console.log(message.destinationName);
    // console.log(message.payloadString);
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

//判断协议是否一致，一致则显示页面，不一致则跳转
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
        getHistoryHead();
        pageNow = 1;
    }
}

//拿到协议内容（历史数据栏名）
function getHistoryHead() {
    $.ajax({
        type : "POST",
        url : "/data/getProtocol/history",
        data:{
            "protocolVersion" : protocolVersionDb
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                alert(result.msg);
                headData = result.data;
                getHistoryData();
            } else {
                alert(result.msg);
            }
        }
    });
}

//拿到历史数据
function getHistoryData() {
    let beginTime = $("#beginDatetime").val();
    let endTime = $("#endDatetime").val();
    $.ajax({
        type : "POST",
        url : "/data/getData/all",
        data:{
            "snCode" : devSNCode,
            "protocolVersion" : protocolVersionDb,
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
                historyData = result.data;
                displayData();
            } else {
                alert(result.msg);
            }
        }
    });
}

//渲染数据
function displayData() {
    displayHead(headData);
    displayHistoryData(historyData);
}
function displayHead(heads){
    var headStr = '<tr><th width="10%">采集时间</th>';
    for(var i=0;i<heads.length;i++){
        var head = heads[i];
        headStr += '<th width="10%">'+head.dataName+unit(head.dataName)+'</th>';
    }
    headStr += '</tr>';
    $(".history_load_head").empty().append(headStr);
}

function displayHistoryData(list){
    var listStr = "";
    for(var i=0;i<list.length;i++){
        listStr += '<tr>';
        listStr += '<td>'+list[i].dataTime+'</td>';
        let datalist = list[i].deviceData.split("_");
        for (var j = 0; j < datalist.length; j++) {
            listStr += '<td>'+datalist[j]+'</td>';
        }
        listStr += '</tr>';
    }
    $(".history_load_list").empty().append(listStr);
}
//添加数据单位
function unit(dataName){
    switch (dataName){
        case "温度":
            return "°C";
        case "浓度":
        case "湿度":
            return "%";
        case "电场":
            return "V/m";
        default:
            return "";
    }
}