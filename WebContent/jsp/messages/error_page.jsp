<%@ page pageEncoding="UTF-8"%>
<%@ page isErrorPage="true"%>
<%@ page import="java.io.PrintWriter"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<html>

<c:set var="title" value="Error" scope="page" />
<%@ include file="/jspf/head.jspf"%>

<body>
	<h2 class="error">
		<fmt:message key="messeges_jsp.error" />
	</h2>
	<c:set var="code"
		value="${requestScope['javax.servlet.error.status_code']}" />
	<c:set var="message"
		value="${requestScope['javax.servlet.error.message']}" />
	<c:set var="exception"
		value="${requestScope['javax.servlet.error.exception']}" />

	<c:if test="${not empty code}">
		<h3>
			<fmt:message key="messeges_jsp.error_code" />
			${code}
		</h3>
	</c:if>

	<c:if test="${not empty message}">
		<h3>${message}</h3>
	</c:if>

	<c:if test="${not empty exception}">
		<%
			exception.printStackTrace(new PrintWriter(out));
		%>
	</c:if>

	<c:if test="${not empty sessionScope.errorMessage}">
		<h3>
			<fmt:message key="messeges_jsp.message_login/password" />
		</h3>
		<c:remove var="errorMessage" scope="session" /> 
	</c:if>
	<c:if test="${not empty requestScope.errorMessage}">
		<h3>
			<fmt:message key="messeges_jsp.message" />
		</h3>
	</c:if>
</body>
</html>