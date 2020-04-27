<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 700px; width: 700px;">
		<c:if test="${listNotPaid.size() >0}">
			<div class="table-container" style="width: 700px; height: 200px">
				<table style="border: black; border-style: ridge">
					<thead>
						<tr>
							<th><fmt:message key="change_status_jsp.id_order" /></th>
							<th><fmt:message key="change_status_jsp.login_user" /></th>
							<th><fmt:message key="change_status_jsp.room_number" /></th>
							<th><fmt:message key="change_status_jsp.date_in" /></th>
							<th><fmt:message key="change_status_jsp.date_out" /></th>
							<th><fmt:message key="change_status_jsp.status" /></th>
							<th><fmt:message key="change_status_jsp.paid" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="book" items="${listNotPaid}">
							<tr>
								<td>${book.getId()}</td>
								<td>${book.getLoginUser()}</td>
								<td>${book.getRoomId()}</td>
								<td>${book.getDateIn()}</td>
								<td>${book.getDateOut()}</td>
								<td><fmt:message key="change_status_jsp.status_name" /></td>
								<td><form id="adminSetPay"
										action="/FinalTask/controller?command=changeStatus"
										method="post">
										<input type="hidden" name="command" value="changeStatus" /><input
											type="hidden" name="adminReserve"
											value="${book.getLoginUser()}" /><input type="submit"
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
		<c:if test="${listNotPaid.size() == 0}">
			<h2>
				<fmt:message key="change_status_jsp.unpaid_title" />
			</h2>
		</c:if>
		<h3>
			<fmt:message key="change_status_jsp.title_condition" />
		</h3>
		<form id="adminSetStatus" style="height: 100%;"
			action="/FinalTask/controller?command=selectByDay" method="post">
			<input type="hidden" name="command" value="listRoom" />
			<div class="styleOrder" style="margin: auto; width: 65%;">
				<label for="dateIn"><fmt:message
						key="change_status_jsp.choose_day" /></label> <input type="date"
					id="dateIn" name="dateIn" required><br>
				<div class="bookButton">
					<input type="submit"
						value="<fmt:message key="change_status_jsp.select_dates" />"
						name="selectBooks">
				</div>
			</div>
		</form>
		<c:if test="${listBooks.size()>0}">
			<div class="sortChoise">
				<form id="sortChoise"
					action="/FinalTask/controller?command=sortListBooks" method="post">
					<input type="hidden" name="command" value="sortListBooks" /> <label
						for="order"><fmt:message
							key="change_status_jsp.choose_sort" /></label> <select id="sortChoise"
						name="sortChoise" onchange="this.form.submit();">
						<option selected disabled><fmt:message
								key="change_status_jsp.sort_list" /></option>
						<option value="roomAsc"><fmt:message
								key="change_status_jsp.asc_room" /></option>
						<option value="roomDesc"><fmt:message
								key="change_status_jsp.desc_room" /></option>
						<option value="statusAsc"><fmt:message
								key="change_status_jsp.asc_status" /></option>
						<option value="statusDesc"><fmt:message
								key="change_status_jsp.desc_status" /></option>
					</select>
				</form>
			</div>
			<div class="table-container" style="height: 300px; width: 700px">
				<table style="border: black; border-style: ridge;">
					<thead>
						<tr>
							<th><fmt:message key="change_status_jsp.room_number" /></th>
							<th><fmt:message key="change_status_jsp.date_in" /></th>
							<th><fmt:message key="change_status_jsp.date_out" /></th>
							<th><fmt:message key="change_status_jsp.status" /></th>
							<th><fmt:message key="change_status_jsp.date_pay" /></th>
							<th><fmt:message key="change_status_jsp.login_user" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="book" items="${listBooks}">
							<tr>
								<td>${book.getRoomId()}</td>
								<td>${book.getDateIn()}</td>
								<td>${book.getDateOut()}</td>
								<td><c:choose>
										<c:when test="${book.getStatus()==0}">
											<fmt:message key="change_status_jsp.available" />
										</c:when>
										<c:when test="${book.getStatus()==1}">
											<fmt:message key="change_status_jsp.reserved" />
										</c:when>
										<c:when test="${book.getStatus()==2}">
											<fmt:message key="change_status_jsp.booked" />
										</c:when>
										<c:when test="${book.getStatus()==3}">
											<fmt:message key="change_status_jsp.unavailable" />
										</c:when>
									</c:choose></td>
								<td>${book.getDatePaid()}</td>
								<td>${book.getLoginUser()}</td>
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
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>