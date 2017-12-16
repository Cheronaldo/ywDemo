var grid_selector = "#jqGrid";
var infoId;
var getUrl = window.parent.location.search;
var userName = getUrl.substring(10);
var protocolVersion;
var protocolVersionDev;
var sendTime = 0;
var client;
var devSNCodeTest;
$(function() {




    fillHeadInfo(devSNCode);


    // $("#jqGrid").jqGrid({
    //     url:"/protocol/site/get",
    //     //url:"/queryStudentList",
    //     postData: {snCode : devSNCode,userName: userName,isAdapt: 0,protocolVersion: protocolVersion},
    //     datatype: "json",
    //     mtype: 'POST',
    //     height:window.screen.height-550,
    //     // colNames: ['id','序号','属性','数据是否可见','报警是否接收'],
    //     colModel: [
    //         { label: 'id', name: 'id', width: 40,hidden:true},
    //         { label: '序号', name: 'offsetNumber', width: 40},
    //         { label: '属性', name: 'dataName', width: 40 },
    //         { label: '数据是否可见', name: 'isVisible', width: 40,editrules: true,formatter:'select',editoptions:{value:{0:'否',1:'是'}} },
    //         { label: '报警是否接收', name: 'isAlarmed', width: 40,editrules: true,formatter:'select',editoptions:{value:{0:'否',1:'是'}} }
    //         //---- name: 'passwd'  对应的是后台实体中的  属性值  因为在这里并没有对其进行 声明
    //          //{ label: '性别', name: 'opt', width: 200,formatter: function(cellvalue, options, cell){
    //            //  return '<a  href="'+cell.student_gender+'" class="btn btn-purple btn-sm" target="_blank"><i class="fa fa-cog  fa-spin" aria-hidden="true"></i>点我</a>';
    //          //}}
    //     ],
    //     // colModel: [
    //     //     { name: 'id', width: 40,hidden:true},
    //     //     { name: 'offsetNumber', width: 40},
    //     //     { name: 'dataName', width: 40 },
    //     //     { name: 'isVisible', width: 40 },
    //     //     { name: 'isAlarmed', width: 40 }
    //     //     //---- name: 'passwd'  对应的是后台实体中的  属性值  因为在这里并没有对其进行 声明
    //     //     //{ label: '性别', name: 'opt', width: 200,formatter: function(cellvalue, options, cell){
    //     //     //  return '<a  href="'+cell.student_gender+'" class="btn btn-purple btn-sm" target="_blank"><i class="fa fa-cog  fa-spin" aria-hidden="true"></i>点我</a>';
    //     //     //}}
    //     // ],
    //
    //     pager: '#jqGridPager',
    //     rowNum:2,
    //     rowList:[2,30,45], //可调整每页显示的记录数
    //     viewrecords: true,//是否显示行数
    //     altRows: true,  //设置表格 zebra-striped 值
    //     gridview: true, //加速显示
    //     multiselect: true,//是否支持多选
    //     multiselectWidth: 40, //设置多选列宽度
    //     multiboxonly: true,
    //     shrinkToFit:true, //此属性用来说明当初始化列宽度时候的计算类型，如果为ture，则按比例初始化列宽度。如果为false，则列宽度使用colModel指定的宽度
    //     forceFit:true, //当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit为false时，此属性会被忽略
    //     autowidth: true,
    //     loadComplete : function() {
    //         var table = this;
    //         setTimeout(function(){
    //             updatePagerIcons(table);
    //         }, 0);
    //     },
    //     gridComplete: function () {
    //         // 防止水平方向上出现滚动条
    //         // removeHorizontalScrollBar();
    //     },
    //     jsonReader: {//jsonReader来跟服务器端返回的数据做对应
    //         root: "rows",   //包含实际数据的数组
    //         total: "total", //总页数
    //         records:"records", //查询出的总记录数
    //         repeatitems : false //指明每行的数据是可以重复的，如果设为false，则会从返回的数据中按名字来搜索元素，这个名字就是colModel中的名字
    //     },
    //     emptyrecords: '没有记录!',
    //     loadtext: '正在查询服务器数据...',
    //     //error: window.location.href = "/toLogin",//这个方法不行
    //
    //
    // });
    //
    // //设置分页按钮组
    // $("#jqGrid").jqGrid('navGrid',"#jqGridPager",
    //     {
    //         edit: true,
    //         edittitle:'修改',
    //         edittext:'修改',
    //         editicon : 'icon-pencil blue',
    //         editfunc :editRecord,
    //         add: false,
    //         // addtitle:'新增',
    //         // addtext:'新增',
    //         // addicon : 'icon-plus-sign purple',
    //         // addfunc :addUser,
    //         del: false,
    //         // deltitle:'删除',
    //         // deltext:'删除',
    //         // delicon : 'icon-trash red',
    //         // delfunc:delUser,
    //         refresh: true,
    //         refreshicon : 'icon-refresh green',
    //         beforeRefresh:refreshData,
    //         search: false,
    //         view: false,
    //         alertcap:"提示",
    //         alerttext : "请选择需要操作的数据!"
    //     }
    // );
    // $(window.parent).resize(function(){
    // });
});

