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
import ua.nure.ivanovv.SummaryTask4.db.entity.User;
import ua.nure.ivanovv.SummaryTask4.db.entity.UserOrder;

public class ListOrdersCommand extends Command {

	private static final long serialVersionUID = -7793399277027219884L;

	private static final Logger log = Logger.getLogger(ListOrdersCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");
		
		HttpSession session = request.getSession();
		User user = (User) request.getSession().getAttribute("user");
		
		List<UserOrder> listOrders = new ReserveDao().allOrders();
		session.setAttribute("listOrders", listOrders);
		
		log.trace("admin obtain all orders -->" + user);
		log.debug("Command finished");
		return Path.PAGE_LIST_ORDERS;
	}

}
