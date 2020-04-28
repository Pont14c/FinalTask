<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 700px; width: 670px;">
		<c:if test="${historyOrders != null}">
			<c:if test="${historyOrders.size()==0}">
				<h2>
					<fmt:message key="list_user_orders_jsp.title_no_rooms" />
				</h2>
			</c:if>
			<c:if test="${historyOrders.size()>0 }">
				<div class="table-container" style="width: 670px;">
					<table style="border: black; border-style: ridge;">
						<thead>
							<tr>
								<th><fmt:message key="change_status_jsp.login_user" /></th>
								<th><fmt:message key="list_user_orders_jsp.room_number" /></th>
								<th><fmt:message key="change_status_jsp.date_in" /></th>
								<th><fmt:message key="change_status_jsp.date_out" /></th>
								<th><fmt:message key="change_status_jsp.status" /></th>
								<th><fmt:message key="change_status_jsp.date_las" /></th>
								<th><fmt:message key="change_status_jsp.remove" /></th>
								<th><fmt:message key="change_status_jsp.pay" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${historyOrders}">
								<tr>
									<td>${order.getLoginUser()}</td>
									<td>${order.getRoomId()}</td>
									<td>${order.getDateIn()}</td>
									<td>${order.getDateOut()}</td>
									<td><c:choose>
											<c:when test="${order.getStatus() == '1'}">
												<fmt:message key="change_status_jsp.reserved" />
											</c:when>
											<c:otherwise>
												<fmt:message key="change_status_jsp.booked" />
											</c:otherwise>
										</c:choose></td>
									<td>${order.getDatePaid() }</td>
									<td><form id="clientChoise" style="height: 100%;"
											action="/FinalTask/controller?command=removeBook"
											method="post">
											<input type="hidden" name="command" value="removeBook" />
											<input type="hidden" name="statusPaid" value="${order.getStatus()}" /><input
												type="hidden" name="removeIdRoom"
												value="${order.getRoomId()}" /> <input type="submit"
												id="buttonInTable"
												value="<fmt:message key="change_status_jsp.client_choise" />"
												name="reserve" />
										</form></td>
									<td><form id="clientChoise" style="height: 100%;"
											action="/FinalTask/controller?command=payBook"
											method="post">
											<input type="hidden" name="command" value="payBook" />
											<input type="hidden" name="statusPaid" value="${order.getStatus()}" /><input
												type="hidden" name="removeIdRoom"
												value="${order.getRoomId()}" /> <input type="submit"
												id="buttonInTable"
												value="<fmt:message key="change_status_jsp.client_choise" />"
												name="reserve" />
										</form></td>
								</tr>
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