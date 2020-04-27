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

public class LoginCommand extends Command {

	private static final long serialVersionUID = 3626135719223581766L;

	private static final Logger log = Logger.getLogger(LoginCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		String login = request.getParameter("login");
		log.trace("Request parameter: loging --> " + login);

		String password = request.getParameter("password");

		String errorMessage = null;
		String forward = Path.PAGE_ERROR_PAGE;

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = "Login/password cannot be empty";
			session.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}

		User user = new UserDao().findUserByLogin(login);
		log.trace("Found in DB: user --> " + user);

		if (user == null || !user.getPassword().equals(new UserDao().passwordByLogin(login))) {
			errorMessage = "Cannot find user with such login/password";
			session.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		} else {
			Role userRole = Role.getRole(user);
			log.trace("userRole --> " + userRole);

			if (userRole == Role.ADMIN)
				forward = Path.PAGE_START;

			if (userRole == Role.CLIENT)
				forward = Path.PAGE_START;

			session.setAttribute("user", user);
			log.trace("Set the session attribute: user --> " + user);

			session.setAttribute("userRole", userRole);
			log.trace("Set the session attribute: userRole --> " + userRole);

			log.info("User " + user + " logged as " + userRole.toString().toLowerCase());

		}

		log.debug("Command finished");
		return forward;

	}

}
