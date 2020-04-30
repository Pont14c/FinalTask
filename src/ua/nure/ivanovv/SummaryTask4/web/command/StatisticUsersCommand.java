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
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class StatisticUsersCommand extends Command {

	private static final long serialVersionUID = -7277394945280075809L;

	private static final Logger log = Logger.getLogger(StatisticUsersCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();
		User user = (User) request.getSession().getAttribute("user");
		
		List<User> listHistory = new UserDao().getHistoryAll();
		System.out.println(listHistory);
		session.setAttribute("listHistory", listHistory);
		log.trace("admin obtain history of his all bookings -->" + user);
		
		log.debug("Command finished");
		return Path.PAGE_HISTORY_USER_ORDER;
	}

}
