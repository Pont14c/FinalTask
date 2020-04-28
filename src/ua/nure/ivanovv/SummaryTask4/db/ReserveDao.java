package ua.nure.ivanovv.SummaryTask4.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ua.nure.ivanovv.SummaryTask4.Path;
import ua.nure.ivanovv.SummaryTask4.db.entity.Booking;
import ua.nure.ivanovv.SummaryTask4.db.entity.Room;
import ua.nure.ivanovv.SummaryTask4.db.entity.UserOrder;

public class ReserveDao {

	private static final String SQL_LEAVE_ORDER = "INSERT INTO orders VALUES (DEFAULT,?,?,?,?,?,?)";

	private static final String SQL_BOOKING_ORDER = "INSERT INTO booking VALUES (DEFAULT,?,?,?,1,?,("
			+ "case when ((CURDATE() + INTERVAL 2 DAY)<=date_in) then (CURDATE() + INTERVAL 2 DAY) else date_in end))";

	private static final String SQL_SELECT_BY_CONDITION = "select * from rooms as r where r.price=? and r.place=? and r.room_stars=? "
			+ "and r.id not in (select b.id_room from booking as b where (date_in<=? and ?<=date_out)"
			+ "or (?<=date_in and date_out<=?) " + "or (date_in<? and ?<date_out) "
			+ "or (date_in<? and ?<date_out)) group by r.id";

	private static final String SQL_SELECT_BY_DATES = "select * from rooms as r left join booking as b on r.id = b.id_room where r.id not in "
			+ "(select b.id_room from booking as b where (date_in<=? and ?<=date_out) "
			+ "or (?<=date_in and date_out<=?) " + "or (date_in<? and ?<date_out) "
			+ "or (date_in<? and ?<date_out)) group by r.id";

	private static final String SQL_BOOKING_BY_DATES = "SELECT * FROM booking WHERE ?<=date_in and date_out<=?";

	private static final String SQL_ALL_ORDER = "SELECT * FROM orders WHERE date_in>=CURDATE()";

	private static final String SQL_ALL_ROOMS = "SELECT * FROM rooms";

	private static final String SQL_REMOVE_ORDERS = "DELETE FROM orders WHERE login_user=?";

	private static final String SQL_UNPAID_ORDERS = "SELECT * FROM booking WHERE status_room=1";

	private static final String SQL_SET_PAID = "UPDATE booking SET status_room=2 WHERE login_user=?";

	private static final String SQL_BOOKING_BY_ONE_DATE = "SELECT * FROM booking WHERE ?>=date_in and date_out>=?";

