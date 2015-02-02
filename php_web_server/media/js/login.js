var Login = (function () {
    "use strict";
    return {
        //main function to initiate the module
        init: function () {
            /*global $, jQuery*/
            $('.login-form').validate({
	            errorElement: 'label', //default input error message container
	            errorClass: 'help-inline', // default input error message class
	            focusInvalid: false, // do not focus the last invalid input
	            rules: {
	                username: {
	                    required: true
	                },
	                password: {
	                    required: true
	                },
	                remember: {
	                    required: false
	                }
	            },

	            messages: {
	                username: {
	                    required: "请输入管理员ID"
	                },
	                password: {
	                    required: "请输入密码"
	                }
	            },

	            invalidHandler: function (event, validator) { //display error alert on form submit   
	            	$("#wrong").text("请输入管理员ID和密码");
	            	var errors = validator.numberOfInvalids();
	            	if($('#flag_submit').html() == 1){
	            		if (errors == 2) {
	            	　　		$('.alert-error', $('.login-form')).show();
	            	　　		}else{
	            	　　		$('.alert-error', $('.login-form')).hide();
	            	　　		}
	            		$('#flag_submit').html(0);
	            	}else{
	            		$('.alert-error', $('.login-form')).hide();
	            	}
	            },

	            highlight: function (element) { // hightlight error inputs
	                $(element)
	                    .closest('.control-group').addClass('error'); // set error class to the control group
	            },

	            success: function (label) {
	                label.closest('.control-group').removeClass('error');
	                label.remove();
	            },

	            errorPlacement: function (error, element) {
	                error.addClass('help-small no-left-padding').insertAfter(element.closest('.input-icon'));
	            },

	            submitHandler: function (form) {
	            	var pwd = "" ;
                    var options = {
                            type : "post",
                            dataType : "json",
                            url : "LoginControl.php",
                            beforeSerialize : function($from,options){
                            	$("input").attr("readonly",true);
                            	$("#checkcode").val(createCode(10));
                            	pwd = $("#password").attr("value");
                            	$("#password").val(hex_md5(hex_md5(hex_md5(pwd+""))+$("#checkcode").attr("value")));
                            },
                    		//data : {"remember" : $("#remember").attr("value")},
                            success : function (data) {
                                /*jslint devel: true */
                            	if(data.success){
                            		//var admin = data.success;
                    			        //alert("uid:"+admin.uid+",uname:"+admin.uname);
                    			        location.href = "Manage.php" ;
                    			    }else{
                            		var result = "";
//   IE8不支持	                    	for(var p in data.error){
//                            			result += data.error[p] ;
//                            		}
                            		for(var p=0 ; p < data.error.length ; p++){
                            			result += data.error[p] ;
                            		}
                            		$("#wrong").text(result);
                	            	$('.alert-error', $('.login-form')).show();
                                    //alert(result);
                            	}
                            	
                                $("#password").val(pwd) ;
                                $("input").attr("readonly",false);
                            },
                    		error : function(data){
                    			$("#wrong").text(data);
            	            	$('.alert-error', $('.login-form')).show();
                    			//alert(data); 
                    			$("#password").val(pwd) ;
                    			$("input").attr("readonly",false);
                    		},
                        };
	            	
                        // 异步提交登陆请求  
                    $('.login-form').ajaxSubmit(options);
	                //window.location.href = "index.html";
	            }
	        });
        }

    };

}());