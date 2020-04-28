package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.UserDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.Booking;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class UserOrdersCommand extends Command {

	private static final long serialVersionUID = 7177862136293830088L;

	private static final Logger log = Logger.getLogger(UserOrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();
		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();

		List<Booking> historyOrders = new UserDao().historyOrder(loginUser);

		if (checkPaid(historyOrders)) {
			session.removeAttribute("reserveAlready");
			session.removeAttribute("orderLeaved");
			session.removeAttribute("dateIn");
			session.removeAttribute("dateOut");
			session.removeAttribute("listRooms");
		}

		session.setAttribute("historyOrders", historyOrders);
		log.trace("client obtain history of his orders -->" + user);

		log.debug("Command finished");
		return Path.PAGE_LIST_USER_ORDER;
	}

	/**
	 * Check if user paid all his orders.
	 * 
	 * @param list.
	 * 
	 * @return boolean.
	 */
	private boolean checkPaid(List<Booking> historyOrders) {
		int count = 0;
		for (Booking book : historyOrders) {
			if (book.getStatus()==2) {
				count++;
			}
		}
		return count == historyOrders.size();
	}

}
