<%@ include file="../include/header.jsp" %>
        <title>Profile</title>
    </head>
    <body>
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
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
				<c:if test="${user.accountBalance > 0}">
					+
				</c:if>
				<c:out value="${user.accountBalance}"/>
				&euro;</p>
				<form method="POST" action="/refundMyAccount" class="control_add_money">
				<div class="content_input_money">
					<input type="text" name="amount" placeholder="Amount">
					<input type="text" name="description" placeholder="Description">
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
				</div>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Add money" name="submit_refund" class="btn btn_pay">
				</form>
			</div>
	</section>

	<section>
		<h2>My friends</h2>
		<table class="table_transactions table_friend">
			<thead>
			<tr>
				<th>Firstname</th>
				<th>Lastname</th>
				<th>Mail</th>
				<th>Action</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${user.listFriends}" var="friend">
				<tr>
					<td><c:out value="${friend.firstname}"/></td>
					<td><c:out value="${friend.lastname}"/></td>
					<td><c:out value="${friend.mail}"/></td>
					<td><a class="btn_delete_friend" href="/deleteFriend?friend=<c:out value="${friend.mail}"/>"><img src="/CSS/img/cross.png" alt="Croix de suppression"></a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</section>
	<%@ include file="../include/footer.jsp" %>
	</body>
</html>