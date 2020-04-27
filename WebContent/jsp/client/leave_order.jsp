<%@ page pageEncoding="UTF-8"%>
<%@ include file="/jspf/directive/taglib.jspf"%>

<!DOCTYPE html>
<html>
<c:set var="title" value="Rooms list" scope="page" />
<%@ include file="/jspf/head.jspf"%>
<body>
	<%@ include file="/jspf/header.jspf"%>
	<div class="chooseRoom" style="height: 250px; width: fit-content;">
		<form id="booking" action="/FinalTask/controller?command=leaveOrder"
			method="post">
			<input type="hidden" name="command" value="leaveOrder" />
			<h2>
				<fmt:message key="leave_order.choose_parameters" />
			</h2>
			<label for="price"><fmt:message
					key="leave_order.choose_prive" /></label> <select name="price">
				<option value="100">100$</option>
				<option value="150">150$</option>
				<option value="200">200$</option>
			</select> <br> <label for="numberOfPeople"><fmt:message
					key="leave_order.choose_people" /></label> <select name="places">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
			</select> <br> <label for="quality"><fmt:message
					key="leave_order.choose_comfort" /></label> <select name="roomLvl">
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select> <br> <label for="dateIn"><fmt:message
					key="change_status_jsp.choose_arrival_date" /></label> <input type="date"
				id="dateIn" name="dateIn" required><br> <label for="dateOut"><fmt:message
					key="change_status_jsp.choose_leaving_date" /></label> <input type="date"
				id="dateOut" name="dateOut" required><br>
			<div class="bookButton">
				<input type="submit"
					value="<fmt:message key="change_status_jsp.choose_leaving_order" />"
					name="leaveOrder">
			</div>
		</form>
	</div>
	<%@ include file="/jspf/footer.jspf"%>
</body>
</html>