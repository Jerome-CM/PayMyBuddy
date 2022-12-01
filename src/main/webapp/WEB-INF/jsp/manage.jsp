<%@ taglib prefix="input" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../include/header.jsp" %>
        <title>Manage</title>
    </head>
    <body>
    
    <%@ include file="../include/menu.jsp" %>

	<section class="money">
			<div class="header_money">
				<h2>Users</h2>
				<p><c:out value="${nbrUsers}"/></p>
			</div>
	</section>

	<section class="money">
		<div class="header_money">
			<h2>Transactions</h2>
			<p><c:out value="${nbrTransactions}"/></p>
		</div>
	</section>

	<section class="money">
		<div class="header_money">
			<h2>Money Exchange in app</h2>
			<p><c:out value="${totalMoney}"/>€</p>
		</div>
	</section>

	<section class="money">
		<div class="header_money">
			<h2>Money in wallet</h2>
			<p><c:out value="${moneyInWallet}"/>€</p>
		</div>
	</section>


	<%@ include file="../include/footer.jsp" %>
	</body>
</html>