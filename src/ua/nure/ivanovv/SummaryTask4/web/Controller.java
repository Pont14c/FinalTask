package ua.nure.ivanovv.SummaryTask4.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.web.command.Command;
import ua.nure.ivanovv.SummaryTask4.web.command.CommandContainer;

/**
 * Main servlet controller.
 * 
 * @author V.Ivanov
 * 
 */
public class Controller extends HttpServlet {

	private static final long serialVersionUID = 6404404021624062978L;

	private static final Logger log = Logger.getLogger(Controller.class);

	private static final String[] forwardCommands = { "listOrders", "changeStatus", "logout", "leaveOrderPage",
			"chooseRoom", "userOrders", "listRoom", "removeOrder", "sortListBooks", "listRoomAdmin", "language", "listUsers" };

	public Controller() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * Main method of this controller.
	 */
	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		log.debug("Controller starts");

		String commandName = request.getParameter("command");
		log.trace("Request parameter: command --> " + commandName);

		Command command = CommandContainer.get(commandName);
		log.trace("Obtained command --> " + command);

		String path = command.execute(request, response);
		log.trace("Redirect address --> " + path);

		log.debug("Controller finished, now go to address --> " + path);

		if (path != null) {
			if (containsForward(commandName)) {
				request.getRequestDispatcher(path).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + path);
			}
		}
	}

	/**
	 * Check command for forward.
	 */
	public static boolean containsForward(String str) {

		for (String s : forwardCommands) {
			if (s.equals(str)) {
				return true;
			}
		}
		return false;
	}
}
