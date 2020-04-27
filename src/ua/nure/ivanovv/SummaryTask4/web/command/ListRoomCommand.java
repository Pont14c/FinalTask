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

public class ListRoomCommand extends Command {

	private static final long serialVersionUID = -1938248497495881918L;

	private static final Logger log = Logger.getLogger(ListRoomCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		Date dateIn = null;
		Date dateOut = null;
		try {
			dateIn = Date.valueOf(request.getParameter("dateIn"));
			dateOut = Date.valueOf(request.getParameter("dateOut"));
			if (!validateDate(dateIn, dateOut)) {
				return Path.PAGE_INCORRECT_DATE;
			} else {
				session.setAttribute("dateIn", dateIn);
				session.setAttribute("dateOut", dateOut);
			}
		} catch (IllegalArgumentException e) {
			dateIn = (Date) session.getAttribute("dateIn");
			if (dateIn == null) {
				log.trace("Empty dates");
				return Path.PAGE_INCORRECT_DATE;
			}
		}

		List<Room> listRooms = new ReserveDao().findAllRoomsByDates(dateIn, dateOut);
		log.trace("Found in DB: listRooms --> " + listRooms);

		listRooms.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

		session.setAttribute("listRooms", listRooms);
		log.trace("Set the request attribute: listRooms --> " + listRooms);

		log.debug("Command finished");
		return Path.PAGE_LIST_ROOMS;
	}

	/**
	 * Validate input dates.
	 * 
	 * @return boolean.
	 */
	private boolean validateDate(Date in, Date out) {
		if (in.compareTo(out) >= 0) {
			return false;
		}
		long differenceOut = out.getTime() - System.currentTimeMillis();
		int daysBetweenTodayAndOut = (int) TimeUnit.DAYS.convert(differenceOut, TimeUnit.MILLISECONDS);
		long differenceIn = in.getTime() - System.currentTimeMillis();
		int daysBetweenTodayAndIn = (int) TimeUnit.DAYS.convert(differenceIn, TimeUnit.MILLISECONDS);
		long differenceInAndOut = out.getTime() - in.getTime();
		int daysBetweenInAndOut = (int) TimeUnit.DAYS.convert(differenceInAndOut, TimeUnit.MILLISECONDS);
		return daysBetweenTodayAndOut <= 365 && daysBetweenTodayAndIn >= 0 && daysBetweenInAndOut >= 1
				&& differenceOut > 0;
	}

}
