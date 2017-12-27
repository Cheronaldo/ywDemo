var protocolVersionDb;                                      //数据库返回的协议版本
var message_payloadString;                                  //页面发出的payload  （设备收到的payload）
var message_destinationName;                                //页面发出的destination（设备收到的topic）
var c_sub_topic;                                            //页面订阅的主题
var protocolVersionDev;                                     //从设备拿到的协议版本
var client;                                                 //Mqtt客户端
var offsetNumber;                                           //当前按钮对应属性偏移值
var dataUnit;                                               //当前按钮对应属性的单位
var timeList;                                               //历史数据对应时间数组
var dataList;                                               //历史数据数组
var singData;                                               //从数据库取到的历史数据
var option;                                                 //表格数据
var devData = new Array();                                  //从设备拿到的数据
var protocolFormat = new Array();                           //协议格式
var protocolData = new Array();                             //协议内容
var dataCount = 0;                                          //数据发送次数  （用于保护协议配置）
var dataReceive = 0;                                        //数据显示时数据发送次数  （用于收到3次数据页面数据刷新）
var protocolVersionFlag = false;                            //协议版本标志位
var protoSaveFlag = false;                                  //协议储存标志位
var displayDataFlag = false;                                //页面数据渲染标志位
var getprotocolFlag = false;                                //是否已从数据库拿到协议版本

var getUrl = window.parent.location.search;
var userName = getUrl.substring(10);
Date.prototype.Format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
$(function() {

    getprotocolVersionDb(devSNCode);

    $(window).unload(function(){
        closeData();
    });
    datapicker();


});

function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
    // client.subscribe("/China/HuBei/#");
    // c_sub_topic = "/China/HuBei/"+devSNCode+"/#";
    c_sub_topic = "/"+devSNCode+"/#";
    client.subscribe(c_sub_topic);
    message_payloadString = "1";
    message = new Paho.MQTT.Message(message_payloadString);
    devSNCodeTest = "HMITest001";       //测试用
    // message_destinationName = "/China/HuBei/" + devSNCode + "/DevRecv/runtime/switch"
    message_destinationName = "/" + devSNCode + "/DevRecv/runtime/switch"
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
    switch(message.destinationName){
        // case "China/HuBei/"+devSNCode+"/DevSend/runtime/on":
        case devSNCode+"/DevSend/runtime/on":
            if (dataCount == 0) {
                console.log(message.payloadString);
                analPayload(message.payloadString);
                configProtocol();
                dataCount++;
            }
            if (displayDataFlag) {
                if (dataReceive < 2) dataReceive++;
                else{
                    dataReceive = 0;
                    analPayload(message.payloadString);
                    displayData();
                    refreshTime();
                }
            }
            break;
        // case "China/HuBei/" + devSNCode + "/DevSend/cfg/ack":
        case devSNCode + "/DevSend/cfg/ack":
            console.log(message.payloadString);
            // analProtocol(message.payloadString);         //为手动适配注销
            saveProtocol();
            if (protoSaveFlag) {
                getProtocol();
            }
            else{
                closeData();
            }
            break;
    }
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
                getprotocolFlag = true;
                initMqtt();
                console.log(protocolVersionDb);
            }
            else {
                alert(result.msg);
                closeData();
            }
        },
        complete: function () {

        },
        error: function (data) {
            console.log(data);
            console.info("error: " + data.responseText);
            closeData();
        }
    });
}

//分析设备发过来的数据
function analPayload(payload){
    let payloadData = new Array();
    payloadData = payload.split("_");
    protocolVersionDev = payloadData[0];
    payloadData.shift();
    payloadData.pop();
    devData = payloadData;
    // console.log(devData);
    // protocolVersionDev = protocolVersionDb;         //测试用
    if (protocolVersionDev == protocolVersionDb) protocolVersionFlag = true;
    else{
        protocolVersionFlag = false;
        if(confirm("协议不匹配，是否返回至协议同步页面？")){
            window.location.href = "/device?isAsk=1";
        }
    }
}

// //协议适配（自动版）
// function configProtocol(){
//     //版本号一致
//     if (protocolVersionFlag) {
//         message_destinationName = "/China/HuBei/" + devSNCode + "/runtime/ok";
//         let mes = new Paho.MQTT.Message("1");
//         mes.destinationName = message_destinationName;
//         client.send(mes);
//         getProtocol();
//     }
//     //版本号不一致
//     else{
//         message_destinationName = "/China/HuBei/" + devSNCode + "/cfg/req";
//         let mes = new Paho.MQTT.Message("1");
//         mes.destinationName = message_destinationName;
//         client.send(mes);
//     }
// }

//协议适配（手动版）
function configProtocol(){
    //版本号一致
    if (protocolVersionFlag) getProtocol();
    //版本号不一致
    // else{
    //     if(confirm("协议不匹配，是否返回至协议同步页面？")){
    //         window.location.href = "/device?isAsk=1";
    //     }
    // }
}

// //页面关闭时告知设备停止发送数据（自动适配协议版）
// function closeData(){
//     protocolVersionFlag = false;
//     protoSaveFlag = false;
//     displayDataFlag = false;
//     dataCount = 0;
//     dataReceive = 0;
//     message_destinationName = "/China/HuBei/" + devSNCode + "/runtime/switch";
//     let mes = new Paho.MQTT.Message("0");
//     mes.destinationName = message_destinationName;
//     if (getprotocolFlag) client.send(mes);
//     getprotocolFlag = false;
// }

