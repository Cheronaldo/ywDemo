var getUrl = window.parent.location.search;
var userName = getUrl.substring(10);
var inputFlag = false;
var map;
var client;
var devSNCode;
var protocolVersionDev;
var protocolContent;
var sendTime = 0;
var isAsk = 0;
var isSynBtn = false;
var deviceLng;
var deviceLat;
$(function() {

    initMap();
    //getData();

    $(window).unload(function(){
        inputFlag = false;
        sendTime = 0;
    });

     //4.获取设备列表信息
    $("#query_").click(function () {

        //4.1 获取已选择的地址信息
        map.clearOverlays();//清空原来的标注
        var keyword = document.getElementById("suggestId").value;   

        localSearch.setSearchCompleteCallback(function (searchResult) {         
            var point1 = pp;         
            map.centerAndZoom(point1, 13);

            var point = new BMap.Point(point1.lng, point1.lat);

            var url = "http://image.tupian114.com/20140419/09274112.png";
            //var url= "http://lbsyun.baidu.com/jsdemo/img/fox.gif";
            var myIcon = new BMap.Icon(url, new BMap.Size(40,30));
            var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注
            map.addOverlay(marker);



            //4.2 显示经纬度信息
            var opts = {
                width:100,
                height:120,
                title:"设备信息",
            };

            var content = "地址："+document.getElementById("suggestId").value + "<br/><br/>经度：" + point1.lng + "<br/>纬度：" + point1.lat;
            //var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            var infoWindow = new BMap.InfoWindow(content,opts);
            marker.addEventListener("click", function () { map.openInfoWindow(infoWindow,point); });

            //4.3 将sn码 校验码 地址 及 经纬度 下发
            var snCode = document.getElementById("snCode").value;
            var siteType = document.getElementById("xcType").value;
            var siteName = document.getElementById("xcName").value;
            var deviceAddress = keyword;
            deviceLng = point1.lng;
            deviceLat = point1.lat;

           //设备注册

            if(isAsk){
                initMqtt();
                isSynBtn = false;
            }
            else devregister();
            // $.ajax({
            //         type : "POST",
            //         url : "/device/site/register",
            //         data : {
            //             "snCode" :  snCode,
            //             "userName": userName,
            //             "siteType" : siteType,
            //             "siteName" : siteName,
            //             "deviceAddress" : deviceAddress,
            //             "deviceLongitude" : deviceLng,
            //             "deviceLatitude": deviceLat,
            //             "siteIcon": "/123456",
            //             "isAdapt": isAsk,
            //             "protocolVersion": protocolVersionDev,
            //             "protocolContent": protocolContent
            //         },
            //         dataType : "json",
            //         success : function(data) {
            //             if (data.code == 0) {
            //                 alert('设备注册成功！');
            //                 document.getElementById("snCode").value = "";
            //                 document.getElementById("suggestId").value = "";
            //                 document.getElementById("jiaoyan").value = "";
            //                 $("#query_").attr("disabled","disabled");
            //                 $("#modify_").removeAttr("disabled");
            //                 $("#suggestId").removeAttr("disabled");
            //                 isSynBtn = false;
            //                 isAsk = 0;
            //             } else if(data.code == 1) {
            //                 alert('设备注册失败');
            //             }
            //             $("#query_").attr("disabled","disabled");
            //             $("#modify_").removeAttr("disabled");
            //             $("#cancel_").removeAttr("disabled");
            //         }
            //     });
        });
        localSearch.search(keyword);
    });

    //5.SN码和校验码输入完成后校验
    $("#snCode").change(function(){
        checkInputRight();
        if (inputFlag) {
            checkResgisState();
        }
    });

    $("#jiaoyan").change(function(){
        checkInputRight();
        if (inputFlag) {
            checkResgisState();
        }
    });

    //12.二级用户注册设备
    $("#addRegister").click(function(){
        var snCode = document.getElementById("snCode").value;
        $.ajax({
            type : "POST",
            url : "/device/addRegister",
            data : {
                "snCode" :  snCode
            },
            dataType : "json",
            success : function(data) {
                if (data.code == 0) {
                    alert(data.msg);
                } else if(data.code == 1) {
                    alert(data.msg);
                }
            }
        });
    });

    //8.设备修改
    $("#modify_").click(function(){
        map.clearOverlays();//清空原来的标注
        var keyword = document.getElementById("suggestId").value;   

        localSearch.setSearchCompleteCallback(function (searchResult) {         
            var point1 = pp;         
            map.centerAndZoom(point1, 13);

            var point = new BMap.Point(point1.lng, point1.lat);

            var url = "http://image.tupian114.com/20140419/09274112.png";
            //var url= "http://lbsyun.baidu.com/jsdemo/img/fox.gif";
            var myIcon = new BMap.Icon(url, new BMap.Size(40,30));
            var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注
            map.addOverlay(marker);



            //4.2 显示经纬度信息
            var opts = {
                width:100,
                height:120,
                title:"设备信息",
            };

            var content = "地址："+document.getElementById("suggestId").value + "<br/><br/>经度：" + point1.lng + "<br/>纬度：" + point1.lat;
            //var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
            var infoWindow = new BMap.InfoWindow(content,opts);
            marker.addEventListener("click", function () { map.openInfoWindow(infoWindow,point); });

            //4.3 将sn码 校验码 地址 及 经纬度 下发
            var snCode = document.getElementById("snCode").value;
            var siteType = document.getElementById("xcType").value;
            var siteName = document.getElementById("xcName").value;
            var deviceAddress = keyword;
            var deviceLng = point1.lng;
            var deviceLat = point1.lat;

           
            $.ajax({
                    type : "POST",
                    url : "/device/site/update",
                    data : {
                        "snCode" :  snCode,
                        "userName": userName,
                        "siteType" : siteType,
                        "siteName" : siteName,
                        "deviceAddress" : deviceAddress,
                        "deviceLongitude" : deviceLng,
                        "deviceLatitude": deviceLat,
                        "siteIcon": "/123456"
                    },
                    dataType : "json",
                    success : function(data) {
                        if (data.code == 0) {
                            alert('设备修改成功！');
                            $("#modify_").attr('disabled',true);//避免重复注册
                            document.getElementById("snCode").value = "";
                            document.getElementById("suggestId").value = "";
                            document.getElementById("jiaoyan").value = "";
                            // <!-- 传值成功 如何到index.html中接收？ -->
                        } else if(data.code == 1) {
                            alert('设备修改失败');
                        }
                        $("#query_").attr("disabled","disabled");
                        $("#modify_").attr("disabled","disabled");
                        $("#cancel_").removeAttr("disabled");
                    }
                });
        });
        localSearch.search(keyword);
    });

    //9.设备注销
    $("#cancel_").click(function(){
        var snCode = document.getElementById("snCode").value;
        $.ajax({
            type : "POST",
            url : "/device/unbind",
            data : {
                "snCode" :  snCode,
                "userName": userName
            },
            dataType : "json",
            success : function(data) {
                if (data.code == 0) {
                    alert('设备注销成功！');
                    $("#cancel_").attr('disabled',true);
                    document.getElementById("snCode").value = "";
                    document.getElementById("suggestId").value = "";
                    document.getElementById("jiaoyan").value = "";
                } else if(data.code == 1) {
                    alert('设备注销失败');
                }
                $("#query_").removeAttr("disabled");
                $("#modify_").attr("disabled","disabled");
                $("#cancel_").attr("disabled","disabled");
            }
        });
    });

    //10.协议同步
    $("#protoSyn").click(function(){
        initMqtt();
        isSynBtn = true;
    });
});




