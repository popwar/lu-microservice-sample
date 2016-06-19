<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta name="keywords" content="Org.Lu">
<link rel="icon" type="image/png" sizes="32x32" href="img/favicon.ico">
<link rel="stylesheet" href="webjars/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="webjars/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/custom.css">
<link rel="stylesheet" href="css/login.css">
<title>Org.Lu | login</title>
<style>
html {
	background-image: url('img/bg1.jpg');
	background-repeat: no-repeat;
	background-size: cover;
}
</style>
</head>

<body>
	<section id="loginSection">
		<div class="container">
			<div class="row loginbox-header">
				<h2 class="blue-text">Org.Lu</h3>
			</div>
			<div class="row">
				<div class="loginbox">
					<span class="text-form-title black-text">Sign in to
						dashboard</span>
					<form role="form" action="login" method="post">
						<div class="form-row">
							<input class="form-control input-md" id="username"
								name="username" placeholder="Username" />
						</div>
						<div class="form-row">
							<input type="password" id="password"
								class="form-control input-md" name="password"
								placeholder="Password" />
						</div>
						<input type="hidden" id="csrf_token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="form-row">
							<input class="btn" type="submit" id="login" value="Sign In">
						</div>
					</form>
					<#if RequestParameters['error']??>
					<div style="color: red;">
		               Username or password is invalid. Please try again.
	                </div>
                    </#if>
					<div id="forgotpwd-signup">
						<p>
							<a href="#" class="blue-text">Forgot your username or
								password?</a>
						</p>
						<p>
							Don't have an account?&nbsp;&nbsp;&nbsp;<a href="#"
								class="blue-text">Sign Up</a>
						</p>
					</div>
				</div>
				<div id="link-back-home">
					<a href="https://www.google.com.au"><span class="glyphicon glyphicon-chevron-left"></span>&nbsp;&nbsp;&nbsp;<span
						class="black-text">Return to Homepage</span></a>
				</div>
			</div>
		</div>
	</section>
	<script src="js/wro.js" type="text/javascript"></script>
</body>
</html>