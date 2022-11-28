<%@ include file="../include/header.jsp" %>
        <title>Add a friend</title>
    </head>
    <body>
    
    <%@ include file="../include/menu.jsp" %>

	<section class="money">
			<div>
				<h2>Send a message</h2>
			</div>
			<div class="control_money contact">
				<form method="POST" action="/sendContactMessage" class="container_choose_friend">
					<div class="control_add_money">
						<input type="text" name="firstname" placeholder="Firstname">
						<input type="text" name="lastname" placeholder="Lastname">
						<input type="text" name="mail" placeholder="Mail">
						<textarea name="message" placeholder="Votre message ici"></textarea>
						<input type="submit" value="Save" name="submit" class="btn btn_pay">
					</div>
				</form>
			</div>
	</section>
	<%@ include file="../include/footer.jsp" %>
	</body>
</html>