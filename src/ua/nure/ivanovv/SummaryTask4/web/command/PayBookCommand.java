package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.ReserveDao;
import ua.nure.ivanovv.SummaryTask4.db.UserDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class PayBookCommand extends Command {

	private static final long serialVersionUID = 2673259746602835955L;

	private static final Logger log = Logger.getLogger(PayBookCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();

		if(request.getParameter("statusPaid").equals("2"))
			return Path.PAGE_BOOK_ALREADY_PAID;
		
		Boolean checkPay = (Boolean) session.getAttribute("paid");
		if (checkPay != null) {
			boolean check = new UserDao().checkPaid(loginUser);
			if (check) {
				checkPay = true;
				session.setAttribute("paid", checkPay);
			}
		}

		if (checkPay == null || checkPay == true) {
			new ReserveDao().setPay(loginUser);

			session.removeAttribute("orderLeaved");
			boolean paid = true;
			session.setAttribute("paid", paid);
		} else {
			return Path.PAGE_BOOK_ALREADY_PAID;
		}

		log.trace("client removed his own reservation -->" + loginUser);

		log.debug("Command finished");
		return Path.PAGE_BOOK_PAID;
	}

}
