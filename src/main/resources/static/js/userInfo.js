var getUrl = window.parent.location.search;
var userName = getUrl.substring(10);
var pageNow = 1;
var pageDevNow = 1;
var userData;                                                       //经销商所有三级用户（字符串数组）
var devListData;                                                    //现场用户名下所有设备
var addListData;                                                    //现场用户可添加所有设备
var totalPage;
var totalRecords;                                                   //经销商所有用户个数
var addListRecords;
var devListTotalPage;
var inputFlag = false;
var DevListFlag = false;
var locUserName;

$(function() {

    getLocaleUser();
    //导航栏
    var options = {
        bootstrapMajorVersion: 3,
        currentPage: 1,
        totalPages: 5,
        size:"small",
        alignment:"right",
        onPageClicked: function(e,originalEvent,type,page){
            pageNow = page;
            getLocaleUser();
        }
    }
    $('#page-right').bootstrapPaginator(options);

});

function getLocaleUser() {
    $.ajax({
        url: "/user/getAll",
        cache: false,
        dataType:'json',
        data : {
            userName: userName,
            page: pageNow
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                // alert(result.msg);
                totalPage = result.total;
                totalRecords = result.records;
                userData = result.data;
                displayUserData();
            }
            else {
                // alert(result.msg);
                totalPage = 0;
                totalRecords = 0;
                userData = [];
                displayUserData();
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

function displayUserData() {
    var listStr = "";
    for(var item in userData){
        listStr += "<tr class='user-hook'><td class='localName'>" + userData[item].userName +
                    "</td><td>" + userData[item].realName +
                    "</td><td>" + userData[item].userTelephone +
                    "</td><td>" + userData[item].userMail +
                    "</td><td>" + userData[item].userCompany +
                    "</td><td>" + userData[item].industryType +
                    "</td><td><span class='remove glyphicon glyphicon-remove'></span></td></tr>";
    }
    $(".userInfo_load_list").empty().append(listStr);
    $('.remove').click(function(e){
        var localName = $(this).parents().children(".localName").html();
        removeUser($(this).parents(".user-hook"),localName);
        stopBubble(e);
    });
    $('.user-hook').click(function(){
        DevListFlag = false;
        var localName = $(this).children(".localName").html();
        getDevList(localName);
        locUserName = localName;
    });
    $('#addLocalUser').click(function(){
        initModal();
        showModal();
    });
    //编辑对话框取消点击事件
    $('#cancelSave').click(function(){
        hideModal();
    });

    $('#close-modal').click(function(){
        hideModal();
    });
    $('#saveUserInfoBtn').click(function(){
        checkInputRight();
        if(inputFlag) {
            var localUserName = $("#localUserName").val();
            var userMail = $("#localMail").val();
            var userCompany = $("#localCompany").val();
            var userTelephone = $("#localTel").val();
            var industryType = $("#industryType").val();
            var realName = $("#localRealName").val();
            $.ajax({
                url: "/user/site/add",
                cache: false,
                dataType: 'json',
                data: {
                    agencyName: userName,
                    userName: localUserName,
                    userMail: userMail,
                    userCompany: userCompany,
                    userTelephone: userTelephone,
                    industryType: industryType,
                    realName: realName
                },
                type: 'POST',
                beforeSend: function () {
                    $('#saveUserInfoBtn').attr({ disabled: "disabled"});
                },
                success: function (result) {
                    if (result.code == 0) {
                        // alert(result.msg);
                        hideModal();
                        getLocaleUser();
                    }
                    else {
                        // alert(result.msg);
                    }
                },
                complete: function () {
                    $('#saveUserInfoBtn').removeAttr("disabled");
                },
                error: function (data) {
                    console.log(data);
                    console.info("error: " + data.responseText);
                }
            });
        }
    });
}

function stopBubble(e) {
    //如果提供了事件对象，则这是一个非IE浏览器
    if ( e && e.stopPropagation )
    //因此它支持W3C的stopPropagation()方法
        e.stopPropagation();
    else
    //否则，我们需要使用IE的方式来取消事件冒泡
        window.event.cancelBubble = true;
}

function removeUser(dom,localName) {
    $.ajax({
        url: "/user/site/unbind",
        cache: false,
        dataType:'json',
        data : {
            userName: localName
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                // alert(result.msg);
                dom.remove();
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

function unbindDev(dom, snCode) {
    $.ajax({
        url: "/device/site/unbind",
        cache: false,
        dataType:'json',
        data : {
            userName: locUserName,
            snCode: snCode
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                // alert(result.msg);
                dom.remove();
                $("#addList").prepend("<option value=" + snCode + ">" + snCode + "</option>");
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

function hideModal(){
    $("#addModal").removeClass("showModal");
    $("#addModal").addClass("hide");
    $("body").css("overflow","visible");
}

function showModal(){
    $("#addModal").removeClass("hide");
    $("#addModal").addClass("showModal");
    $("body").css("overflow","hidden");
}

function checkInputRight() {
    var localUserName = $("#localUserName").val();
    var userMail = $("#localMail").val();
    var userCompany = $("#localCompany").val();
    var userTelephone = $("#localTel").val();
    var industryType = $("#industryType").val();
    var realName = $("#localRealName").val();
    if(localUserName == "" || userMail == "" ||userCompany == "" ||userTelephone == "" ||industryType == "" ||realName == ""){
        alert("输入不能为空！");
        return;
    }
    if(userMail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1){
        alert("邮箱输入错误！");
        return;
    }
    if(!/^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(userTelephone)){
        alert("联系方式输入错误！");
        return;
    }
    if(!localUserName.match(/^[\w]{6,12}$/)){
        alert("用户名必须为6-12位数字或字母组合");
        return;
    }
    inputFlag = true;
}

function initModal() {
    inputFlag = false;
    $("#localUserName").val("");
    $("#localMail").val("");
    $("#localCompany").val("");
    $("#localTel").val("");
    $("#industryType").val("");
    $("#localRealName").val("");
}

function showDevModal() {
    $("#devModal").removeClass("hide");
    $("#devModal").addClass("showModal");
    $("body").css("overflow","hidden");
}

function hideDevModal(){
    $("#devModal").removeClass("showModal");
    $("#devModal").addClass("hide");
    $("body").css("overflow","visible");
}

function getDevList(localName) {
    $.ajax({
        url: "/device/site/getAll",
        cache: false,
        dataType:'json',
        data : {
            userName: localName,
            page: pageDevNow,
            size: 10
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                // alert(result.msg);
                devListData = result.data;
                devListTotalPage = result.total;
                DevListFlag = true;
                getAddList(localName);
            }
            else {
                // alert(result.msg);
                devListData = [];
                devListTotalPage = 0;
                DevListFlag = true;
                getAddList(localName);
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

function getAddList(localName) {
    $("#addList").empty();
    $.ajax({
        url: "/device/agency/getAll",
        cache: false,
        dataType:'json',
        data : {
            agencyName: userName,
            siteName: localName,
            page: 1,
            size: 10
        },
        type : 'POST',
        beforeSend: function () {

        },
        success: function(result){
            if(result.code == 0){
                // alert(result.msg);
                addListData = result.data;
                addListRecords = result.records;
                displayDevList();
            }
            else {
                // alert(result.msg);
                addListData = [];
                addListRecords = 0;
                displayDevList();
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

function displayDevList() {
    var str = "<li style='font-size: 12px;color: #6b6a6a; font-family: '微软雅黑''>sn码：</li>";
    var numstr = "";
    var addListstr = "";
    for (var i in devListData){
        str += "<li class='li-hook'><span class='snCode-hook'>" + devListData[i] + "</span><span class='removeDev glyphicon glyphicon-remove'></span></li>";
    }
    $(".devList").empty().append(str);
    for (var i = 1; i <= devListTotalPage; i++) {
        numstr += "<option value=" + i + ">" + i + "</option>";
    }
    $("#numSelect").empty().append(numstr);
    $('#numSelect').val(pageDevNow);
    for (var i = 0; i < addListRecords; i++) {
        addListstr += "<option value=" + addListData[i] + ">" + addListData[i] + "</option>";
    }
    $("#addList").empty().append(addListstr);
    showDevModal();
    $('#close-devModal').click(function(){
        hideDevModal();
    });
    $('#closeDevBtn').click(function(){
        hideDevModal();
    });
    $('#addDevBtn').click(function(){
        var snCode = $('#addList').val();
        $.ajax({
            url: "/device/site/bindDevice",
            cache: false,
            dataType:'json',
            data : {
                userName: locUserName,
                snCode: snCode
            },
            type : 'POST',
            beforeSend: function () {

            },
            success: function(result){
                if(result.code == 0){
                    // alert(result.msg);
                    getDevList(locUserName);
                }
                else {
                    // alert(result.msg);
                    getDevList(locUserName);
                }
            },
            complete: function () {

            },
            error: function (data) {
                console.log(data);
                console.info("error: " + data.responseText);
            }
        });
    });
    $('.removeDev').click(function(){
        var snCode = $(this).parents().children(".snCode-hook").html();
        unbindDev($(this).parents(".li-hook"),snCode);
    });
    $('#numSelect').change(function(){
        pageDevNow = $('#numSelect').val();
        getDevList(locUserName);
    });
}