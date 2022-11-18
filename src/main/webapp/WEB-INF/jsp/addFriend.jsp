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
					<select name="friend">
						<option value="">Choose a user</option>
						<option value="">Jérôme Bouteveille - bouteveillejerome@hotmail.fr</option>
						<!--  Affiche les options avec une boucle jstl -->
					</select>
					<input type="submit" value="Save" name="submit" class="btn btn_pay">
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
		    	<tr>
		            <td>John</td>
		            <td>Doe</td>
		            <td>j.doe@gmail.com</td>
		            <td>X</td>
		        </tr>
		    </tbody>
		</table>
	</section>
	</body>
</html>