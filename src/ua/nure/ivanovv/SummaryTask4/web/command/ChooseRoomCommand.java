package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.ReserveDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.Room;

public class ChooseRoomCommand extends Command {

	private static final long serialVersionUID = -3172326936990274163L;
	
	private static final Logger log = Logger.getLogger(ChooseRoomCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		
		HttpSession session = request.getSession();

		Date dateIn = (Date) session.getAttribute("dateIn");
		Date dateOut = (Date) session.getAttribute("dateOut");
		
		if(dateIn != null && dateOut != null) {
			List<Room> listRooms = new ReserveDao().findAllRoomsByDates(dateIn, dateOut);
			log.trace("Found in DB: listRooms --> " + listRooms);

			listRooms.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));

			session.setAttribute("listRooms", listRooms);
			log.trace("Set the request attribute: listRooms --> " + listRooms);
		}
		
		log.debug("Command finished");
		return Path.PAGE_LIST_ROOMS;
	}

}
