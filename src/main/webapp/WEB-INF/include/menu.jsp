<c:if test="${!empty sessionScope.notification}">
	<div class="notification">${sessionScope.notification}</div>
</c:if>
<nav>
<span class="appereance_h1"><h1>PayMyBuddy</h1></span>
	<ul>
		<li><a href="/">Home</a></li>
		<c:choose>
			<c:when test="${!empty sessionScope.mail}">
				<li><a href="/transfert">Transfer</a></li>
				<li><a href="/profile">Profile</a></li>
				<li><a href="/contact">Contact</a></li>
			</c:when>
			<c:when test="${empty sessionScope.mail}">
				<li><a href="/contact">Contact</a></li>
				<li><a href="/register">Register</a></li>
			</c:when>
		</c:choose>

		<c:choose>
			<c:when test="${!empty sessionScope.mail}"><li><a href="/logout">Logout</a></li></c:when>
			<c:when test="${empty sessionScope.mail}"><li><a href="/login">Login</a></li></c:when>
		</c:choose>

	</ul>
</nav>
<section class="section_path">
	<div class="content_access_path">
		<c:forEach items="${accessPath}" var="span">
			<span class="span_access_path"><c:out value="${span}"/></span>
		</c:forEach>
	</div>
</section>