package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
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
import ua.nure.ivanovv.SummaryTask4.db.entity.UserOrder;

public class ReserveCommand extends Command {

	private static final long serialVersionUID = 1598960208827874157L;

	private static final Logger log = Logger.getLogger(ReserveCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		String reserve = request.getParameter("reserve");
		log.trace("Request parameter: reserve --> " + reserve);

		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();
		Role userRole = Role.getRole(user);
		log.trace("userRole --> " + userRole);

		if (userRole == Role.CLIENT) {

			if (session.getAttribute("reserveAlready") == null) {

				int idRoom = Integer.parseInt(request.getParameter("clientChoise"));
				new ReserveDao().booking(idRoom, (Date) session.getAttribute("dateIn"),
						(Date) session.getAttribute("dateOut"), loginUser);
				boolean reserveAlready = true;
				session.setAttribute("reserveAlready", reserveAlready);
				session.removeAttribute("paid");
				
				Date first = (Date) session.getAttribute("dateIn");
				Date last = (Date) session.getAttribute("dateOut");
				int price = Integer.parseInt(request.getParameter("price"));
				long diffInMillies = last.getTime() - first.getTime();
			    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			    session.setAttribute("priceForNumber", diff*price);

			    return Path.PAGE_THX_FOR_RESERVATION;
			} else {
				return Path.YOU_ALREADY_RESERVED;
			}

		}

		if (userRole == Role.ADMIN) {

			loginUser = (String) session.getAttribute("loginBook");
			int idRoom = Integer.parseInt(request.getParameter("adminChoise"));
			new ReserveDao().booking(idRoom, (Date) session.getAttribute("dateIn"),
					(Date) session.getAttribute("dateOut"), loginUser);
			boolean reserveAlready = true;
			session.setAttribute("reserveAlready", reserveAlready);
			new UserDao().removeOrder(loginUser);
			return Path.PAGE_THX_FOR_RESERVATION;
		}
		log.debug("Command finished");
		return Path.PAGE_LIST_ORDERS;
	}

}
