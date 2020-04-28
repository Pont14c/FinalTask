package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.ReserveDao;
import ua.nure.ivanovv.SummaryTask4.db.Role;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;
import ua.nure.ivanovv.SummaryTask4.db.entity.UserOrder;

public class ReserveFortuneCommand extends Command {

	private static final long serialVersionUID = 4013013995578579191L;

	private static final Logger log = Logger.getLogger(ReserveFortuneCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		String reserve = request.getParameter("reserve");
		log.trace("Request parameter: reserve --> " + reserve);

		User user = (User) request.getSession().getAttribute("user");
		Role userRole = Role.getRole(user);
		log.trace("userRole --> " + userRole);

		String loginUserReserve = request.getParameter("adminReserve");
		List<UserOrder> listOrders = (List<UserOrder>) session.getAttribute("listOrders");
		UserOrder order = null;
		for (UserOrder ord : listOrders) {
			if (ord.getLoginUser().equals(loginUserReserve)) {
				order = ord;
				break;
			}
		}

		boolean result = new ReserveDao().reserve(order.getRoomStars(), order.getDateIn(), order.getDateOut(),
				order.getLoginUser(), order.getPrice(), order.getPlace());
		if (result) {
			log.trace("admin booked room for client -->" + userRole);
			listOrders.remove(order);
			session.setAttribute("listOrdres", listOrders);
			log.debug("Command finished");
			return Path.PAGE_LIST_ORDERS;
		} else {
			List<String> unbooked = (List<String>) session.getAttribute("unbooked");
			if (unbooked == null) {
				unbooked = new LinkedList<>();
			}
			unbooked.add(order.getLoginUser());
			session.setAttribute("unbooked", unbooked);
			log.trace("admin tried to book room for client -->" + userRole);
			log.debug("Command finished");
			return Path.PAGE_ROOM_DOESNT_EXIST;
		}

	}

}
