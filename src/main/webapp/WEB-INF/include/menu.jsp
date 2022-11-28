<c:out value="${!empty sessionScope.notification ? sessionScope.notification : ''}"/>
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
<section>
	<div class="content_access_path">
		<c:forEach items="${accessPath}" var="span">
			<span class="span_access_path"><c:out value="${span}"/></span>
		</c:forEach>
	</div>
</section>