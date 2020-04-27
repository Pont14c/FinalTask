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

public class RemoveBookCommand extends Command {

	private static final long serialVersionUID = -5429982785078032917L;

	private static final Logger log = Logger.getLogger(RemoveBookCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();
		int idRoom = Integer.parseInt(request.getParameter("removeIdRoom"));

		if (request.getParameter("statusPaid").equals("1")) {
			session.removeAttribute("reserveAlready");
			session.removeAttribute("orderLeaved");
		}

		new UserDao().removeReservation(loginUser, idRoom);
		List<Booking> historyOrders = (List<Booking>) session.getAttribute("historyOrders");
		for (Booking book : historyOrders) {
			if (book.getRoomId() == idRoom)
				historyOrders.remove(book);
		}
		session.setAttribute("historyOrders", historyOrders);
		log.trace("client removed his own reservation -->" + loginUser);

		log.debug("Command finished");
		return Path.PAGE_REMOVE_BOOK;
	}

}
