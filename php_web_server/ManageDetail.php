<?php 
	require 'header.php';
?>

<!DOCTYPE html>

<!--[if IE 8]> <html class="ie8"> <![endif]-->

<!--[if IE 9]> <html class="ie9"> <![endif]-->

<!--[if !IE]><!--> <html > <!--<![endif]-->

<!-- BEGIN HEAD -->

<head>

	<meta charset="utf-8" />

	<title>消防水箱监控 | 管理</title>

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->

	<?php require 'manage_global_styles.php';?>
	<link href="media/css/manageDetail.css" rel="stylesheet" type="text/css"/>
	
	<!-- END GLOBAL MANDATORY STYLES -->

	<link rel="shortcut icon" href="media/image/favicon.ico" />

</head>

<!-- END HEAD -->

<!-- BEGIN BODY -->

<body class="page-header-fixed page-full-width">

	<!-- BEGIN HEADER -->

	<?php require 'manage_header.php';?>

	<!-- END HEADER -->

	<!-- BEGIN CONTAINER -->   

	<div class="page-container row-fluid" >

		<!-- BEGIN EMPTY PAGE SIDEBAR -->

		<?php require 'manage_sidebar.php';?>

		<!-- END EMPTY PAGE SIDEBAR -->

		<!-- BEGIN PAGE -->

		<div class="page-content">

			<!-- BEGIN PAGE CONTAINER-->

			<div class="container-fluid">

				<!-- BEGIN PAGE HEADER-->

				<div class="row-fluid">

					<div class="span12" style="text-align: center">

						<!-- BEGIN PAGE TITLE & BREADCRUMB-->

						<h3 class="page-title">

							<?php echo $_REQUEST["ename"]?>水箱详细记录
                            <p style="display:none" id="eid"><?php echo $_REQUEST["eid"]?></p>
						</h3>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

                    <div class="row-fluid">

                        <!-- BEGIN CHARTS-->
                        
                        <div class="span12">


						<!-- BEGIN INTERACTIVE CHART PORTLET-->

						<div class="portlet box green">

							<div class="portlet-title">

								<div class="caption"><i ></i>水压记录</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

								</div>

							</div>

							<div class="portlet-body">
	
								<p>
								<div id="chart_1" class="chart"></div>
								</p>
								<p>
								<div class="input-group">
									  <span class="input-group-addon">每次查询</span>
									  <input type="number" class="form-control row_1" value="30" id="number_gage" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
									  <span class="input-group-addon">&nbsp;&nbsp;条</span>
								</div>
								</p>
								<p style="position: relative;">
									<a href="javaScript:Detail.get_old_gage()" class="btn green text_left"><i class="m-icon-big-swapleft m-icon-white"></i>向前查询</a>
									<a href="javaScript:Detail.get_new_gage()" class="btn green text_right">向后查询<i class="m-icon-big-swapright m-icon-white"></i></a>
								</p>
							</div>
						</div>
						
						<!-- END INTERACTIVE CHART PORTLET-->  
						
						<!-- BEGIN INTERACTIVE CHART PORTLET-->

						<div class="portlet box yellow">

							<div class="portlet-title">

								<div class="caption"><i ></i>水温记录</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

								</div>

							</div>

							<div class="portlet-body">
	
								<p>
								<div id="chart_2" class="chart"></div>
								</p>
								<p>
								<div class="input-group">
									  <span class="input-group-addon">每次查询</span>
									  <input type="number" class="form-control row_1" value="30" id="number_temp" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
									  <span class="input-group-addon">&nbsp;&nbsp;条</span>
								</div>
								</p>
								<p style="position: relative;">
									<a href="javaScript:Detail.get_old_temp()" class="btn yellow text_left"><i class="m-icon-big-swapleft m-icon-white"></i>向前查询</a>
									<a href="javaScript:Detail.get_new_temp()" class="btn yellow text_right">向后查询<i class="m-icon-big-swapright m-icon-white"></i></a>
								</p>
							</div>
						</div>
						
						<!-- END INTERACTIVE CHART PORTLET-->  
						
						<!-- BEGIN INTERACTIVE CHART PORTLET-->

						<div class="portlet box blue">

							<div class="portlet-title">

								<div class="caption"><i ></i>水位记录</div>

								<div class="tools">

									<a href="javascript:;" class="collapse"></a>

								</div>

							</div>

							<div class="portlet-body">
	
								<p>
								<div id="chart_3" class="chart"></div>
								</p>
								<p>
								<div class="input-group">
									  <span class="input-group-addon">每次查询</span>
									  <input type="number" class="form-control row_1" value="30" id="number_level" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
									  <span class="input-group-addon">&nbsp;&nbsp;条</span>
								</div>
								</p>
								<p style="position: relative;">
									<a href="javaScript:Detail.get_old_level()" class="btn blue text_left"><i class="m-icon-big-swapleft m-icon-white"></i>向前查询</a>
									<a href="javaScript:Detail.get_new_level()" class="btn blue text_right">向后查询<i class="m-icon-big-swapright m-icon-white"></i></a>
								</p>
							</div>
						</div>
						
						<!-- END INTERACTIVE CHART PORTLET-->              

					</div>


						<!-- END CHARTS-->
						
					</div>
                
			</div>

			<!-- END PAGE CONTAINER--> 
            
		</div>

		<!-- END PAGE -->    
        
        
	</div>

	<!-- END CONTAINER -->

	<!-- BEGIN FOOTER -->

	<?php require 'manage_footer.php';?>
	
	<!-- END FOOTER -->

	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->

	<!-- BEGIN CORE PLUGINS -->

	<?php require 'manage_core_plugins.php';?>

	<!-- END CORE PLUGINS -->

	<script src="media/js/jquery.flot.js"></script>
	<script src="media/js/jquery.flot.time.js"></script>
	<script src="media/js/jquery.flot.resize.js"></script>
	
	<script src="media/js/app.js"></script>
	<script src="media/js/manageDetail.js"></script>
	<!--<script src="media/js/debuggap.js" type="text/javascript"></script>-->

	<script>

		jQuery(document).ready(function() {

		   App.init();
		   /*Detail.init();*/
		   Detail.initCharts();
		   
		});


	</script>

	<!-- END JAVASCRIPTS -->

</body>

<!-- END BODY -->

</html>