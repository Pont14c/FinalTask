<%@ page pageEncoding="UTF-8" %>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Register" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	
	<div class="register">
		<h2><fmt:message key="messeges_jsp.room_not_exist" /></h2>
		<div class="bookButton">
			<button onclick="history.back()">
				<fmt:message key="messeges_jsp.back" />
			</button>
		</div>
	</div>
</body>
</html>