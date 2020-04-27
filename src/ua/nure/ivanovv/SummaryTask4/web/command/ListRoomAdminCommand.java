package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.ReserveDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.Room;

public class ListRoomAdminCommand extends Command {

	private static final long serialVersionUID = -3345045205884994327L;

	private static final Logger log = Logger.getLogger(ListOrdersCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		Date dateIn = null;
		Date dateOut = null;
		Integer price = null;
		try {
			dateIn = Date.valueOf(request.getParameter("dateIn"));
			dateOut = Date.valueOf(request.getParameter("dateOut"));
			price = Integer.parseInt(request.getParameter("price"));
		} catch (IllegalArgumentException e) {
				log.trace("Empty dates");
				return Path.PAGE_INCORRECT_DATE;
		}

		long diffInMillies = dateOut.getTime() - dateIn.getTime();
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		session.setAttribute("priceForNumber", diff * price);

		List<Room> listRooms = new ReserveDao().findAllRoomsByDates(dateIn, dateOut);
		log.trace("Found in DB: listRooms --> " + listRooms);

		listRooms.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

		session.setAttribute("listRooms", listRooms);
		log.trace("Set the request attribute: listRooms --> " + listRooms);

		String loginBook = (String) request.getParameter("adminChoiseUser");
		session.setAttribute("loginBook", loginBook);

		log.debug("Command finished");
		return Path.PAGE_LIST_ORDERS;
	}

}
