var Manage = (function () {
    "use strict";
    return {
        //main function to initiate the module
        init: function () {
           
            Manage.getEquipment();
            
        },
        
        /* 3000毫秒刷新一次 */
        intervalRefresh: function () {

            setTimeout(function () {
                Manage.getEquipment();
            }, 3000);
            
        },
        
        addEquipment : function (eid_1, ename_1, serialnum_1, water_gage_1, water_temp_1, water_level_1) {
            //	E/W/N=>2/1/0
            var flag = 0, water_gage_result, water_temp_result , water_level_result , color_bg ;
            //判断水压
            if(water_gage_1==1){
                water_gage_result = "过低";
                if(flag<1){
                    flag=1;
                }
            }else{
                water_gage_result = "正常";
            }

            //判断水温
            if (4<=water_temp_1&&water_temp_1<6){
                water_temp_result = "较低";
                if(flag<1){
                    flag=1;
                }
            }else if(water_temp_1<4){
                water_temp_result = "过低";
                if(flag<2){
                    flag=2;
                }
            }else{
                water_temp_result = "正常";
            }

            //判断水位
            if(water_level_1==1){
                water_level_result = "过低";
                if(flag<2){
                    flag=2;
                }
            }else if (water_level_1==2){
                water_level_result = "较低";
                if(flag<1){
                    flag=1;
                }
            }else{
                water_level_result = "正常";
            }

            if(flag==2){
                color_bg="red";
            }else if(flag==1){
                color_bg="yellow";
            }else{
                color_bg="green";
            }
            
            var txt_1 =  '<div class="responsive span3" data-tablet="span6" data-desktop="span3">' ;
            txt_1 += '<div class="dashboard-stat '+color_bg+'">';
            txt_1 += '<div class="visual">';
            txt_1 += '<i class="icon-bar-chart"></i>';
            txt_1 += '</div>';
            txt_1 += '<div class="details">';
            txt_1 += '<div class="number">';
            txt_1 += water_temp_1+' ℃';
            txt_1 += '</div>';
            txt_1 += '<div class="desc">';
            txt_1 += '水压'+water_gage_result;
            txt_1 += '</div>';
            txt_1 += '<div class="desc">';
            txt_1 += '水位'+water_level_result;
            txt_1 += '</div>';
            txt_1 += '</div>';
            txt_1 += '<div class="ename">';
            txt_1 += '<div class="desc">';
            txt_1 += ename_1+'水箱';
            txt_1 += '</div>';
            txt_1 += '</div>';
            txt_1 += '<a class="more" href="ManageDetail.php?eid='+eid_1+'&ename='+ename_1+'">';
            txt_1 += '详细信息 ';
            txt_1 += '<i class="m-icon-swapright m-icon-white"></i>';
            txt_1 += '</a>';
            txt_1 += '</div>';
            txt_1 += '</div>';
            $("#content").append(txt_1);
        
        },
        getEquipment: function () {
            
           $.ajax({
				url: "getCurrent.php",
				type: "GET",
				dataType: "json",
				success: onDataReceived,
				error: onError
			});
            
            function onDataReceived(series) {
                var time = Date().toLocaleString();
                $("#refresh_time").text("刷新时间："+ time);
                
                $("#content").empty();
                
                if(series.data==null){
                        alert("获取数据错误: "+series.error);
                    }else{
                        
                        
                        
                        var data = series.data ;
                        for(var y in data){
                           var x = data[y]; Manage.addEquipment(x["eid"],x["ename"],x["serialnum"],x["water_gage"],x["water_temp"],x["water_level"]);
                        }
                    }
                Manage.addEquipment(3,"NO.3模拟","",1,13,3);
                /*添加模拟数据*/
                
                Manage.intervalRefresh();
                
        }

        function onError(XMLHttpRequest, textStatus, errorThrown){
            /*alert("获取数据错误: "+textStatus);*/
            $("#refresh_time").text("与服务器断开连接！！！");
            Manage.intervalRefresh();
        }
           
        },
        
 
    };
}());
