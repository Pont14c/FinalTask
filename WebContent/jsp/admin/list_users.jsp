<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 700px; width: 700px;">
		<c:if test="${listUsers != null}">
			<c:if test="${listUsers.size()>0}">
				<div class="table-container" style="width: 700px; height: 700px">
					<table style="border: black; border-style: ridge">
						<thead>
							<tr>
								<th><fmt:message key="list_users.login" /></th>
								<th><fmt:message key="list_users.fname" /></th>
								<th><fmt:message key="list_users.lname" /></th>
								<th><fmt:message key="list_users.status" /></th>
								<th><fmt:message key="list_users.change_status" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="user" items="${listUsers}">
								<tr>
									<td>${user.getLogin()}</td>
									<td>${user.getFirstName()}</td>
									<td>${user.getLastName()}</td>
									<td><c:choose>
											<c:when test="${user.getRoleId()==0}">
												<fmt:message key="list_users.admin" />
											</c:when>
											<c:when test="${user.getRoleId()==1}">
												<fmt:message key="list_users.client" />
											</c:when>
										</c:choose></td>
									<td><form id="changeStatus"
											action="/FinalTask/controller?command=changeRole"
											method="post">
											<input type="hidden" name="command" value="changeRole" /><input
												type="hidden" name="changeRoleLogin"
												value="${user.getLogin()}" /> <input type="hidden"
												name="changeRoleId" value="${user.getRoleId()}" /><input
												type="submit" id="buttonInTable"
												value="<fmt:message key="change_status_jsp.client_choise" />"
												name="changeRole" />
										</form></td>
								<tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:if>
		</c:if>
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>