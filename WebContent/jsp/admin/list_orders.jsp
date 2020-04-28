<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 700px; width: 700px;">
		<c:if test="${listOrders != null}">
			<c:if test="${listOrders.size()>0}">
				<div class="table-container" style="width: 700px; height: 250px">
					<table style="border: black; border-style: ridge">
						<thead>
							<tr>
								<th><fmt:message key="list_ordres.id_Order" /></th>
								<th><fmt:message key="change_status_jsp.login_user" /></th>
								<th><fmt:message key="list_orders_jsp.price" /></th>
								<th><fmt:message key="list_orders_jsp.places" /></th>
								<th><fmt:message key="list_orders_jsp.comfort" /></th>
								<th><fmt:message key="change_status_jsp.date_in" /></th>
								<th><fmt:message key="change_status_jsp.date_out" /></th>
								<th><fmt:message key="change_status_jsp.reserve" /></th>
								<th><fmt:message key="change_status_jsp.alternative" /></th>
								<th><fmt:message key="change_status_jsp.remove" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="order" items="${listOrders}">
								<tr>
									<td>${order.getId()}</td>
									<td>${order.getLoginUser()}</td>
									<td>${order.getPrice()}</td>
									<td>${order.getPlace()}</td>
									<td>${order.getRoomStars()}</td>
									<td>${order.getDateIn()}</td>
									<td>${order.getDateOut()}</td>
									<td><form id="adminReserve"
											action="/FinalTask/controller?command=reserveFortune"
											style="height: 100%;" method="post">
											<input type="hidden" name="command" value="reserveFortune" /><input
												type="hidden" name="adminReserve"
												value="${order.getLoginUser()}" /><input type="submit"
												id="buttonInTable"
												value="<fmt:message key="change_status_jsp.client_choise" />"
												name="reserve" />
										</form></td>
									<td><form id="adminReserve"
											action="/FinalTask/controller?command=listRoomAdmin"
											style="height: 100%;" method="post">
											<input type="hidden" name="command" value="listRoom" /><input
												type="hidden" name="adminChoiseUser"
												value="${order.getLoginUser()}" /><input type="hidden"
												name="dateIn" value="${order.getDateIn()}" /><input
												type="hidden" name="price" value="${order.getPrice()}" /><input
												type="hidden" name="dateOut" value="${order.getDateOut()}" /><input
												type="submit" id="buttonInTable"
												style="padding-left: 15px; padding-right: 20px;"
												value="<fmt:message key="change_status_jsp.client_choise" />"
												name="reserve" />
										</form></td>
									<td><form id="adminReserve"
											action="/FinalTask/controller?command=removeOrder"
											style="height: 100%;" method="post">
											<input type="hidden" name="command" value="removeOrder" /><input
												type="hidden" name="adminReserve"
												value="${order.getLoginUser()}" /><input type="submit"
												id="buttonInTable"
												style="min-height: 25px; margin-top: 0px;"
												value="<fmt:message key="change_status_jsp.client_choise" />"
												name="reserve" />
										</form></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<c:if test="${listRooms != null}">
					<h3>
						<fmt:message key="change_status_jsp.alternative" />
						${loginBook}
					</h3>
					<c:if test="${listRooms.size()>0}">
						<div class="table-container" style="height: 350px">
							<table id="myTable"
								style="border: black; border-style: ridge; width: 500px;">
								<thead>
									<tr>
										<th onclick="sortTable(0)"><fmt:message
												key="change_status_jsp.room_number" /></th>
										<th onclick="sortTable(1)"><fmt:message
												key="list_orders_jsp.price" /></th>
										<th onclick="sortTable(2)"><fmt:message
												key="list_orders_jsp.places" /></th>
										<th onclick="sortTable(3)"><fmt:message
												key="list_orders_jsp.comfort" /></th>
										<th><fmt:message key="change_status_jsp.reserve" /></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="room" items="${listRooms}">
										<tr>
											<td>${room.getId()}</td>
											<td>${room.getPrice()}</td>
											<td>${room.getPlacesInRoom()}</td>
											<td>${room.getRoomLvl()}</td>
											<td><form id="clientChoise"
													action="/FinalTask/controller?command=reserve"
													method="post">
													<input type="hidden" name="adminChoise"
														value="${room.getId()}" /> <input type="submit"
														style="height: 25px; margin-top: 0px; width: 100%; text-align: center; display: block;"
														value="<fmt:message key="list_orders_jsp.book" />"
														name="reserve" />
												</form></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:if>
					<c:if test="${listRooms.size()==0}">
						<h2>
							<fmt:message key="change_status_jsp.title_no_room" />
						</h2>
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${listOrders.size()==0}">
				<h2>
					<fmt:message key="list_orders.clientOrders" />
				</h2>
			</c:if>
		</c:if>
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>