function displayGrid() {
    $("#jqGrid").jqGrid({
        url:"/protocol/site/get",
        //url:"/queryStudentList",
        postData: {snCode : devSNCode,userName: userName,isAdapt: 0,protocolVersion: protocolVersion},
        datatype: "json",
        mtype: 'POST',
        height:window.screen.height-550,
        // colNames: ['id','序号','属性','数据是否可见','报警是否接收'],
        colModel: [
            { label: 'id', name: 'id', width: 40,hidden:true},
            { label: '序号', name: 'offsetNumber', width: 40},
            { label: '属性', name: 'dataName', width: 40 },
            { label: '数据是否可见', name: 'isVisible', width: 40,editrules: true,formatter:'select',editoptions:{value:{0:'否',1:'是'}} },
            { label: '报警是否接收', name: 'isAlarmed', width: 40,editrules: true,formatter:'select',editoptions:{value:{0:'否',1:'是'}} }
            //---- name: 'passwd'  对应的是后台实体中的  属性值  因为在这里并没有对其进行 声明
            //{ label: '性别', name: 'opt', width: 200,formatter: function(cellvalue, options, cell){
            //  return '<a  href="'+cell.student_gender+'" class="btn btn-purple btn-sm" target="_blank"><i class="fa fa-cog  fa-spin" aria-hidden="true"></i>点我</a>';
            //}}
        ],
        // colModel: [
        //     { name: 'id', width: 40,hidden:true},
        //     { name: 'offsetNumber', width: 40},
        //     { name: 'dataName', width: 40 },
        //     { name: 'isVisible', width: 40 },
        //     { name: 'isAlarmed', width: 40 }
        //     //---- name: 'passwd'  对应的是后台实体中的  属性值  因为在这里并没有对其进行 声明
        //     //{ label: '性别', name: 'opt', width: 200,formatter: function(cellvalue, options, cell){
        //     //  return '<a  href="'+cell.student_gender+'" class="btn btn-purple btn-sm" target="_blank"><i class="fa fa-cog  fa-spin" aria-hidden="true"></i>点我</a>';
        //     //}}
        // ],

        pager: '#jqGridPager',
        rowNum:10,
        rowList:[10,30,45], //可调整每页显示的记录数
        viewrecords: true,//是否显示行数
        altRows: true,  //设置表格 zebra-striped 值
        gridview: true, //加速显示
        multiselect: true,//是否支持多选
        multiselectWidth: 40, //设置多选列宽度
        multiboxonly: true,
        shrinkToFit:true, //此属性用来说明当初始化列宽度时候的计算类型，如果为ture，则按比例初始化列宽度。如果为false，则列宽度使用colModel指定的宽度
        forceFit:true, //当为ture时，调整列宽度不会改变表格的宽度。当shrinkToFit为false时，此属性会被忽略
        autowidth: true,
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        gridComplete: function () {
            // 防止水平方向上出现滚动条
            // removeHorizontalScrollBar();
        },
        jsonReader: {//jsonReader来跟服务器端返回的数据做对应
            root: "rows",   //包含实际数据的数组
            total: "total", //总页数
            records:"records", //查询出的总记录数
            repeatitems : false //指明每行的数据是可以重复的，如果设为false，则会从返回的数据中按名字来搜索元素，这个名字就是colModel中的名字
        },
        emptyrecords: '没有记录!',
        loadtext: '正在查询服务器数据...',
        //error: window.location.href = "/toLogin",//这个方法不行


    });

    //设置分页按钮组
    $("#jqGrid").jqGrid('navGrid',"#jqGridPager",
        {
            edit: true,
            edittitle:'修改',
            edittext:'修改',
            editicon : 'icon-pencil blue',
            editfunc :editRecord,
            add: false,
            // addtitle:'新增',
            // addtext:'新增',
            // addicon : 'icon-plus-sign purple',
            // addfunc :addUser,
            del: false,
            // deltitle:'删除',
            // deltext:'删除',
            // delicon : 'icon-trash red',
            // delfunc:delUser,
            refresh: true,
            refreshicon : 'icon-refresh green',
            beforeRefresh:refreshData,
            search: false,
            view: false,
            alertcap:"提示",
            alerttext : "请选择需要操作的数据!"
        }
    );
}

