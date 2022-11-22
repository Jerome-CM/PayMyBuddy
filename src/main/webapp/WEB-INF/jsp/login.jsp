<%@ include file="../include/header.jsp" %>
        <title>Test</title>
    </head>
    <body>
    
	    <div class="form_container">
	    
		    <h1 class="h1_form">Pay My Buddy</h1>
		    <form action="/loginFormControl" method="post">
		    	<input type="email" name="mail" placeholder="Identifiant">
		    	<input type="password" name="password" placeholder="Mot de passe">
		    	<p class="remember"><input type="checkbox" name="remember"> Remember me</p>
		    	<input type="submit" value="Login" name="submit" class="submit btn">
		    </form>
	    </div>
    </body>
</html>