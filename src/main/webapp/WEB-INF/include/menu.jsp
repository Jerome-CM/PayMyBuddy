<c:if test="${!empty sessionScope.notification}">
	<div class="notification">${sessionScope.notification}</div>
</c:if>
<nav>
<span class="appereance_h1"><h1>PayMyBuddy</h1></span>
	<ul>
		<li><a href="/home">Home</a></li>
		<li><a href="/transfert">Transfer</a></li>
		<li><a href="/profile">Profile</a></li>
		<li><a href="/contact">Contact</a></li>
		<li><a href="/home">Logout</a></li>
	</ul>
</nav>
<section class="section_path">
	<div class="content_access_path">
		<c:forEach items="${accessPath}" var="span">
			<span class="span_access_path"><c:out value="${span}"/></span>
		</c:forEach>
	</div>
</section>