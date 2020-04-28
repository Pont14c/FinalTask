<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>
<%@ taglib prefix="myTag" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Register" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>

	<div class="register">
		<h2>
			<myTag:MyTag />
		</h2>
		<div class="bookButton">
<<<<<<< HEAD
			<button onclick="goBack()">
=======
			<button onclick="history.back()">
>>>>>>> d7c153ab667f5beab95a5e96bdfdd25e9eee1567
				<fmt:message key="messeges_jsp.back" />
			</button>
		</div>
	</div>
<<<<<<< HEAD
	<script type="text/javascript">
		function goBack() {
			window.history.back();
			location.reload(); 
			window.location.replace("http://localhost:8080/FinalTask/controller?command=chooseRoom");
		}
	</script>
=======
>>>>>>> d7c153ab667f5beab95a5e96bdfdd25e9eee1567
</body>
</html>