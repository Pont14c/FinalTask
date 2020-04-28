package ua.nure.ivanovv.SummaryTask4.web.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class LanguageCommand extends Command {

	private static final long serialVersionUID = 6333532545703339644L;

	private static final Logger log = Logger.getLogger(LanguageCommand.class);
	
	private static final int LENGHT_OF_HOSTNAME_PORT = 31;

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		log.debug("Command starts");
		
		if(request.getParameter("en.x") != null) {
			request.getSession().setAttribute("language", "en");
		}

		if(request.getParameter("ru.x") != null) {
			request.getSession().setAttribute("language", "ru");
		}
		
		String url = request.getParameter("url");
		url = url.substring(LENGHT_OF_HOSTNAME_PORT, url.length());
		
		log.debug("Command finished");
		return url;
	}

}
