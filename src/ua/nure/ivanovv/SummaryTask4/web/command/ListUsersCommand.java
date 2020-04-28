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

public class ListUsersCommand extends Command {

	private static final long serialVersionUID = -1427293142709388385L;

	private static final Logger log = Logger.getLogger(ListUsersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();
		
		String changeRole = request.getParameter("command2");
		if (changeRole != null && request.getParameter("command2").equals("changeRole")) {

			String userLogin = request.getParameter("changeRoleLogin");
			int roleId = Integer.valueOf(request.getParameter("changeRoleId")) == 1 ? 0 : 1;

			new UserDao().changeRole(userLogin, roleId);
		}
		
		List<User> users = new UserDao().allUser();
		users.remove(0);
		session.setAttribute("listUsers", users);

		log.debug("Command finished");
		return Path.PAGE_LIST_USER;
	}

}
