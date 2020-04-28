<%@ page pageEncoding="UTF-8" %>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Login" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="register">
		<h2><fmt:message key="messeges_jsp.login_engaged" /></h2>
		<div class="bookButton">
						<button onclick="history.goBack()"><fmt:message key="messeges_jsp.back" /></button>
				</div>
	</div>
	<script type="text/javascript">
		function goBack() {
			window.history.back();
			location.reload(); 
			window.location.replace("http://localhost:8080/FinalTask/login.jsp");
		}
	</script>
</body>
</html>