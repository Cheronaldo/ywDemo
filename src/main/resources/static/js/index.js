var getUrl = window.location.search;
var userName = getUrl.substring(10);
var inputFlag = false;
var mailFlag = false;
var telFlag = false;
var passwordEmptyFlag = false;
var passwordFlag = false;
var pageNow = 1;
var alarmData;
var unreadNum;
$(".leftsidebar_box dt").css({"background-color":"#3992d0"});
$(".leftsidebar_box dt img").attr("src","../static/assets/images/left/select_xl01.png");
$(".leftsidebar_box dt img").attr("th:src","@{/assets/images/left/select_xl01.png}");

$(function() {

	$(".leftsidebar_box dd").hide();
    $(".leftsidebar_box dt").click(function(){
        $(".leftsidebar_box dt").css({"background-color":"#3992d0"});
        $(this).css({"background-color": "#317eb4"});
        $(this).parent().find('dd').removeClass("menu_chioce");
        $(".leftsidebar_box dt img").attr("src","/assets/images/left/select_xl01.png");
        $(".leftsidebar_box dt img").attr("th:src","@{/assets/images/left/select_xl01.png}");
        $(this).parent().find('img').attr("src","/assets/images/left/select_xl.png");
        $(this).parent().find('img').attr("th:src","@{/assets/images/left/select_xl.png}");
        $(".menu_chioce").slideUp();
        $(this).parent().find('dd').slideToggle();
        $(this).parent().find('dd').addClass("menu_chioce");
    });

	$("#logout").click(function () {
		$.ajax({
            type : "GET",
            cache: false,
            url : "/user/logout",
            dataType : "json",
            success : function(result) {
                if (result.code == "0") {
                    alert(result.msg);
                    window.location.href = "/login";
                } else {
                    alert(result.msg);
                }
            }
        });
	});

    $("#userDropdownBtn").click(function () {
        if ($("#userDropdown").css("display") == "none") {
            $("#userDropdown").css("display","block");
        }
        else{
            $("#userDropdown").css("display","none");
        }
    });

	$("#modifyAdmin").click(function () {
		getUserInfo();
	});

    $("#modifyPassword").click(function () {
        clearPasswdInput();
        showPasswordModal();
    });

	$("#close-modal").click(function () {
		hideModal();
	});

    $("#close-modal-password").click(function () {
        hidePasswordModal();
    });

    $("#saveUserInfoBtn").click(function () {
        checkInput();
        if (mailFlag) {
                if (telFlag) {
                    saveUserInfo();
                }
                //telFlag = false
                else{
                    alert("联系电话输入有误，请重新输入!");
                    $('#userTelephone').val("");
                }
            }
            //mailFlag = false
            else{
                alert("邮箱输入有误，请重新输入");
                $('#userMail').val("");
            }
    });

    $("#savePasswordInfoBtn").click(function () {
        checkPassword();
        if (passwordEmptyFlag) {
            if (passwordFlag) {
                savePassword();
            }
            //passwordFlag = false
            else{
                alert("密码不一致，请重新输入");
                $('#userPassword').val("");
                $('#userPasswordAgain').val("");
            }
        }
        //passwordEmptyFlag = false
        else{
            alert("输入不能为空");
        }
    });

	$("#cancelSave").click(function () {
		hideModal();
	});

    $("#cancelSavePassword").click(function () {
        hidePasswordModal();
    });
    askAlarmNum();
});

function hideModal(){
	$("#addModal").removeClass("showModal");
	$("#addModal").addClass("hide");
    $("body").css("overflow","visible");
}

function hidePasswordModal(){
    $("#addModalPassword").removeClass("showModal");
    $("#addModalPassword").addClass("hide");
    $("body").css("overflow","visible");
}

function showModal(){
	$("#addModal").removeClass("hide");
	$("#addModal").addClass("showModal");
    $("body").css("overflow","hidden");
}

function showPasswordModal(){
    $("#addModalPassword").removeClass("hide");
    $("#addModalPassword").addClass("showModal");
    $("body").css("overflow","hidden");
}

function getUserInfo(){
	$.ajax({
        type : "POST",
        url : "/user/getUser",
        data:{
            "userName" : userName
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                initData();
                $('#userName').val(result.data.userName);
			    $('#userClass').val(result.data.userClass);
			    $('#userPost').val(result.data.userPost);
			    $('#userMail').val(result.data.userMail);
			    $('#userCompany').val(result.data.userCompany);
			    $('#userTelephone').val(result.data.userTelephone);
				showModal();
            } else {
                alert(result.msg);
            }
        }
    });
}
function initData(){
	$('#userName').val("");
    $('#userClass').val("");
    $('#userPost').val("");
    $('#userMail').val("");
    $('#userCompany').val("");
    $('#userTelephone').val("");
}
function checkInput(){
    var mail = $('#userMail').val();
    mailFlag = mail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1;
    var tel = $('#userTelephone').val();
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
    telFlag = myreg.test(tel);
}
function saveUserInfo(){
    var userClass = $('#userClass').val();
    var userPost = $('#userPost').val();
    var userMail = $('#userMail').val();
    var userCompany = $('#userCompany').val();
    var userTelephone = $('#userTelephone').val();
    $.ajax({
        type : "POST",
        url : "/user/updateInfo",
        data:{
            "userName" : userName,
            "userClass": userClass,
            "userPost": userPost,
            "userMail": userMail,
            "userCompany": userCompany,
            "userTelephone": userTelephone
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                alert(result.msg);
                hideModal();
            } else {
                alert(result.msg);
            }
        }
    });
}
function savePassword(){
    var userPassword = $('#userPassword').val();
    userPassword = $.md5(userPassword);
    $.ajax({
        type : "POST",
        url : "/user/updatePassword",
        data:{
            "userName" : userName,
            "userPassword": userPassword
        },
        dataType : "json",
        success : function(result) {
            if (result.code == "0") {
                alert(result.msg);
                hidePasswordModal();
            } else {
                alert(result.msg);
            }
        }
    });
}
function clearPasswdInput(){
    $('#userPassword').val("");
    $('#userPasswordAgain').val("");
}
function checkPassword(){
    var password1 = $('#userPassword').val();
    var password2 = $('#userPasswordAgain').val();
    if (password1 == "" || password2 == "") {passwordEmptyFlag = false;}
    else{passwordEmptyFlag = true;}
    if (password1 == password2){
        passwordFlag = true;
    }
    else{passwordFlag = false;}
}

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
                    $("#alarmNum").html(unreadNum);
                    $("#alarmNum").css({"display":"block"});
                }
                else $("#alarmNum").css({"display":"none"});
            } else {
                alert(result.msg);
            }
        }
    });
}
