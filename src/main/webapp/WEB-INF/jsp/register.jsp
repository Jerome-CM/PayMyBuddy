<%@ include file="../include/header.jsp" %>
        <title>Register</title>
    </head>
    <body>
    
    <%@ include file="../include/menu.jsp" %>
    
    <br>
    <p class="center">Welcome, you are a new user in Pay My Buddy application, please complete your profile for start experience</p>

	<section class="money">
			<div>
				<h2>My informations</h2>
			</div>
			<div class="control_money">
				<form method="POST" action="/registerUser" class="container_money">
					<input type="text" name="firstname" placeholder="Firstname">
					<input type="text" name="lastname" placeholder="Lastname">
					<input type="email" name="mail" placeholder="E-mail">
					<input type="password" name="password" placeholder="Password">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Save" name="submit_infos" class="btn btn_pay">
				</form>
			</div>
	</section>
	
	<section class="money">
			<div>
				<h2>Your Bank</h2>
			</div>
			<div class="control_money">
				<form method="" action="" class="container_money ">
					<div class="container_input_label">
						<label>Your bank</label>
						<input type="text" name="bank" placeholder="Your bank" value="Caribbean Bank">
					</div>
					<div class="container_input_label">
						<label>Your account number</label>
						<input type="text" name="account" placeholder="Account number" value="FR010203040506070809">
					</div>
					<div class="container_input_label">
						<label>Key</label>
						<input type="text" name="key" placeholder="Account key" value="777">
					</div>
				</form>
			</div>
	</section>
	<%@ include file="../include/footer.jsp" %>
	</body>
</html>

