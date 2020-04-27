package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.Role;
import ua.nure.ivanovv.SummaryTask4.db.UserDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class LeaveOrderPageCommand extends Command {

	private static final long serialVersionUID = -3172326936990274163L;

	private static final Logger log = Logger.getLogger(LeaveOrderPageCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();
		Role userRole = Role.getRole(user);
		log.trace("userRole --> " + userRole);

		Boolean leaveOrder = (Boolean) session.getAttribute("orderLeaved");
		leaveOrder = new UserDao().chekOrder(loginUser);
		
		session.setAttribute("orderLeaved", leaveOrder);
		log.debug("Command finished");
		return Path.PAGE_LIST_LEAVE_ORDER;
	}

}
