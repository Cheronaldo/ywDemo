var client;                                         //MQTT连接客户
var protocolVersionDb;                              //数据库协议版本
var protocolVersionDev;                             //设备协议版本
var RWData;                                         //报警数据
var sendTime = 0;
var readTime = 0;
var writeTime = 0;
var domNow;
var typeNow;
$(function() {
    getprotocolVersionDb(devSNCode);
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
                // alert(result.msg);
                protocolVersionDb = result.data;
                initMqtt();
            }
            else {
                // alert(result.msg);
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
    console.log(message.payloadString);
    switch(message.destinationName){
        case devSNCode + "/DevSend/cfg/ack":
            if(sendTime == 0){
                analProtocol(message.payloadString);
                sendTime++;
            }
            break;
        case devSNCode + "/DevSend/RdOnce/ack":
            if(readTime == 0){
                var answer = message.payloadString.split("_")[2];
                if(typeNow === "2") domNow.val(answer);
                else if(typeNow === "3") domNow.val(answer);
                else if (typeNow === "1") {
                    for (var i = 0; i < domNow.length; i++) {
                        if(domNow[i].value == answer){
                            domNow[i].checked = true;
                        }
                    }
                }
                readTime++;
            }
            break;
        case devSNCode + "/DevSend/WwOnce/ack":
            if(writeTime == 0){
                var answer = message.payloadString.split("_")[1];
                if(answer === "1") alert("写入成功");
                else alert("写入失败");
                writeTime++;
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
        getRWListData();
    }
}

function getRWListData() {
    $.ajax({
        type : "POST",
        url : "/protocol/data/readWrite",
        data:{
            "protocolVersion" : protocolVersionDb
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                // alert(result.msg);
                RWData = result.data;
                displayData();
            } else {
                // alert(result.msg);
            }
        }
    });
}

function displayData() {
    var str = "";
    for (var i = 0; i < RWData.length; i++) {
        if (RWData[i].dataType === "1") {
            str += "<div class='col-md-5 panel' id='type" + RWData[i].dataName + "=" + RWData[i].dataType + "'><div class='pull-left img'>" +
                "<img src='/assets/" + translateImg(RWData[i].dataName) +
                "' th:src='@{/assets/" + translateImg(RWData[i].dataName) + "}' " +
                "'  alt='图片暂无' />" + "</div><div class='pull-left dataInfo'><div class='attrName'>" +
                RWData[i].dataName + "<span class='unit'>（单位：" + RWData[i].dataUnit + "）</span></div>" +
                "<div class='infoInput attrRadio clearfix'><div class='radio-inline'><span><input class='dataInput' type='radio' value='1' name='" +
                RWData[i].dataName + "'></span><span>开启</span></div><div class='radio-inline'><span><input class='dataInput' type='radio' value='0' name='" +
                RWData[i].dataName + "'></span><span>关闭</span></div></div></div>" + "<div class='pull-right btn-group'>" +
                "<div class='btn-read " + isHide(RWData[i].isRead) + "'><button class='btn btn-primary readData' id='RDoffset=" + RWData[i].offsetNumber + "'><span class='glyphicon glyphicon-log-out'></span>读取</button></div>" +
                "<div class='btn-write " + isHide(RWData[i].isWrite) + "'><button class='btn btn-primary writeData' id='WToffset=" + RWData[i].offsetNumber + "'><span class='glyphicon glyphicon-log-in'></span>写入</button></div></div></div>";
        }
        else {
            str += "<div class='col-md-5 panel' id='type" + RWData[i].dataName + "=" + RWData[i].dataType + "'><div class='pull-left img'>" +
                "<img src='/assets/" + translateImg(RWData[i].dataName) +
                "' th:src='@{/assets/" + translateImg(RWData[i].dataName) + "}' " +
                "'  alt='图片暂无' />" + "</div><div class='pull-left dataInfo'><div class='attrName'>" +
                RWData[i].dataName + "<span class='unit'>（单位：" + RWData[i].dataUnit + "）</span></div>" +
                "<div class='infoInput'><input type='text' class='dataInput inputText'></div>" + "</div><div class='pull-right btn-group'>" +
                "<div class='btn-read " + isHide(RWData[i].isRead) + "'><button class='btn btn-primary readData' id='RDoffset=" + RWData[i].offsetNumber + "'><span class='glyphicon glyphicon-log-out'></span>读取</button></div>" +
                "<div class='btn-write " + isHide(RWData[i].isWrite) + "'><button class='btn btn-primary writeData' id='WToffset=" + RWData[i].offsetNumber + "'><span class='glyphicon glyphicon-log-in'></span>写入</button></div></div></div>";
        }
    }
    $(".RWList").empty().append(str);

    $('.readData').click(function(){
        typeNow = $(this).parents(".panel").attr("id").split("=")[1];
        readTime = 0;
        var offset = this.id.split("=")[1];
        message_payloadString = protocolVersionDev + "_" + offset;
        var mes = new Paho.MQTT.Message(message_payloadString);
        mes.destinationName = "/" + devSNCode + "/DevRecv/RdOnce/req";
        domNow = $(this).parents(".panel").find(".dataInput");
        client.send(mes);
    });

    $('.writeData').click(function(){
        typeNow = $(this).parents(".panel").attr("id").split("=")[1];
        writeTime = 0;
        var input;
        var offset = this.id.split("=")[1];
        if (typeNow === "2") var input = $(this).parents(".panel").find(".dataInput").val();
        else if (typeNow === "3") var input = $(this).parents(".panel").find(".dataInput").val();
        else if(typeNow === "1") {
            var doms = $(this).parents(".panel").find(".dataInput");
            for (var i = 0; i < doms.length; i++) {
                if(doms[i].checked) {
                    input = doms[i].value;
                }
            }
        }
        message_payloadString = protocolVersionDev + "_" + offset + "_" + input;
        var mes = new Paho.MQTT.Message(message_payloadString);
        mes.destinationName = "/" + devSNCode + "/DevRecv/WrOnce/req";
        client.send(mes);
    });
}
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

function isHide(offset) {
    if (offset === "1") return "";
    else return "hide";
}