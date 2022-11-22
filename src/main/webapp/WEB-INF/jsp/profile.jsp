<%@ include file="../include/header.jsp" %>
        <title>Add a friend</title>
    </head>
    <body>
    <c:choose>
		<c:when test="${!empty modifInfos && modifInfos == yes}">Yours informations as been changed</c:when>
		<c:when test="${!empty modifPassword && modifPassword == yes}">Your password as been changed</c:when>
		<c:when test="${!empty refund && refund == yes}">+${amount}€ added in your account</c:when>
		<c:otherwise> </c:otherwise>
	</c:choose>
    <%@ include file="../include/menu.jsp" %>

	<section class="money">
			<div>
				<h2>My informations</h2>
				
			</div>
			<div class="control_money">
				<form method="POST" action="/modifyingUserInfos" class="container_money control_informations">
					<input type="text" name="firstname" placeholder="Firstname" value="<c:out value="${user.firstname}"/>">
					<input type="text" name="lastname" placeholder="Lastname" value="<c:out value="${user.lastname}"/>">
					<input type="text" name="mail" placeholder="Mail" value="<c:out value="${user.mail}"/>">
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
					<input type="submit" value="Save" name="submit_infos" class="btn btn_pay">
				</form>
			</div>
	</section>
	
	<section class="money">
			<div>
				<h2>Password</h2>
			</div>
			<div class="control_money">
				<form method="POST" action="/modifyingUserPassword" class="container_money">
					<input type="text" name="password" placeholder="New password">
					<input type="text" name="confirmPassword" placeholder="Confirm your new password">
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
					<input type="submit" value="Change" name="submit_password" class="btn btn_pay">
				</form>
			</div>
	</section>
	
	<section class="money">
			<div>
				<h2>Balance</h2>
			</div>
			<div class="control_money container_money">
			
			<p>
				<c:if test="${userBalance > 0}">
					+
				</c:if>
				<c:out value="${userBalance}"/>
			€</p>
				<form method="POST" action="/refundMyAccount" class="control_add_money">
				<div class="content_input_money">
					<input type="text" name="amount" placeholder="Amount">
					<input type="text" name="description" placeholder="Description">
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
				</div>
					<input type="submit" value="Add money" name="submit_refund" class="btn btn_pay">
				</form>
			</div>
	</section>
	
	<section>
		<h2>My friends</h2>
		<table class="table_transactions">
			<thead>
		        <tr>
		            <th>Firstname</th>
		            <th>Lastname</th>
		            <th>Action</th>
		        </tr>
		    </thead>
		    <tbody>
		    	<tr>
		            <td>John</td>
		            <td>Doe</td>
		            <td>X</td>
		        </tr>
		        <tr>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
		    </tbody>
		</table>
	</section>
	</body>
</html>