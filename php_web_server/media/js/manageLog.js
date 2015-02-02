function initTable(){

        $("#table_1").dataTable({
                "oLanguage" : { // 汉化
                    "sProcessing" : "正在加载数据...",
                    "sLengthMenu" : "显示 _MENU_ 条 ",
                    "sZeroRecords" : "没有您要搜索的内容",
                    "sInfo" : "从_START_ 到 _END_ 条记录——总记录数为 _TOTAL_ 条",
                    "sInfoEmpty" : "记录数为0",
                    "sInfoFiltered" : "(全部记录数 _MAX_  条)",
                    "sInfoPostFix" : "",
                    "sSearch" : "搜索",
                    "sUrl" : "",
                    "oPaginate" : {
                        "sFirst" : "第一页",
                        "sPrevious" : " 上一页 ",
                        "sNext" : " 下一页 ",
                        "sLast" : " 最后一页 "
                    }
                },
                "bPaginate" : true,// 分页按钮
                "bFilter" : false,// 搜索栏
                "bLengthChange" : true,// 每行显示记录数
                "iDisplayLength" : 10,// 每页显示行数
                "bSort" : false,// 排序
                "sPaginationType" : "bootstrap", 
                "bProcessing" : true,
                "bServerSide" : true,
                "sAjaxSource": 'getLog.php', 
                "aoColumns":
                   [
                        { "sTitle": "日志ID" },
                        { "sTitle": "设备ID" },
                        { "sTitle": "等级" ,
                            "fnRender": function(obj) {
                                var sReturn = obj.aData[ obj.iDataColumn ];
                                if ( sReturn == 0 ) {
                                    sReturn = '<span class="label label-success">正常</span>';
                                }else if( sReturn == 1 ){
                                    sReturn = '<span class="label label-warning">警告</span>';
                                }else{
                                        sReturn = '<span class="label label-danger">错误</span>';
                                    }
                                return sReturn;
                            },
                            "sClass": "",
                        },
                        { "sTitle": "水压" ,
                            "fnRender": function(obj) {
                                    var y = obj.aData[ obj.iDataColumn ];
                                    var result= "正常";
                                    if(y==1){
                                        result="较低";
                                    }else if(y==3){
                                        result="充足";
                                    }                                  
                                    
                                    return result;
                                }
                        },
                        { "sTitle": "水温" ,
                            "fnRender": function(obj) {
                                var sReturn = obj.aData[ obj.iDataColumn ];
                                return sReturn+"℃";
                            }
                        },
                        { "sTitle": "水位" ,
                            "fnRender": function(obj) {
                                var y = obj.aData[ obj.iDataColumn ];
                                var result= "正常";
                                if(y==1){
                                    result="过低";
                                }else if(y==2){
                                    result="较低";
                                }else if(y==4){
                                    result="充足";
                                }
                                return result;
                            }  
                        },
                        { "sTitle": "日期" }
                    ],
                "fnServerData" : function(sSource, aoData, fnCallback) {
                    $.ajax({
                        "type" : 'post',
                        "url" : sSource,
                        "dataType" : "json",
                        "data" : aoData ,
                        "success" : fnCallback
                    });

                }

            });

}