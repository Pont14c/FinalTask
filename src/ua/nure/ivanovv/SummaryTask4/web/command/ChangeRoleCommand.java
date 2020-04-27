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

public class ChangeRoleCommand extends Command {

	private static final long serialVersionUID = -5549271138171969725L;
	
	private static final Logger log = Logger.getLogger(ChangeRoleCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		
		HttpSession session = request.getSession();
		List<User> users = (List<User>) session.getAttribute("listUsers");
		
		String userLogin = request.getParameter("changeRoleLogin");
		int roleId = Integer.valueOf(request.getParameter("changeRoleId")) == 1 ? 0 : 1;
		
		for(User user : users) {
			if(user.getLogin().equals(userLogin))
				user.setRoleId(roleId);
		}
		session.setAttribute("listUsers", users);
		
		new UserDao().changeRole(userLogin, roleId);
		
		log.debug("Command finished");
		return Path.PAGE_LIST_USER;
	}

}
