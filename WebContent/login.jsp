<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Login" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<c:if test="${not empty user}">
		<%
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		%>
	</c:if>
	<div class="login">
		<form action="controller" method="post">
			<input type="hidden" name="command" value="login" />
			<h2>
				<fmt:message key="login_jsp.login_title" />
			</h2>
			<input type="text"
				placeholder="<fmt:message key="login_jsp.enter_login" />"
				name="login" required> <input type="password"
				placeholder="<fmt:message key="login_jsp.enter_password" />"
				name="password" required> <input type="submit" name="login"
				value="<fmt:message key="login_jsp.login" />" />
		</form>
	</div>

</body>
</html>