	/**
	 * Returns booking orders with specific one date.
	 *
	 * @param dateIn  Date.
	 * @param dateOut Date.
	 * @return List of booking entities.
	 */
	public List<Booking> findAllBooksByOneDate(Date dateIn) {
		List<Booking> listBooks = new ArrayList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_BOOKING_BY_ONE_DATE);) {
			BookingMapper mapper = new BookingMapper();
			pstmt.setDate(1, dateIn);
			pstmt.setDate(2, dateIn);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					listBooks.add(mapper.mapRow(rs));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listBooks;
	}

	/**
	 * Returns booking orders with specific dates.
	 *
	 * @param dateIn  Date.
	 * @param dateOut Date.
	 * @return List of booking entities.
	 */
	public List<Booking> findAllBooksByDates(Date dateIn, Date dateOut) {
		List<Booking> listBooks = new ArrayList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_BOOKING_BY_DATES);) {
			BookingMapper mapper = new BookingMapper();
			pstmt.setDate(1, dateIn);
			pstmt.setDate(2, dateOut);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					listBooks.add(mapper.mapRow(rs));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listBooks;
	}

	/**
	 * Set reserved orders like booked, hence paid.
	 * 
	 * @param loginUser.
	 */
	public void setPay(String loginUser) {
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_SET_PAID);) {
			pstmt.setString(1, loginUser);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * List reserves that wasn't paid.
	 * 
	 * @return list with unpaid reserves.
	 */
	public List<Booking> notPaid() {
		List<Booking> notPaid = new LinkedList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_UNPAID_ORDERS);) {
			BookingMapper mapper = new BookingMapper();
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					notPaid.add(mapper.mapRow(rs));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return notPaid;
	}

	/**
	 * List orders what exist for today.
	 * 
	 * @return page with decision about order.
	 */
	public List<UserOrder> allOrders() {
		List<UserOrder> listOrders = new LinkedList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_ALL_ORDER);) {
			UserOrderMapper mapper = new UserOrderMapper();
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					listOrders.add(mapper.mapRow(rs));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return listOrders;
	}

	/**
	 * Booking room returns corresponding message.
	 * 
	 * @param dateIn     Date.
	 * @param dateIn     Date.
	 * @param logingUser String.
	 *
	 * @return page with decision about order.
	 */
	public void booking(int idRoom, Date in, Date out, String logingUser) {
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_BOOKING_ORDER);) {
			pstmt.setLong(1, idRoom);
			pstmt.setDate(2, in);
			pstmt.setDate(3, out);
			pstmt.setString(4, logingUser);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns all rooms.
	 *
	 * @return List of rooms entities.
	 */
	public List<Room> findAllRooms() {
		List<Room> allRooms = new ArrayList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_ALL_ROOMS);) {
			RoomMapper mapper = new RoomMapper();
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					allRooms.add(mapper.mapRow(rs));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return allRooms;
	}

	/**
	 * Returns rooms with specific dates.
	 *
	 * @param dateIn  Date.
	 * @param dateOut Date.
	 *
	 * @return List of rooms entities.
	 */
	public List<Room> findAllRoomsByDates(Date dateIn, Date dateOut) {
		List<Room> roomsList = new ArrayList<>();
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_BY_DATES);) {
			RoomMapper mapper = new RoomMapper();
			pstmt.setDate(1, dateIn);
			pstmt.setDate(2, dateOut);
			pstmt.setDate(3, dateIn);
			pstmt.setDate(4, dateOut);
			pstmt.setDate(5, dateIn);
			pstmt.setDate(6, dateIn);
			pstmt.setDate(7, dateOut);
			pstmt.setDate(8, dateOut);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next())
					roomsList.add(mapper.mapRow(rs));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return roomsList;
	}

	/**
	 * Booking room, if the room can't be booked, returns corresponding boolean
	 * value.
	 * 
	 * @param roomLvl    integer.
	 * @param dateIn     Date.
	 * @param dateIn     Date.
	 * @param logingUser String.
	 * @param places     integer.
	 * @param price      integer.
	 *
	 * @return boolean with decision about order.
	 */
	public boolean reserve(int roomLvl, Date in, Date out, String logingUser, int price, int places) {
		List<Integer> listrooms = new ArrayList<>();
		boolean result = false;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_SELECT_BY_CONDITION);) {
			con.setAutoCommit(false);
			pstmt.setInt(1, price);
			pstmt.setInt(2, places);
			pstmt.setInt(3, roomLvl);
			pstmt.setDate(4, in);
			pstmt.setDate(5, out);
			pstmt.setDate(6, in);
			pstmt.setDate(7, out);
			pstmt.setDate(8, in);
			pstmt.setDate(9, in);
			pstmt.setDate(10, out);
			pstmt.setDate(11, out);
			try (ResultSet rs = pstmt.executeQuery();) {
				while (rs.next()) {
					listrooms.add(rs.getInt(1));
				}
			}
			try (PreparedStatement pstmt2 = con.prepareStatement(SQL_BOOKING_ORDER);) {
				pstmt2.setDate(2, in);
				pstmt2.setDate(3, out);
				pstmt2.setString(4, logingUser);
				if (listrooms.size() > 0 && !result) {
					pstmt2.setLong(1, listrooms.get(0));
					int changed = pstmt2.executeUpdate();
					if (changed == 1) {
						pstmt.close();
						result = true;
						try (PreparedStatement pstmt3 = con.prepareStatement(SQL_REMOVE_ORDERS);) {
							pstmt3.setString(1, logingUser);
							pstmt3.executeUpdate();
						}
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * Leave order from client
	 * 
	 * @param roomLvl    integer.
	 * @param dateIn     Date.
	 * @param dateIn     Date.
	 * @param logingUser String.
	 * @param places     integer.
	 * @param price      integer.
	 * 
	 */
	public String leaveOrder(int roomLvl, Date in, Date out, String logingUser, int price, int places) {
		String forward = Path.PAGE_ERROR_PAGE;
		try (Connection con = DBManager.getInstance().getConnection();
				PreparedStatement pstmt = con.prepareStatement(SQL_LEAVE_ORDER);) {
			pstmt.setDate(1, in);
			pstmt.setDate(2, out);
			pstmt.setString(3, logingUser);
			pstmt.setInt(4, price);
			pstmt.setInt(5, places);
			pstmt.setInt(6, roomLvl);
			pstmt.executeUpdate();
			forward = Path.PAGE_SUGGEST_SOON;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return forward;
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

	/**
	 * Extracts a rooms from the result set row.
	 */
	private static class RoomMapper implements EntityMapper<Room> {

		@Override
		public Room mapRow(ResultSet rs) {
			try {
				Room room = new Room();
				room.setId(rs.getInt(Fields.ENTITY_ID));
				room.setPrice(rs.getInt(Fields.ROOM_PRICE));
				room.setPlacesInRoom(rs.getInt(Fields.ROOM_PLACES_COUNT));
				room.setRoomLvl(rs.getInt(Fields.ROOM_LVL));
				return room;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	/**
	 * Extracts a orders from the result set row.
	 */
	private static class UserOrderMapper implements EntityMapper<UserOrder> {

		@Override
		public UserOrder mapRow(ResultSet rs) {
			try {
				UserOrder order = new UserOrder();
				order.setId(rs.getInt(Fields.ENTITY_ID));
				order.setLoginUser(rs.getString(Fields.USER_ORDER_LOGIN));
				order.setDateIn(rs.getDate(Fields.USER_ORDER_DATE_IN));
				order.setDateOut(rs.getDate(Fields.USER_ORDER_DATE_OUT));
				order.setPlace(rs.getInt(Fields.USER_ORDER_PLACE));
				order.setPrice(rs.getInt(Fields.USER_ORDER_PRICE));
				order.setRoomStars(rs.getInt(Fields.USER_ORDER_ROOM_STARS));
				return order;
			} catch (SQLException e) {
				throw new IllegalStateException(e);
			}
		}

	}

}
