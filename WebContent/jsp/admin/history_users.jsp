<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 700px; width: 700px;">
	
		<c:if test="${listHistory.size() >0}">
			<div class="table-container" style="width: 700px; height: 200px">
				<table style="border: black; border-style: ridge">
					<thead>
						<tr>
							<th><fmt:message key="change_status_jsp.login_user" /></th>
							<th><fmt:message key="change_status_jsp.sum_bookings" /></th>
							<th><fmt:message key="change_status_jsp.sum_paid" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${listHistory}">
							<tr>
								<td>${user.getLogin()}</td>
								<td>${user.getCountBook()}</td>
								<td>${user.getSumPay()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<c:if test="${listHistory.size()==0}">
			<h2>
				<fmt:message key="change_status_jsp.history_no_room" />
			</h2>
		</c:if>
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>