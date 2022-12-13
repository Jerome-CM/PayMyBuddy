<%@ include file="../include/header.jsp" %>
        <title>Login</title>
    </head>
    <body>
    
	    <div class="form_container">
	    
		    <h1 class="h1_form">Pay My Buddy</h1>
			<c:out value="${!empty sessionScope.error ? sessionScope.error : ''}"/>
		    <form action="/login" method="post" class="form-login">
		    	<input type="email" name="mail" placeholder="Identifiant" value="bouteveillejerome@hotmail.fr">
		    	<input type="password" name="password" placeholder="Mot de passe" value="adminPass">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		    	<p class="remember"><input type="checkbox" name="remember-me"> Remember me</p>
		    	<input type="submit" value="Login" name="submit" class="submit btn">
		    </form>
	    </div><%@ include file="../include/footer.jsp" %>
    </body>
</html>