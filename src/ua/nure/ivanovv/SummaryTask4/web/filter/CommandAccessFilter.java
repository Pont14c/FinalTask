package ua.nure.ivanovv.SummaryTask4.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.Role;
import ua.nure.ivanovv.SummaryTask4.db.UserDao;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

/**
 * Security filter. Disabled by default. Uncomment Security filter section in
 * web.xml to enable.
 * 
 * @author V.Ivanov
 * 
 */
public class CommandAccessFilter implements Filter {

	private static final Logger log = Logger.getLogger(CommandAccessFilter.class);

	private static Map<Role, List<String>> accessMap = new HashMap<>();
	private static List<String> commons = new ArrayList<>();
	private static List<String> outOfControl = new ArrayList<>();

	@Override
	public void destroy() {
		log.debug("Filter destruction starts");
		log.debug("Filter destruction finished");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		log.debug("Filter starts");

		HttpSession session = ((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			user = new UserDao().findUserByLogin(user.getLogin());
			Role userRole = Role.getRole(user);
			session.setAttribute("userRole", userRole);
		}

		if (accessAllowed(request)) {
			log.debug("Filter finished");
			chain.doFilter(request, response);
		} else {
			String errorMessage = "You do not have permission to access the requested resource";

			request.setAttribute("errorMessage", errorMessage);
			log.trace("Set the request attribute: errorMessage --> " + errorMessage);

			request.getRequestDispatcher(Path.PAGE_ERROR_PAGE).forward(request, response);
		}
	}

	private boolean accessAllowed(ServletRequest request) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		String commandName = request.getParameter("command");
		if (commandName == null || commandName.isEmpty())
			return false;

		if (outOfControl.contains(commandName)) {
			return true;
		}

		HttpSession session = httpRequest.getSession(false);
		if (session == null)
			return false;
		if (httpRequest.getSession().getAttribute("user") == null) {
			return false;
		}

		Role userRole = (Role) session.getAttribute("userRole");
		if (userRole == null)
			return false;
		return accessMap.get(userRole).contains(commandName) || commons.contains(commandName);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		log.debug("Filter initialization starts");

		accessMap.put(Role.ADMIN, asList(fConfig.getInitParameter("admin")));
		accessMap.put(Role.CLIENT, asList(fConfig.getInitParameter("client")));
		log.trace("Access map --> " + accessMap);

		commons = asList(fConfig.getInitParameter("common"));
		log.trace("Common commands --> " + commons);

		outOfControl = asList(fConfig.getInitParameter("out-of-control"));
		log.trace("Out of control commands --> " + outOfControl);

		log.debug("Filter initialization finished");
	}

	/**
	 * Extracts parameter values from string.
	 * 
	 * @param str parameter values string.
	 * @return list of parameter values.
	 */
	private List<String> asList(String str) {
		List<String> list = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(str);
		while (st.hasMoreTokens())
			list.add(st.nextToken());
		return list;
	}

}