function refreshData(){
    $("#jqGrid").jqGrid('setGridParam',{
        postData:{snCode : devSNCode,userName: userName,isAdapt: 0,protocolVersion: protocolVersion},///-----
        page:1
    }).trigger("reloadGrid");
}

// 这个是分页图标，必须添加
function updatePagerIcons(table) {
    var replacement =
        {
            'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
            'ui-icon-seek-next' : 'icon-angle-right bigger-140',
            'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
        };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
        // console.info($class);
        if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
    });
}

function editRecord() {
    var rows=$(grid_selector).getGridParam('selarrrow');
    if(rows.length==0){
        // $.messager.alert("温馨提示","请选择一行记录！");
        layer.msg('请选择一行记录！', {icon: 7,time: 2000}); //2秒关闭（如果不配置，默认是3秒）
        // alert("请选择一条数据！");
        return;
    }else if(rows.length>1){
        // $.messager.alert("温馨提示","不能同时修改多条记录！");
        layer.msg('不能同时修改多条记录！', {icon: 7,time: 2000}); //2秒关闭（如果不配置，默认是3秒）
        // alert("最多选择一条数据！");
        return;
    }else{
        var data = $(grid_selector).jqGrid('getRowData', rows[0]);
        // task = "update";
        initData();
        $("#offsetNumber").val(data.offsetNumber);
        $("#dataName").val(data.dataName);//
        $("#isVisible").val(translateCode(data.isVisible));
        $("#isAlarmed").val(translateCode(data.isAlarmed));
        console.log(data.isVisible);
        console.log(data.isAlarmed);
        infoId = data.id;
        //$('#account').readOnly="readOnly";
        // AddReadOnly();
        showModal();
    }
}

function initData() {
    $('#offsetNumber').val("");
    $('#dataName').val("");
    $('#isVisible').val("");
    $('#isAlarmed').val("");
}

//编辑对话框取消点击事件
$('#cancelSave').click(function(){
    hideModal();
});

$('#close-modal').click(function(){
    hideModal();
});

$('#saveUserInfoBtn').click(function(){
    var isVisible = detranslateCode($('#isVisible').val());
    var isAlarmed = detranslateCode($('#isAlarmed').val());
    var offsetNumber = $('#offsetNumber').val();
    $.ajax({
        type : "POST",
        url : "/protocol/site/update",
        data:{
            "userName" : userName,
            "snCode" : devSNCode,
            "protocolVersion" : protocolVersion,
            "offsetNumber" : offsetNumber,
            "isVisible": isVisible,
            "isAlarmed": isAlarmed
        },
        dataType : "json",
        success : function(result) {
            // if (result.code == "0") {
            //     alert(result.msg);
            //
            // } else {
            //     alert(result.msg);
            // }
            hideModal();
            refreshData();
        }
    });
});

function translateCode(code) {
    if(code == 0)return "否";
    else return "是";
}

function detranslateCode(code) {
    if(code == "是")return 1;
    else return 0;
}

function fillHeadInfo(devSNCode) {
    $("#snCode").val(devSNCode);
    $.ajax({
        url: "/protocol/getVersion",
        cache: false,
        async: false,
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
                $("#protocolVersion").val(result.data);
                protocolVersion = result.data;
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
    c_sub_topic = "/China/HuBei/"+devSNCode+"/#";
    client.subscribe(c_sub_topic);
    message_payloadString = "1";
    message = new Paho.MQTT.Message(message_payloadString);
    devSNCodeTest = "HMITest001";       //测试用
    message_destinationName = "/China/HuBei/" + devSNCode + "/DevRecv/cfg/req"
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
        case "China/HuBei/" + devSNCode + "/DevSend/cfg/ack":
            if(sendTime == 0){
                analProtocol(message.payloadString);
                sendTime++;
            }
            break;
    }
}

function analProtocol(protocol) {
    let tempProtocol = new Array();
    tempProtocol = protocol.split("_");
    protocolVersionDev = tempProtocol.shift();
    // protocolVersionDev = protocolVersion;       //测试用
    if(protocolVersionDev != protocolVersion){
        if(confirm("协议不匹配，是否返回至协议同步页面？")){
            window.location.href = "/device?isAsk=1";
        }
    }
    else displayGrid();
}