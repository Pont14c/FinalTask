<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Register" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>

	<div class="register">
		<h2>
			<fmt:message key="messeges_jsp.order_reserved" />
			${priceForNumber}
		</h2>
		<div class="bookButton">
			<button onclick="goBack()">
				<fmt:message key="messeges_jsp.back" />
			</button>
		</div>
	</div>
	<c:if test="${userRole.name == 'admin' }">
		<script>
			function goBack() {
				window.history.back();
				location.reload();
				window.location.replace("http://localhost:8080/FinalTask/controller?command=listOrders");
			}
		</script>
	</c:if>
	<c:if test="${userRole.name == 'client' }">
		<script>
			function goBack() {
				window.history.back();
				location.reload();
				window.location.replace("http://localhost:8080/FinalTask/controller?command=chooseRoom");
			}
		</script>
	</c:if>
</body>
</html>