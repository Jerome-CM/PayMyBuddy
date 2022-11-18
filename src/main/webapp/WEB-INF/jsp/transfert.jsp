<%@ include file="../include/header.jsp" %>
        <title>dashboard</title>
    </head>
    <body>
    
    <%@ include file="../include/menu.jsp" %>

	<section class="money">
			<div class="header_money">
				<h2>Send Money</h2>
				<a class="btn" href="/testJEE/dashboard">Add Connection</a>
			</div>
			<div class="control_money">
				<form method="POST" action="/sendMoney" class="container_money">
				
					<select name="friend">
						<option value="">Choose a connection</option>
						<!--  Affiche les options avec une boucle jstl -->
					</select>
					<input type="number" name="amount" value="0">
					<input type="submit" value="Pay" name="submit" class="btn btn_pay">
				</form>
			</div>
	</section>
	
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
		    	<tr>
		            <td>Hayley</td>
		            <td>Restaurant bill share</td>
		            <td>10€</td>
		        </tr>
		        <tr>
		            <td></td>
		            <td></td>
		            <td></td>
		        </tr>
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
	</body>
</html>