//页面关闭时告知设备停止发送数据（手动适配协议版）
function closeData(){
    protocolVersionFlag = false;
    protoSaveFlag = false;
    displayDataFlag = false;
    dataCount = 0;
    dataReceive = 0;
    getprotocolFlag = false;
}

//将数据渲染到页面
function displayData(){
    for(let item in devData){
        $("#data" + item).html(parseFloat(devData[item]).toFixed(2));
    }
}

//分析协议内容
function analProtocol(protocol){
    let tempProtocol = new Array();
    tempProtocol = protocol.split("_");
    tempProtocol.shift();
    tempProtocol.pop();
    protocolFormat = tempProtocol;
}

//储存协议
function saveProtocol(){
    var tempProtocol = protocolFormat[0];
    for (var i = 1; i < protocolFormat.length - 1; i++) {
        tempProtocol = tempProtocol + "_" + protocolFormat[i];
    }
    tempProtocol = "湿度_浓度_温度";
    $.ajax({
        url: "/data/protocolAdapt",
        cache: false,
        dataType:'json',
        data : {
            userName: userName,
            snCode: devSNCode,
            protocolVersion: protocolVersionDev,
            protocolContent: tempProtocol
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                alert(result.msg);
                protoSaveFlag = true;
                protocolVersionFlag = true;
            }
            else {
                alert(result.msg);
                protoSaveFlag = false;
            }
        },
        complete: function () {

        },
        error: function (data) {
            console.log(data);
            console.info("error: " + data.responseText);
            closeData();
        }
    });
}

//查询协议内容
function getProtocol(){
    $.ajax({
        url: "/data/getProtocol/realTime",
        cache: false,
        dataType:'json',
        data : {
            userName: userName,
            snCode: devSNCode,
            // snCode: "1510311999826615905",         //测试用
            protocolVersion: protocolVersionDev
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                alert(result.msg);
                protocolData = result.data;
                displayProtocol();
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
            closeData();
        }
    });
}

//显示协议（页面骨架）
function displayProtocol(){
    for(let item in protocolData){
        let strProto = $("<div class='protocol'><div class='dataIcon'>" +
                            "<img src='/assets/" + translateImg(protocolData[item].dataName) +
                            "' th:src='@{/assets/" + translateImg(protocolData[item].dataName) + "}' " +
                            "' alt='图片暂无' /></div>" +
                            "<div class='qualitydiv'><span class='quality'>" + protocolData[item].dataName + "</span></br></br>" +
                            "<span class='date'>" + new Date().Format("yyyy-MM-dd hh:mm:ss") + "</span></div>" +
                            "<div class='datadiv'><span id='data" + item + "' class='dataspan'>-</span>" +
                            "<span id='uint' class='unit'>" + protocolData[item].dataUnit +
                            "</span></div><div class='btndiv'><button class='tendencyBtn' id='offset=" + protocolData[item].offsetNumber +
                            "&dataName=" + protocolData[item].dataUnit +
                            "'><span class='icon-btn-chart'></span>趋势图</button>" +
                        "</div></div>");
        $("#equip-data").append(strProto);
    }

    $(".tendencyBtn").click(function(){
        offsetNumber = this.id.substr(7,1);
        dataUnit = this.id.substr(18,2);
        $("#beginDatetime").val("");
        $("#endDatetime").val("");
        getHistorySingleData();
    });

    $('#close-modal').click(function(){
        $("#addModal").removeClass("showModal");
        $("#addModal").addClass("hide");
        $("body").css("overflow","visible");
    });

    $('#search').click(function(){
        getHistorySingleData();
    });
    displayDataFlag = true;
}

//翻译数据图片路径
function translateImg(dataName){
    switch (dataName){
        case "温度":
            return "images/temperature.png";
        case "浓度":
        case "湿度":
            return "images/humidity.png";
        case "电场":
            return "images/current.png";
        default:
            return "images/WindSpeed.png";
    }
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

//刷新时间
function refreshTime() {
    $(".date").html(new Date().Format("yyyy-MM-dd hh:mm:ss"));
}

//将历史数据添加到数组中
function sortHistoryData(){
    timeList = [];
    dataList = [];
    for (var i = 0; i < singData.length; i++) {
        timeList.push(singData[i].dataTime.substr(11,5));
        dataList.push(singData[i].deviceData);
    }
}

//表格渲染
function initEcharts(){
    var echart = echarts.init(document.getElementById('echart'));
    option = {

        // Make gradient line here
        visualMap: {
            show: false,
            type: 'continuous',
            seriesIndex: 0,
            min: 0,
            max: 400
        },


        title: {
            subtext: '单位 / '+dataUnit,
            left: 'left'
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            data: timeList
        },
        yAxis: {
            splitLine: {show: false}
        },
        grid: {
            bottom: '10%'
        },
        series: [{
            type: 'line',
            showSymbol: false,
            data: dataList
        }]
    };

    echart.setOption(option);
}

//
function getHistorySingleData() {
    let beginTime = $("#beginDatetime").val();
    let endTime = $("#endDatetime").val();
    let protocolVersiontest = protocolVersionDb;						//测试用
    $.ajax({
        url: "/data/getData/single",
        cache: false,
        dataType:'json',
        data : {
            // protocolVersion: protocolVersionDb,
            protocolVersion: protocolVersiontest,
            snCode: devSNCode,
            offsetNumber: offsetNumber,
            oldDate: beginTime,
            newDate: endTime
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                alert(result.msg);
                singData = result.data;
                $("#addModal").removeClass("hide");
                $("#addModal").addClass("showModal");
                $("body").css("overflow","hidden");
                sortHistoryData();
                initEcharts();
                $("#modal-title").html(dataName);
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