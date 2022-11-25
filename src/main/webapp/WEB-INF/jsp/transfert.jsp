<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../include/header.jsp" %>
        <title>dashboard</title>
    </head>
    <body>
    
    <%@ include file="../include/menu.jsp" %>

	<section class="money">
			<div class="header_money">
				<h2>Send Money</h2>
				<a class="btn" href="/addFriend">Add Connection</a>
			</div>
			<div class="control_money">
				<form method="POST" action="/sendMoney" class="container_money">
					<select name="mailFriend">
						<option value="">Choose a connection</option>
						<c:forEach items="${ listMyFriends }" var="user">
							<option value="<c:out value="${user.mail}"/>"> <c:out value="${user.firstname} ${user.lastname} - ${user.mail}"/></option>
						</c:forEach>
					</select>
					<input type="number" name="amount" value="0">
					<input type="text" name="description" placeholder="Description">
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
					<input type="submit" value="Pay" name="submit" class="btn btn_pay">
				</form>
			</div>
	</section>
	<c:if test="${!empty listTransac}">
	<section>
		<h2>My Transactions</h2>
		<table class="table_transactions">
			<thead>
		        <tr>
		            <th>Connections</th>
		            <th>Description</th>
		            <th>Amount</th>
		        </tr>
		    </thead>
		    <tbody>
			<c:forEach items="${listTransac}" var="transac">
		    	<tr>
		            <td><c:out value="${transac.friendFirstname}"/></td>
		            <td><c:out value="${transac.description}"/></td>
		            <td><c:out value="${transac.amount}"/></td>
		        </tr>
			</c:forEach>
		    </tbody>
		</table>
		<div class="pagination">
			<table>
				<tbody>
					<tr>
						<td><<</td>
						<td>1</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
						<td>5</td>
						<td>>></td>
					</tr>
				</tbody>
			</table>
		</div>
	</section>
	</c:if>
	</body>
</html>