<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 700px; width: 500px;">
		<form id="booking" action="/FinalTask/controller?command=listRoom"
			method="post">
			<input type="hidden" name="command" value="listRoom" />
			<h2>
				<fmt:message key="leave_order.choose_parameters" />
			</h2>
			<div class="styleOrder" style="margin: auto">
				<label for="dateIn"><fmt:message
						key="change_status_jsp.choose_arrival_date" /></label> <input type="date"
					id="dateIn" name="dateIn" required><br> <label for="dateOut"><fmt:message
						key="change_status_jsp.choose_leaving_date" /> </label> <input
					type="date" id="dateOut" name="dateOut" required>
				<div class="bookButton">
					<input type="submit"
						value="<fmt:message key="change_status_jsp.select_dates" />"
						name="listRoom">
				</div>
			</div>
		</form>
		<c:if test="${listRooms != null}">
			<c:if test="${listRooms.size()>0}">
				<div class="table-container" style="height: 500px">
					<table id="myTable"
						style="border: black; border-style: ridge; width: 420px">
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
								<th style="width: 65px;"><fmt:message
										key="list_orders_jsp.book" /></th>
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
											action="/FinalTask/controller?command=reserve" method="post">
											<input type="hidden" name="command" value="reserve" /><input
												type="hidden" name="price" value="${room.getPrice()}" /> <input
												type="hidden" name="clientChoise" value="${room.getId()}" /><input
												type="submit" id="buttonInTable"
												value="<fmt:message key="change_status_jsp.client_choise" />"
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
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>