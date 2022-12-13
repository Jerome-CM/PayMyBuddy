<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../include/header.jsp" %>
        <title>Transfer</title>
    </head>
    <body>
    
    <%@ include file="../include/menu.jsp" %>

	<section class="money">
			<div class="header_money">
				<h2>Send Money</h2>
				<a class="btn" href="/addFriend">Add Connection</a>
			</div>
			<div class="control_money">
				<form method="POST" action="/sendMoney" class="container_money command_send_money">
					<select name="mailFriend">
						<option value="">Choose a connection</option>
						<c:forEach items="${ userDTO.listFriends }" var="user">
							<option value="<c:out value="${user.mail}"/>"> <c:out value="${user.firstname} ${user.lastname} - ${user.mail}"/></option>
						</c:forEach>
					</select>
					<input type="number" name="amount" value="0">
					<input type="text" name="description" placeholder="Description">
					<input type="hidden" name="mail_hidden" value="${sessionScope.mail}">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<input type="submit" value="Pay" name="submit" class="btn btn_pay">
				</form>
			</div>
	</section>
	<c:if test="${!empty listTransacDTO}">
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
			<c:forEach items="${listTransacDTO}" var="transac">
		    	<tr>
		            <td><c:out value="${transac.friendFirstname}"/></td>
		            <td><c:out value="${transac.description}"/></td>
		            <td><c:out value="${transac.amount}"/>&euro;</td>
		        </tr>
			</c:forEach>
		    </tbody>
		</table>
		<div class="pagination">
			<table>
				<tbody>
					<tr>
						<c:if test="${sessionScope.page > 1}"><td><a class="pages" href="/previousPageTransaction?page=${sessionScope.page -1}"><<</a></td></c:if>
						<c:forEach var="i" begin="1" end="${nbrPages}">
							<td><a class="pages" href="?page=<c:out value="${i}"/>"><c:out value="${i}"/></a></td>
						</c:forEach>
						<c:if test="${sessionScope.page < nbrPages}"><td><a class="pages" href="/previousPageTransaction?page=${sessionScope.page +1}">>></a></td></c:if>
					</tr>
				</tbody>
			</table>
		</div>
	</section>
	</c:if>
	<%@ include file="../include/footer.jsp" %>
	</body>
</html>