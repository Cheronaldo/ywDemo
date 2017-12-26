var mapSpots = [];
var index = 0;
var myGeo = new BMap.Geocoder();
var spotSNCodes = [];
var spotDevModels = [];
var spotDevOnlines = [];
var content;
var url = "http://image.tupian114.com/20140419/09274112.png";
var myIcon = new BMap.Icon(url, new BMap.Size(40,30));
var opts = {
				width : 180,     // 信息窗口宽度
				height: 130,     // 信息窗口高度
				title : "信息窗口" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
$(function() {
	askMapList();
});

function askMapList(){
	$.ajax({
            url: "/device/site/mapList",
            cache: false,
            dataType:'json',
            data : {
                "userName":userName
            },
            type : 'post',
            beforeSend: function () {
                
            },
            success: function(result){
                if (result.code == 0) {
                	alert(result.msg);
                	sortMapSpot(result.data);
                	addSpots();
                }
                else{
                	alert(result.msg);
                }
            },
            complete: function () {
                
            },
            error: function (data) {
                console.info("error: " + data.responseText);
            }
        });
}

function sortMapSpot(data){
	mapSpots = [];
	spotSNCodes = [];
	spotDevModels = [];
	spotDevOnlines = [];
	for (var item in data) {
		mapSpots.push(new BMap.Point(data[item].deviceLongitude,data[item].deviceLatitude));
		spotSNCodes.push(data[item].snCode);
		spotDevModels.push(data[item].deviceModel);
		spotDevOnlines.push(data[item].isOnline);
	}
}

function addSpots(){
	for (var i = 0; i < mapSpots.length; i++) {
		content = "";
		content = 	"<div><span>SN码：" + spotSNCodes[i] + "</span></br>" +
						 "<span>设备状态：" + translateOnline(spotDevOnlines[i]) + "</span></br>" +
						 "<span>设备型号：" + spotDevModels[i] + "</span></br>" +
						 "<div class='btn'><a href='/deviceConfig?devSNCode=" + spotSNCodes[i] + "' target='fname'"
						 + "'>设备配置</a></div>" +
					"</div>";
		if (spotDevOnlines[i] == 0) {var marker = new BMap.Marker(mapSpots[i]);}
		else{var marker = new BMap.Marker(mapSpots[i],{icon:myIcon});}
		map.addOverlay(marker);
		addClickHandler(content,marker);
	}
}

function addClickHandler(content,marker){
	marker.addEventListener("click",function(e){
		openInfo(content,e)}
	);
}

function translateOnline(code){
	if (code == 0) {return "离线";}
	else if(code == 1) {return "在线";}
	else{return "error";}
}

function openInfo(content,e){
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}