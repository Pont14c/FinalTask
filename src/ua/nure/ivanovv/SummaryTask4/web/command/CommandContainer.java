package ua.nure.ivanovv.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.
 * 
 * @author V.Ivanov
 * 
 */
public class CommandContainer {
	
	private static final Logger log = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		commands.put("register", new RegisterCommand());
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("reserve", new ReserveCommand());
		commands.put("listRoom", new ListRoomCommand());
		commands.put("leaveOrderPage", new LeaveOrderPageCommand());
		commands.put("leaveOrder", new LeaveOrderCommand());
		commands.put("sortList", new SortListRoomsCommand());
		commands.put("sortListBooks", new SortListBooksCommand());
		commands.put("chooseRoom", new ChooseRoomCommand());
		commands.put("userOrders", new UserOrdersCommand());
		commands.put("listOrders", new ListOrdersCommand());
		commands.put("changeStatus", new ChangeStatusCommand());
		commands.put("removeOrder", new RemoveOrderCommand());
		commands.put("language", new LanguageCommand());
		commands.put("reserveFortune", new ReserveFortuneCommand());
		commands.put("listRoomAdmin", new ListRoomAdminCommand());
		commands.put("removeBook", new RemoveBookCommand());
		commands.put("payBook", new PayBookCommand());
		commands.put("selectByDay", new SelectByDayCommand());
		commands.put("listUsers", new ListUsersCommand());
<<<<<<< HEAD
=======
		commands.put("changeRole", new ChangeRoleCommand());
>>>>>>> d7c153ab667f5beab95a5e96bdfdd25e9eee1567
		
		log.debug("Command container was successfully initialized");
		log.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			log.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}