function initMap() {


    //1.创建地图
    function G(id) {
        return document.getElementById(id);
    }

    map = new BMap.Map("container");
    map.centerAndZoom("武汉",12);                   // 初始化地图,设置城市和地图级别。

    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件

    localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小
//以上部分代码已经测试通过


    //2.下拉框事件监测
    var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
        {"input" : "suggestId"
            ,"location" : map
        });

    ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件


        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;

        value = "";
        if (e.toitem.index > -1) {
            _value = e.toitem.value;
            value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        }
        str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
        G("searchResultPanel").innerHTML = str;
    });


    ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
        G("searchResultPanel").innerHTML ="onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue;
       ///
        //pp = local.getResults().getPoi(0).point;
        setPlace();
    });

    //以下为添加部分


    //
    // var geoc = new BMap.Geocoder();

    map.addEventListener("click", function(e){
        var geoc = new BMap.Geocoder();
         pp = e.point;
        geoc.getLocation(pp, function(rs){
            var addComp = rs.addressComponents;

            var str1 = addComp.province+addComp.city+addComp.district+addComp.street+addComp.streetNumber;
            document.getElementById("suggestId").value = str1;

     

            map.clearOverlays();    //清除地图上所有覆盖物
            map.centerAndZoom(pp, 18);
            map.addOverlay(new BMap.Marker(pp));    //添加标注

        });
    });


    //以上为添加部分
    

}
    //3.显示选择的设备注册的地址
    function setPlace(){



        map.clearOverlays();    //清除地图上所有覆盖物
        function myFun(){
            pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
            map.centerAndZoom(pp, 18);
            map.addOverlay(new BMap.Marker(pp));    //添加标注
        }
        var local = new BMap.LocalSearch(map, { //智能搜索
            onSearchComplete: myFun
        });
        local.search(myValue);
    }
   


    
    
    

    //6.输入有效性验证
    function checkInputRight(){
        var snCode = $("#snCode").val();
        var regCode = $("#jiaoyan").val();
        if (snCode != "") {if (regCode != "") {
                inputFlag = true;
            }
        }
    }

    //7.查询设备注册状态
    function checkResgisState(){
        var snCode = $("#snCode").val();
        var regCode = $("#jiaoyan").val();
        $.ajax({
                url: "/device/site/check",
                cache: false,
                dataType:'json',
                data : {
                    "userName":userName,
                    "snCode":snCode,
                    "checkCode":regCode
                },
                type : 'post',
                success: function(result){
                    //首次注册
                    if(result.code == 0){
                        $("#query_").removeAttr("disabled");                                //使能设备注册按钮
                        $("#modify_").attr("disabled","disabled");                          //禁用设备修改按钮
                        $("#cancel_").attr("disabled","disabled");                          //禁用设备注销按钮
                        $("#protoSyn").attr("disabled","disabled");                         //禁用协议同步按钮
                        $("#suggestId").removeAttr("disabled");                             //使能设备部署地址输入框
                        $("#xcName").removeAttr("disabled");                                //使能现场名称输入框
                        $("#xcType").removeAttr("disabled");                                //使能现场类型输入框
                        isAsk = result.data.isAsk;                                          //协议同步标志位
                        alert(result.msg);
                    }
                    //追加注册
                    else if(result.code == 1){
                        $("#query_").removeAttr("disabled");                                //使能设备注册按钮
                        $("#protoSyn").attr("disabled","disabled");                         //禁用协议同步按钮
                        $("#modify_").attr("disabled","disabled");                          //禁用设备修改按钮
                        $("#cancel_").attr("disabled","disabled");                          //禁用设备注销按钮
                        $("#suggestId").removeAttr("disabled");                             //使能设备部署地址输入框
                        $("#xcName").removeAttr("disabled");                                //使能现场名称输入框
                        $("#xcType").removeAttr("disabled");                                //使能现场类型输入框
                        $("#suggestId").val(result.data.deviceAddress);                     //显示设备部署地址信息
                        $("#xcName").val(result.data.siteName);                             //显示现场名称信息
                        $("#xcType").val(result.data.siteType);                             //显示现场类型信息
                        isAsk = result.data.isAsk;                                          //协议同步标志位
                        alert(result.msg);
                    }
                    //该用户已注册此设备
                    else if(result.code == 2){
                        $("#query_").attr("disabled","disabled");                           //禁用设备注册按钮
                        $("#modify_").removeAttr("disabled");                               //使能设备修改按钮
                        $("#cancel_").removeAttr("disabled");                               //使能设备注销按钮
                        if(result.data.isAsk) $("#protoSyn").removeAttr("disabled");        //使能协议同步按钮
                        $("#suggestId").removeAttr("disabled");                             //使能设备部署地址输入框
                        $("#xcName").removeAttr("disabled");                                //使能现场名称输入框
                        $("#xcType").removeAttr("disabled");                                //使能现场类型输入框
                        $("#suggestId").val(result.data.deviceAddress);                     //显示设备部署地址信息
                        $("#xcName").val(result.data.siteName);                             //显示现场名称信息
                        $("#xcType").val(result.data.siteType);                             //显示现场类型信息
                        alert(result.msg);
                    }
                    //校验码或SN码错误
                    else if(result.code == 3){
                        $("#query_").attr("disabled","disabled");                           //禁用设备注册按钮
                        $("#modify_").attr("disabled","disabled");                          //禁用设备修改按钮
                        $("#cancel_").attr("disabled","disabled");                          //禁用设备注销按钮
                        $("#protoSyn").attr("disabled","disabled");                         //禁用协议同步按钮
                        $("#suggestId").attr("disabled","disabled");                        //禁用设备部署地址输入框
                        $("#xcName").attr("disabled","disabled");                           //禁用现场名称输入框
                        $("#xcType").attr("disabled","disabled");                           //禁用现场类型输入框
                        alert(result.msg);
                    }
                    //校验码失效
                    else if(result.code == 4){
                        $("#query_").attr("disabled","disabled");                           //禁用设备注册按钮
                        $("#modify_").attr("disabled","disabled");                          //禁用设备修改按钮
                        $("#cancel_").attr("disabled","disabled");                          //禁用设备注销按钮
                        $("#protoSyn").attr("disabled","disabled");                         //禁用协议同步按钮
                        $("#suggestId").attr("disabled","disabled");                        //禁用设备部署地址输入框
                        $("#xcName").attr("disabled","disabled");                           //禁用现场名称输入框
                        $("#xcType").attr("disabled","disabled");                           //禁用现场类型输入框
                        alert(result.msg);
                    }
                    //经销商为注册此设备
                    else{
                        $("#query_").attr("disabled","disabled");                           //禁用设备注册按钮
                        $("#modify_").attr("disabled","disabled");                          //禁用设备修改按钮
                        $("#cancel_").attr("disabled","disabled");                          //禁用设备注销按钮
                        $("#protoSyn").attr("disabled","disabled");                         //禁用协议同步按钮
                        $("#suggestId").attr("disabled","disabled");                        //禁用设备部署地址输入框
                        $("#xcName").attr("disabled","disabled");                           //禁用现场名称输入框
                        $("#xcType").attr("disabled","disabled");                           //禁用现场类型输入框
                        alert(result.msg);
                    }
                },
                error: function (data) {
                    console.log("error: " + data.responseText);
                }
            });
    }

    //11.新建MQTT连接，并发去第一次message
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
        c_sub_topic = "/China/HuBei/HMITest001/#";
        client.subscribe(c_sub_topic);
        message_payloadString = "1";
        message = new Paho.MQTT.Message(message_payloadString);
        devSNCode = "HMITest001";       //测试用
        message_destinationName = "/China/HuBei/" + devSNCode + "/cfg/req"
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
            // case "China/HuBei/HMITest001/cgf/req":
            //
            //     break;
            case "China/HuBei/" + devSNCode + "/cfg/ack":
                if(sendTime == 0){
                    analProtocol(message.payloadString);
                    if(isSynBtn) saveProtocol();
                    sendTime++;
                }
                break;
        }
    }

    function analProtocol(protocol) {
        let tempProtocol = new Array();
        tempProtocol = protocol.split("_");
        protocolVersionDev = tempProtocol.shift();
        tempProtocol.pop();
        protocolContent = tempProtocol[0];
        for (var i = 1; i < tempProtocol.length; i++) {
            protocolContent += "_" + tempProtocol[i];
        }
        if(!isSynBtn) devregister();
    }
    function saveProtocol() {
        var snCode = document.getElementById("snCode").value;
        $.ajax({
            type : "POST",
            url : "/data/protocolAdapt",
            data : {
                "snCode" :  snCode,
                "userName": userName,
                "protocolVersion": protocolVersionDev,
                "protocolContent": protocolContent
            },
            dataType : "json",
            success : function(data) {
                if (data.code == 0) {
                    alert(data.msg);
                    $("#protoSyn").attr('disabled',true);
                    isSynBtn = false;
                } else if(data.code == 1) {
                    alert(data.msg);
                    $("#protoSyn").removeAttr("disabled");
                    sendTime = 0;
                }
            }
        });
    }

    function devregister() {

        var snCode = document.getElementById("snCode").value;
        var siteType = document.getElementById("xcType").value;
        var siteName = document.getElementById("xcName").value;
        var deviceAddress = document.getElementById("suggestId").value;
        $.ajax({
            type : "POST",
            url : "/device/site/register",
            data : {
                "snCode" :  snCode,
                "userName": userName,
                "siteType" : siteType,
                "siteName" : siteName,
                "deviceAddress" : deviceAddress,
                "deviceLongitude" : deviceLng,
                "deviceLatitude": deviceLat,
                "siteIcon": "/123456",
                "isAdapt": isAsk,
                "protocolVersion": protocolVersionDev,
                "protocolContent": protocolContent
            },
            dataType : "json",
            success : function(data) {
                if (data.code == 0) {
                    alert('设备注册成功！');
                    document.getElementById("snCode").value = "";
                    document.getElementById("suggestId").value = "";
                    document.getElementById("jiaoyan").value = "";
                    $("#query_").attr("disabled","disabled");
                    $("#modify_").removeAttr("disabled");
                    $("#suggestId").removeAttr("disabled");
                    isSynBtn = false;
                    isAsk = 0;
                } else if(data.code == 1) {
                    alert('设备注册失败');
                }
                $("#query_").attr("disabled","disabled");
                $("#modify_").removeAttr("disabled");
                $("#cancel_").removeAttr("disabled");
            }
        });
    }


   
    
