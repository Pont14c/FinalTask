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
import ua.nure.ivanovv.SummaryTask4.db.entity.UserOrder;

public class RemoveOrderCommand extends Command {

	private static final long serialVersionUID = 5458913459045652685L;

	private static final Logger log = Logger.getLogger(ChangeStatusCommand.class);

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Command starts");

		HttpSession session = request.getSession();

		String loginUserRemove = request.getParameter("adminReserve");
		List<UserOrder> listOrders = (List<UserOrder>) session.getAttribute("listOrders");
		UserOrder order = null;
		for (UserOrder ord : listOrders) {
			if (ord.getLoginUser().equals(loginUserRemove)) {
				order = ord;
				break;
			}
		}
		
		new UserDao().removeOrder(loginUserRemove);
		listOrders.remove(order);
		session.setAttribute("listOrders", listOrders);
		
		log.trace("admin removed order for client -->" + loginUserRemove);
		log.debug("Command finished");
		return Path.PAGE_LIST_ORDERS;
	}

}
