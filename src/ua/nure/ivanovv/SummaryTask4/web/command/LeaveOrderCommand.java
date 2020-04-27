package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.sql.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.ReserveDao;
import ua.nure.ivanovv.SummaryTask4.db.Role;
import ua.nure.ivanovv.SummaryTask4.db.UserDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class LeaveOrderCommand extends Command {

	private static final long serialVersionUID = 3380508539056326487L;

	private static final Logger log = Logger.getLogger(LeaveOrderCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		String forward = "";
		String reserve = request.getParameter("reserve");
		log.trace("Request parameter: reserve --> " + reserve);

		Date dateIn = null;
		Date dateOut = null;
		try {
			dateIn = Date.valueOf(request.getParameter("dateIn"));
			dateOut = Date.valueOf(request.getParameter("dateOut"));
			if (!validateDate(dateIn, dateOut)) {
				return Path.PAGE_INCORRECT_DATE;
			} else {
				session.setAttribute("dateIn", dateIn);
				session.setAttribute("dateOut", dateOut);
			}
		} catch (IllegalArgumentException e) {
			log.trace("Empty dates");
			return Path.PAGE_INCORRECT_DATE;
		}

		int price = Integer.parseInt(request.getParameter("price"));
		int places = Integer.parseInt(request.getParameter("places"));
		int roomLvl = Integer.parseInt(request.getParameter("roomLvl"));

		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();
		Role userRole = Role.getRole(user);
		log.trace("userRole --> " + userRole);

		Boolean leaveOrder = (Boolean) session.getAttribute("orderLeaved");

		if (leaveOrder == null || leaveOrder == false) {
			forward = new ReserveDao().leaveOrder(roomLvl, dateIn, dateOut, loginUser, price, places);
			leaveOrder = true;
			session.setAttribute("orderLeaved", leaveOrder);
			log.trace("client leave order -->" + userRole);
		} else {
			forward = Path.CLIENT_ALREADY_LEAVE_ORDER;
		}

		log.debug("Command finished");
		return forward;
	}

	/**
	 * Validate input dates.
	 * 
	 * return boolean.
	 */
	private boolean validateDate(Date in, Date out) {
		if (in.compareTo(out) >= 0) {
			return false;
		}
		long differenceOut = out.getTime() - System.currentTimeMillis();
		int daysBetweenOut = (int) TimeUnit.DAYS.convert(differenceOut, TimeUnit.MILLISECONDS);
		long differenceIn = in.getTime() - System.currentTimeMillis();
		int daysBetweenIn = (int) TimeUnit.DAYS.convert(differenceIn, TimeUnit.MILLISECONDS);
		return daysBetweenOut <= 365 && daysBetweenIn >= 0 && daysBetweenOut > 0;
	}

}
