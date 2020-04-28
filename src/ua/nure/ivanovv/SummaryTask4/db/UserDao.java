package ua.nure.ivanovv.SummaryTask4.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import ua.nure.ivanovv.SummaryTask4.db.entity.Booking;
import ua.nure.ivanovv.SummaryTask4.db.entity.User;

public class UserDao {

	private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE login=?";

	private static final String SQL_ADD_USER = "INSERT INTO users  VALUES(DEFAULT,?,SHA2(?,224),?,?,?)";

	private static final String SQL_HISTORY_ORDERS = "SELECT * FROM booking WHERE login_user=?";

	private static final String SQL_REMOVE_ORDERS = "DELETE FROM orders WHERE login_user=?";

	private static final String SQL_REMOVE_RESERVATION = "DELETE FROM booking WHERE login_user=? AND id_room=?";

	private static final String SQL_CHECK_PAID = "SELECT COUNT(status_room) FROM booking WHERE login_user=? AND status_room=0";

	private static final String SQL_CHECK_ORDER = "SELECT COUNT(*) FROM orders WHERE login_user=?";

	private static final String SQL_ALL_USERS = "SELECT * FROM users";

	private static final String SQL_CHAGNE_ROLE = "UPDATE users SET role_id=? where login=?";

	private static final String SQL_FIND_PASSWORD = "SELECT * from users where login=? and password=sha2(?, 224)";

	/**
	 * Decision about, that password was typed wrong or not.
	 *
	 * @param loginUser String.
	 * @param pass String.
	 * @return true if user.
	 */
	public boolean passwordByLogin(String loginUser, String pass) {
		boolean password = false;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_FIND_PASSWORD);) {
			pstmt.setString(1, loginUser);
			pstmt.setString(2, pass);
			try (ResultSet rs = pstmt.executeQuery();) {
				password = rs.next();
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return password;
	}

	/**
	 * Change role Id for user.
	 *
	 * @param loginUser String.
	 * @param roleId    int.
	 * @return list of users.
	 */
	public void changeRole(String loginUser, int roleId) {
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_CHAGNE_ROLE);) {
			pstmt.setInt(1, roleId);
			pstmt.setString(2, loginUser);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Looking for all users.
	 *
	 * @return list of users.
	 */
	public List<User> allUser() {
		List<User> users = new LinkedList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_ALL_USERS);) {
			UserMapper mapper = new UserMapper();
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					users.add(mapper.mapRow(rs));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return users;
	}

	/**
	 * Check if user has already left his order.
	 *
	 * @param loginUser String.
	 */
	public boolean chekOrder(String loginUser) {
		boolean check = true;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_CHECK_ORDER);) {
			pstmt.setString(1, loginUser);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					check = rs.getInt(1) == 0 ? false : true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return check;
	}

	/**
	 * Check if user has paid all his orders.
	 *
	 * @param loginUser String.
	 */
	public boolean checkPaid(String loginUser) {
		boolean check = true;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_CHECK_PAID);) {
			pstmt.setString(1, loginUser);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					check = rs.getInt(1) == 0 ? true : false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return check;
	}

	/**
	 * Remove user reservation from Booking table.
	 *
	 * @param loginUser String.
	 * @param idRoom    int.
	 */
	public void removeReservation(String loginUser, int idRoom) {
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_REMOVE_RESERVATION);) {
			pstmt.setString(1, loginUser);
			pstmt.setInt(2, idRoom);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Remove user order from Orders table.
	 *
	 * @param loginUser String.
	 */
	public void removeOrder(String loginUser) {
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_REMOVE_ORDERS);) {
			pstmt.setString(1, loginUser);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Find user orders by loginUser.
	 *
	 * @param loginUser String.
	 * @return list with booking entity.
	 */
	public List<Booking> historyOrder(String loginUser) {
		List<Booking> list = new LinkedList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_HISTORY_ORDERS);) {
			BookingMapper mapper = new BookingMapper();
			pstmt.setString(1, loginUser);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					list.add(mapper.mapRow(rs));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return list;
	}

	/**
	 * Add a new user in DB.
	 *
	 * @param login    String.
	 * @param passwrod String.
	 * @param fName    String.
	 * @param lName    String.
	 * @param roleId   integer.
	 */
	public void addNewUser(String login, String password, String fName, String lName, int roleId) {
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_ADD_USER);) {
			pstmt.setString(1, login);
			pstmt.setString(2, password);
			pstmt.setString(3, fName);
			pstmt.setString(4, lName);
			pstmt.setInt(5, roleId);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns true if this login already exist id DB.
	 * 
	 * @param login String.
	 * @return boolean value.
	 */
	public boolean findUserByLoginBoolean(String login) {
		boolean check = false;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);) {
			pstmt.setString(1, login);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					check = true;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return check;
	}

	/**
	 * Returns a user with the given login.
	 * 
	 * @param login String.
	 * @return User entity.
	 */
	public User findUserByLogin(String login) {
		User user = null;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_FIND_USER_BY_LOGIN);) {
			UserMapper mapper = new UserMapper();
			pstmt.setString(1, login);
			try (ResultSet rs = pstmt.executeQuery();) {
				if (rs.next()) {
					user = mapper.mapRow(rs);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return user;
	}

	/**
	 * Extracts a user from the result set row.
	 */
	private static class UserMapper implements EntityMapper<User> {

		@Override
		public User mapRow(ResultSet rs) {
			try {
				User user = new User();
				user.setLogin(rs.getString(Fields.USER_LOGIN));
				user.setPassword(rs.getString(Fields.USER_PASSWORD));
				user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
				user.setLastName(rs.getString(Fields.USER_LAST_NAME));
				user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
				return user;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Extracts booking from the result set row.
	 */
	private static class BookingMapper implements EntityMapper<Booking> {

		@Override
		public Booking mapRow(ResultSet rs) {
			try {
				Booking book = new Booking();
				book.setId(rs.getInt(Fields.ENTITY_ID));
				book.setDateIn(rs.getDate(Fields.BOOKING_DATE_IN));
				book.setDateOut(rs.getDate(Fields.BOOKING_DATE_OUT));
				book.setLoginUser(rs.getString(Fields.BOOKING_USER_LOGIN));
				book.setRoomId(rs.getInt((Fields.BOOKING_ROOM_ID)));
				book.setStatus(rs.getInt(Fields.BOOKING_STATUS_NAME));
				book.setDatePaid(rs.getDate(Fields.BOOKING_DATE_PAID));
				return book;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	}

}
