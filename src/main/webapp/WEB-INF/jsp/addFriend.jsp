<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
						<option value="test">Choose a user</option>
						<c:forEach items="${ listUsers }" var="user">
							<option value="<c:out value="${user.mail}"/>"> <c:out value="${user.firstname} ${user.lastname} - ${user.mail}"/></option>
						</c:forEach>
					</select>
					// TODO put MyId
					<input type="hidden" name="myId" value="3">
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