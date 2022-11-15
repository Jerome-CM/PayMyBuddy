<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<p>Bonjour ! <br> Merci de vous connecter ou de créer votre compte<br></p>
        
    <form action="/testJEE/connexion" method="post">
    	<input type="text" name="mail" placeholder="Identifiant">
    	<input type="password" name="password" placeholder="Mot de passe">
    	<input type="submit" value="Envoyer">
    </form>

</body>
</html>