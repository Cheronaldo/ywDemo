var getUrl = window.parent.location.search;
var userName = getUrl.substring(10);
var inputFlag = false;
var map;
$(function() {

    initMap();
    //getData();


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
            var deviceLng = point1.lng;
            var deviceLat = point1.lat;

           
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
                        "siteIcon": "/123456"
                    },
                    dataType : "json",
                    success : function(data) {
                        if (data.result == "0") {
                            alert('设备注册成功！');
                            document.getElementById("snCode").value = "";
                            document.getElementById("suggestId").value = "";
                            document.getElementById("jiaoyan").value = "";
                            $("#query_").attr("disabled","disabled");
                            $("#modify_").removeAttr("disabled");
                            $("#suggestId").removeAttr("disabled");
                            // <!-- 传值成功 如何到index.html中接收？ -->
                        } else if(data.result == "1") {
                            alert('设备注册失败');
                        }
                        $("#query_").attr("disabled","disabled");
                        $("#modify_").removeAttr("disabled");
                        $("#cancel_").removeAttr("disabled");
                    }
                });
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
                        if (data.result == "0") {
                            alert('设备修改成功！');
                            $("#modify_").attr('disabled',true);//避免重复注册
                            document.getElementById("snCode").value = "";
                            document.getElementById("suggestId").value = "";
                            document.getElementById("jiaoyan").value = "";
                            // <!-- 传值成功 如何到index.html中接收？ -->
                        } else if(data.result == "1") {
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
                        if (data.result == "0") {
                            alert('设备注销成功！');
                            $("#cancel_").attr('disabled',true);
                            document.getElementById("snCode").value = "";
                            document.getElementById("suggestId").value = "";
                            document.getElementById("jiaoyan").value = "";
                        } else if(data.result == "1") {
                            alert('设备注销失败');
                        }
                        $("#query_").removeAttr("disabled");
                        $("#modify_").attr("disabled","disabled");
                        $("#cancel_").attr("disabled","disabled");
                    }
                });
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
                    if(result.code == 0){
                        $("#query_").removeAttr("disabled");
                        $("#suggestId").removeAttr("disabled");
                        $("#xcName").removeAttr("disabled");
                        $("#xcType").removeAttr("disabled");
                        alert(result.msg);
                    }
                    else if(result.code == 1){
                        $("#query_").removeAttr("disabled");
                        $("#suggestId").removeAttr("disabled");
                        $("#xcName").removeAttr("disabled");
                        $("#xcType").removeAttr("disabled");
                        alert(result.msg);
                        $("#suggestId").val(result.data.deviceAddress);
                        $("#xcName").val(result.data.siteName);
                        $("#xcType").val(result.data.siteType);
                    }
                    else if(result.code == 2){
                        $("#query_").attr("disabled","disabled");
                        $("#modify_").removeAttr("disabled");
                        $("#suggestId").removeAttr("disabled");
                        $("#xcName").removeAttr("disabled");
                        $("#xcType").removeAttr("disabled");
                        $("#cancel_").removeAttr("disabled");
                        alert(result.msg);
                        $("#suggestId").val(result.data.deviceAddress);
                        $("#xcName").val(result.data.siteName);
                        $("#xcType").val(result.data.siteType);
                    }
                    else if(result.code == 3){
                        $("#query_").attr("disabled","disabled");
                        $("#modify_").attr("disabled","disabled");
                        $("#suggestId").attr("disabled","disabled");
                        $("#xcName").attr("disabled","disabled");
                        $("#xcType").attr("disabled","disabled");
                        $("#cancel_").attr("disabled","disabled");
                        alert(result.msg);
                    }
                    else if(result.code == 4){
                        $("#query_").attr("disabled","disabled");
                        $("#modify_").attr("disabled","disabled");
                        $("#suggestId").attr("disabled","disabled");
                        $("#xcName").attr("disabled","disabled");
                        $("#xcType").attr("disabled","disabled");
                        $("#cancel_").attr("disabled","disabled");
                        alert(result.msg);
                    }
                    else{
                        $("#query_").attr("disabled","disabled");
                        $("#modify_").attr("disabled","disabled");
                        $("#suggestId").attr("disabled","disabled");
                        $("#xcName").attr("disabled","disabled");
                        $("#xcType").attr("disabled","disabled");
                        $("#cancel_").attr("disabled","disabled");
                        alert(result.msg);
                    }
                },
                error: function (data) {
                    console.log("error: " + data.responseText);
                }
            });
    }




   
    
