<%@ page pageEncoding="UTF-8" %>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Register" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<c:if test="${not empty user}">
		<%
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		%>
	</c:if>
	<div class="register">
		<form action="controller" method="post">
			<h2><fmt:message key="register_jsp.register_title" /></h2>
			<input type="hidden" name="command" value="register"/>
			<input type="text" placeholder="<fmt:message key="register_jsp.register_login" />" name="login" required>
			<input type="password" placeholder="<fmt:message key="register_jsp.register_password" />"name="password" required>
			<input type="text" placeholder="<fmt:message key="register_jsp.register_fName" />" name="fName" required>
			<input type="text" placeholder="<fmt:message key="register_jsp.register_lName" />" name="lName" required>
			<input type="submit" value="<fmt:message key="register_jsp.register_title" />" name="register"/>
		</form>
	</div>
	
</body>
</html>