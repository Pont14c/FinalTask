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
import ua.nure.ivanovv.SummaryTask4.db.entity.Booking;

public class SelectByDayCommand extends Command {

	private static final long serialVersionUID = 4706087493533731982L;

	private static final Logger log = Logger.getLogger(SelectByDayCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		
		HttpSession session = request.getSession();
		Date dateIn = Date.valueOf(request.getParameter("dateIn"));
		
		List<Booking> listBooks = new ReserveDao().findAllBooksByOneDate(dateIn);
		log.trace("Found in DB: listBooks --> " + listBooks);
		listBooks.sort((o1, o2) -> o1.getRoomId() - o2.getRoomId());
<<<<<<< HEAD
		System.out.println(listBooks);
=======

>>>>>>> d7c153ab667f5beab95a5e96bdfdd25e9eee1567
		session.setAttribute("listBooks", listBooks);
		log.trace("Set the request attribute: listRooms --> " + listBooks);

		log.debug("Command finished");
		return Path.PAGE_CHAGNE_STATUS;
	}

}
