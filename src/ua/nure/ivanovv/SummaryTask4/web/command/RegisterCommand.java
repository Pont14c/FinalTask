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

public class RegisterCommand extends Command {

	private static final long serialVersionUID = -7568824884420258662L;

	private static final Logger log = Logger.getLogger(RegisterCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		String register = request.getParameter("register");
		log.trace("Request parameter: loging --> " + register);

		String fName = request.getParameter("fName");
		String lName = request.getParameter("lName");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		String errorMessage = null;
		String forward = Path.PAGE_ERROR_PAGE;

		if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
			errorMessage = "Fields have to be fill";
			request.setAttribute("errorMessage", errorMessage);
			log.error("errorMessage --> " + errorMessage);
			return forward;
		}
		
		if(login.length()>20 || password.length()>20) {
			return Path.PAGE_LONG_LOGIN_PASSWORD;
		}
		
		User user = createUser(login, password, fName, lName, 1);
		boolean loginEngage = new UserDao().findUserByLoginBoolean(login);
		if (loginEngage) {
			log.trace("Login is engaged --> " + login);
			return Path.PAGE_LOGIN_ENGAGED;
		} else {
			log.trace("Login is available --> " + login);
			new UserDao().addNewUser(login, password, fName, lName, 1);
			log.trace("New user in DB --> " + user);
		}
		

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

		log.info("User " + user + " register as " + userRole.toString().toLowerCase());

		log.debug("Command finished");
		return forward;
	}

	public User createUser(String login, String password, String fName, String lName, int roleId) {
		User user = new User();
		user.setFirstName(fName);
		user.setRoleId(roleId);
		user.setLastName(lName);
		user.setLogin(login);
		user.setPassword(password);
		return user;
	}

}
