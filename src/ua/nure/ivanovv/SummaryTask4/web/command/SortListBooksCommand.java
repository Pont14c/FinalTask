package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.Role;
import ua.nure.ivanovv.SummaryTask4.db.entity.Booking;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class SortListBooksCommand extends Command {

	private static final long serialVersionUID = -8399177902340297840L;

	private static final Logger log = Logger.getLogger(SortListBooksCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		List<Booking> listBooks = (List<Booking>) session.getAttribute("listBooks");
		String sort = request.getParameter("sortChoise");

		switch (sort) {
		case "roomAsc":
			listBooks.sort((o1, o2) -> o1.getRoomId() - o2.getRoomId());
			break;
		case "roomDesc":
			listBooks.sort((o1, o2) -> o2.getRoomId() - o1.getRoomId());
			break;
		case "statusAsc":
			listBooks.sort((o1, o2) -> o1.getStatus() - o2.getStatus());
			break;
		case "statusDesc":
			listBooks.sort((o1, o2) -> o2.getStatus() - o1.getStatus());
			break;
		default:
			log.trace("Wrong type of sort");
			break;
		}
		
		User user = (User) request.getSession().getAttribute("user");
		String loginUser = user.getLogin();
		Role userRole = Role.getRole(user);
		log.trace("userRole --> " + userRole);

		return Path.PAGE_CHAGNE_STATUS;
	}

}
