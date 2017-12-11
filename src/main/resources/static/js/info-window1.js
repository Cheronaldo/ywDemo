
var markers = [];
//   var data_info = [[],[]];
//     data_info = [[116.417854,39.921988,"地址：北京市东城区王府井大街88号乐天银泰百货八层"],
//            [116.406605,39.921585,"地址：北京市东城区东华门大街"],
//            [116.412222,39.912345,"地址：北京市东城区正义路甲5号"]
//        ];

$(function() {

    getData();
    //initMap();


    $('#deviceCheck').click(function(){
        location.reload();
    });

});

//1.通过立即执行函数获取markers的坐标集合

//2.初始化地图

//3.清空地图上之前标记的点

//4.将得到的markers坐标集合标记出来

//5.清空markers坐标集合


//获取地址信息
function getData() {
    $.ajax({
        type : "POST",
        url : "/device/list",
        data : {
            "deviceOwner" : "亿维自动化"
        },
        dataType : "json",
        success : function(data) {

            if(data.code != null){



                $.each(data.code,function (index,device) {

                    markers[index] = {position:{
                        lng:device.deviceLng,
                        lat:device.deviceLat,
                        add:device.deviceAddress,
                        type:device.deviceType,
                        status:device.deviceStatus
                    }
                    };

                });

                //查询结果提示
                   if(markers.length == 0){
                       alert("未检测到您已注册的设备！");
                   }else{
                       alert("检测到您已注册的设备数量为："+markers.length);
                   }

                initMap();//设备初始化只能在这里
            }else{
                alert("设备查询失败，请重试！");
            }

        }
    });

}

//初始化地图
function initMap() {
    createMap();//创建地图
    //addMapOverlay;//向地图添加覆盖物

}

function createMap() {
    function G(id) {
        return document.getElementById(id);
    }

    var map = new BMap.Map("container");
    map.centerAndZoom("武汉",12);                   // 初始化地图,设置城市和地图级别。

    map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
    map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用

    map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件

    var localSearch = new BMap.LocalSearch(map);
    localSearch.enableAutoViewport(); //允许自动调节窗体大小

    var opts = {
        width : 250,     // 信息窗口宽度
        height: 230,     // 信息窗口高度
        title : "设备基本信息" , // 信息窗口标题
        enableMessage:true//设置允许信息窗发送短息
    };

    for(var i=0;i < markers.length;i++){
        var point = new BMap.Point(markers[i].position.lng,markers[i].position.lat);

         var url = "";
         var scontent = "";
        if(markers[i].position.status == 1){
            url = "http://image.tupian114.com/20140611/14503055.png";
            scontent = "<p style='color: red'>状态：离线</p>";
        }else {
            url = "http://image.tupian114.com/20140611/14510345.png";
            scontent = "<p style='color: green'>状态：在线</p>";
        }

         var myIcon = new BMap.Icon(url, new BMap.Size(30,25));
         var marker = new BMap.Marker(point,{icon:myIcon});  // 创建标注

        var content =
                      "<br/>型号："+markers[i].position.type +
                      scontent +
                      "地址：" + markers[i].position.add +
                      "<div align='center'>" +
                      "<br/><br/><button type='button' id = 'deviceCheck' onclick='checkDevice()'>进入设备</button>" +
                      "</div>";
        map.addOverlay(marker);               // 将标注添加到地图中
        addClickHandler(content,marker);


    }

       function addClickHandler(content,marker){
           marker.addEventListener("click",function(e){
               openInfo(content,e)}
           );
       }
       function openInfo(content,e){
           var p = e.target;
           var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
           var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象
           map.openInfoWindow(infoWindow,point); //开启信息窗口
       }


}

function checkDevice() {
    window.location.href = "/map/device";
   
}