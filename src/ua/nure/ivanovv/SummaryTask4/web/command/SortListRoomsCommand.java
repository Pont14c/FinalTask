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
import ua.nure.ivanovv.SummaryTask4.db.entity.Room;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class SortListRoomsCommand extends Command {

	private static final long serialVersionUID = -9210788607541355679L;

	private static final Logger log = Logger.getLogger(SortListRoomsCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		List<Room> listRooms = (List<Room>) session.getAttribute("listRooms");
		String sort = request.getParameter("sortChoise");

		switch (sort) {
		case "roomAsc":
			listRooms.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
			break;
		case "priceAsc":
			listRooms.sort((o1, o2) -> o1.getPrice().compareTo(o2.getPrice()));
			break;
		case "priceDes":
			listRooms.sort((o1, o2) -> o2.getPrice().compareTo(o1.getPrice()));
			break;
		case "placeDes":
			listRooms.sort((o1, o2) -> o2.getPlacesInRoom().compareTo(o1.getPlacesInRoom()));
			break;
		case "starsDes":
			listRooms.sort((o1, o2) -> o2.getRoomLvl().compareTo(o1.getRoomLvl()));
			break;
		default:
			log.trace("Wrong type of sort");
			break;
		}
		
		User user = (User) request.getSession().getAttribute("user");
		Role userRole = Role.getRole(user);
		log.trace("userRole --> " + userRole);
		
		if (userRole == Role.CLIENT) {
			log.debug("Command finished");
			return Path.PAGE_LIST_ROOMS;
		}
		
		if(userRole == Role.ADMIN) {
			log.debug("Command finished");
			return Path.PAGE_LIST_ORDERS;
		}
		return Path.PAGE_ERROR_PAGE;
	}

}
