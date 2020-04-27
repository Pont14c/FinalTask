package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.ReserveDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.Booking;
import ua.nure.ivanovv.SummaryTask4.db.entity.Room;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class ChangeStatusCommand extends Command {

	private static final long serialVersionUID = -6838745016831745602L;

	private static final Logger log = Logger.getLogger(ChangeStatusCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();
		User user = (User) request.getSession().getAttribute("user");

		
		List<Room> allRooms = new ReserveDao().findAllRooms();
		session.setAttribute("allRooms", allRooms);
		
		if(request.getParameter("reserve") != null) {
			String loginUser = request.getParameter("adminReserve");
			new ReserveDao().setPay(loginUser);
			log.trace("admin set booking order like paid for user" + loginUser);
		}
		
		List<Booking> listNotPaid = new ReserveDao().notPaid();
		session.setAttribute("listNotPaid", listNotPaid);

		log.trace("admin obtain all orders -->" + user);
		log.debug("Command finished");
		return Path.PAGE_CHAGNE_STATUS;
	}
}
