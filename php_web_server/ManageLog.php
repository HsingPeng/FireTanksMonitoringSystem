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

	<title>消防水箱监控 | 日志</title>

	<meta content="width=device-width, initial-scale=1.0" name="viewport" />

	<meta content="" name="description" />

	<meta content="" name="author" />

	<!-- BEGIN GLOBAL MANDATORY STYLES -->

	<?php require 'manage_global_styles.php';?>

	<!-- END GLOBAL MANDATORY STYLES -->

	<link rel="stylesheet" type="text/css" href="media/css/select2_metro.css" />

	<link rel="stylesheet" href="media/css/DT_bootstrap.css" />
	
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

							日志

						</h3>

						<!-- END PAGE TITLE & BREADCRUMB-->

					</div>

				</div>

				<!-- END PAGE HEADER-->

                    <div class="row-fluid">

                        <!-- BEGIN LIST-->
                        
                        <div class="portlet box light-grey">
                            
							<div class="portlet-title">

								<div class="caption">日志记录</div>

								</div>
								<div class="portlet-body">
									<table id="table_1" class="table table-striped table-bordered table-hover" cellspacing="0" width="100%">
								        <thead>
								           
								        </thead>

								        <tbody>

										</tbody>
								        
								    </table>
								
								</div>
						
					</div>
                        
                    <!-- END LIST-->
                
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

	<script src="media/js/app.js"></script>      
	<script type="text/javascript" src="media/js/select2.min.js"></script>
	<script type="text/javascript" src="media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="media/js/DT_bootstrap.js"></script>
	<script type="text/javascript" src="media/js/manageLog.js"></script>
	
	<script>

		jQuery(document).ready(function() {

		   App.init();

		  initTable();
		   
		});

	</script>

	<!-- END JAVASCRIPTS -->

</body>

<!-- END BODY -->

</html>