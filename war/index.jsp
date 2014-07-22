<!DOCTYPE html>
<html> 
<head>
<title>Incentro EventSystem</title>
<link rel="stylesheet" type="text/css" href="assets/css/inviteSystem.css" />
</head>

<body>

<div class="wrapper"> 

	  <div class="header">
        <a href="index.jsp" ><img src="assets/img/logo.png" /></a>
      </div>
            <div class="errorMsg"><%
        if(request.getParameter("message")!=null){
        String message = request.getParameter("message");
        out.print(request.getParameter("message")); 
        }
        %>
        </div>
		
	<form class="loginform" action="Inloggen.do" method="post">

			<div class= "logindata" >
				<span>
					<label for="username">Gebruikersnaam</label>
					<input type="text" name="gebruikersnaam" id="username" placeholder="Gebruikersnaam" autofocus required /><br />
				</span>
				<span>
					<label for="password">Wachtwoord</label>
					<input type="password" name="wachtwoord" id="password" placeholder="Wachtwoord" required />
				</span>
			</div>
				<input type="submit" value="Log in" id="login_submit" class="login_submit" />
	</form>
	
</div>
</body>

</html>