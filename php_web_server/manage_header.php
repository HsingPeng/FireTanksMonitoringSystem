<div class="header navbar navbar-inverse navbar-fixed-top">

		<!-- BEGIN TOP NAVIGATION BAR -->

		<div class="navbar-inner">

			<div class="container-fluid">

				<!-- BEGIN LOGO -->

				<a class="brand" href="Manage.php">

				<img src="media/image/logo_2.png" alt="logo" />

				</a>

				<!-- END LOGO -->

				<!-- BEGIN HORIZANTAL MENU -->

				<div class="navbar hor-menu hidden-phone hidden-tablet">

					<div class="navbar-inner">

						<ul class="nav">

							<li class="visible-phone visible-tablet">

							</li>

							<li>

								<a href="Manage.php">

								概况

								</a>

							</li>

							

							<li>

								<a href="ManageLog.php">日志</a>

							</li>

						</ul>

					</div>

				</div>

				<!-- END HORIZANTAL MENU -->

				<!-- BEGIN RESPONSIVE MENU TOGGLER -->

				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">

				<img src="media/image/menu-toggler.png" alt="" />

				</a>          

				<!-- END RESPONSIVE MENU TOGGLER -->            

				<!-- BEGIN TOP NAVIGATION MENU -->              

				<ul class="nav pull-right">

					<!-- BEGIN USER LOGIN DROPDOWN -->

					<li class="dropdown user">

						<a href="#" class="dropdown-toggle" data-toggle="dropdown">

						<img alt="" src="media/image/avatar_small.png" />

						<span class="username"><?php echo $_SESSION['uname'];?></span>

						<i class="icon-angle-down"></i>

						</a>

						<ul class="dropdown-menu">

							<li><a href="ManageSetting.php"><i class="icon-tasks"></i>&nbsp;&nbsp;设置</a></li>

							<li><a href="ManageHelp.php"><i class="icon-question-sign"></i>&nbsp;&nbsp;帮助</a></li>

							<li class="divider"></li>

							<li><a href="LogoutControl.php"><i class="icon-key"></i>&nbsp;&nbsp;退出</a></li>

						</ul>

					</li>

					<!-- END USER LOGIN DROPDOWN -->

				</ul>

				<!-- END TOP NAVIGATION MENU --> 

			</div>

		</div>

		<!-- END TOP NAVIGATION BAR -->

	</div>