var pageNow = 1;
var alarmData;
var unreadNum = localStorage.getItem("unreadNum");
var getUrl = window.parent.location.search;
var userName = getUrl.substring(10);
$(function() {
    askAlarmNum();
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: 1,
        totalPages: 5,
        size:"small",
        alignment:"right",
        onPageClicked: function(e,originalEvent,type,page){
            pageNow = page;
            askAlarmNum();
        }
    };
    $('#page-right').bootstrapPaginator(options);
});

function askAlarmNum() {
    $.ajax({
        type : "POST",
        url : "/alarm/getAll",
        data:{
            "userName" : userName,
            "page": pageNow
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                alert(result.msg);
                alarmData = result.data;
                unreadNum = result.unreadNum;
                localStorage.setItem("unreadNum",unreadNum);
                if(unreadNum != 0){
                    window.parent.$("#alarmNum").html(unreadNum);
                    window.parent.$("#alarmNum").css({"display":"block"});
                }
                else window.parent.$("#alarmNum").css({"display":"none"});
                if(alarmData) displayData();
                else alert("暂无报警信息");
            } else {
                alert(result.msg);
            }
        }
    });
}

function displayData() {
    var listStr = "";
    for(var i=0;i<alarmData.length;i++){
        listStr += "<tr id='" + alarmData[i].id +  "' class='alarmtr " + isReaded(alarmData[i].isChecked) + "'>";
        listStr +=  "<td width='15%'>" + alarmData[i].snCode + "</td>" +
            "<td width='15%'>" + alarmData[i].alarmTime + "</td>" +
            "<td width='15%'>" + alarmData[i].handleTime + "</td>" +
            "<td width='10%'>" + alarmData[i].dataName + "</td>" +
            "<td width='10%'>" + alarmData[i].actualValue + "</td>" +
            "<td width='15%'>" + alarmData[i].alarmInfo + "</td>";
        listStr += "</tr>";
    }
    $(".alarm_load_list").empty().append(listStr);
    $(".alarmtr").click(function(){
        let id = this.id;
        let dom = $(this);
        let isReaded = dom.hasClass('unread');
        if(isReaded) {
            $.ajax({
                type : "POST",
                url : "/alarm/decreaseNum",
                data:{
                    "id" : id
                },
                dataType : "json",
                success : function(result) {
                    if (result.code == "0") {
                        alert(result.msg);
                        unreadNum--;
                        dom.removeClass("unread");
                        localStorage.setItem("unreadNum",unreadNum);
                        if(unreadNum != 0){
                            window.parent.$("#alarmNum").html(unreadNum);
                            window.parent.$("#alarmNum").css({"display":"block"});
                        }
                        else window.parent.$("#alarmNum").css({"display":"none"});
                    } else {
                        alert(result.msg);
                    }
                }
            });
        }
    });
}

function isReaded(code) {
    if(code == 0) return "unread";
    else return "";
}