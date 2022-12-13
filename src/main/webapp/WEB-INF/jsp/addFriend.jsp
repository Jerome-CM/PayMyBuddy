<%@ include file="../include/header.jsp" %>
        <title>Add a friend</title>
    </head>
    <body>
    <%@ include file="../include/menu.jsp" %>
	<section class="money">
			<div>
				<h2>Add a connection</h2>
			</div>

			<div class="control_money">
				<form method="POST" action="/addFriend" class="container_choose_friend">
					<select name="mail">
						<option value="choose">Choose a user</option>
						<c:forEach items="${ listUsers }" var="user">
							<option value="<c:out value="${user.mail}"/>"> <c:out value="${user.firstname} ${user.lastname} - ${user.mail}"/></option>
						</c:forEach>
					</select>
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Add" name="submit" class="btn btn_pay">
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
		        </tr>
		    </thead>
		    <tbody>
			<c:forEach items="${listMyFriends.getListFriends()}" var="friend">
		    	<tr>
		            <td><c:out value="${friend.firstname}"/></td>
		            <td><c:out value="${friend.lastname}"/></td>
		            <td><c:out value="${friend.mail}"/></td>
		        </tr>
			</c:forEach>
		    </tbody>
		</table>
	</section>
	<%@ include file="../include/footer.jsp" %>
	</body>
</html>