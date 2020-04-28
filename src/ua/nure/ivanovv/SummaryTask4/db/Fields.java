package ua.nure.ivanovv.SummaryTask4.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 * @author V.Ivanov
 * 
 */
public final class Fields {
	
	public static final String ENTITY_ID = "id";
	
	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE_ID = "role_id";
	
	public static final String BOOKING_ROOM_ID= "id_room";
	public static final String BOOKING_USER_LOGIN= "login_user";
	public static final String BOOKING_STATUS_NAME= "status_room";
	public static final String BOOKING_DATE_IN= "date_in";
	public static final String BOOKING_DATE_OUT= "date_out";
	public static final String BOOKING_DATE_PAID = "date_paid";
	
	public static final String ROOM_PRICE = "price";
	public static final String ROOM_PLACES_COUNT = "place";
	public static final String ROOM_LVL = "room_stars";	

	public static final String USER_ORDER_DATE_IN = "date_in";
	public static final String USER_ORDER_DATE_OUT = "date_out";
	public static final String USER_ORDER_LOGIN = "login_user";
	public static final String USER_ORDER_PRICE = "price";
	public static final String USER_ORDER_PLACE = "place";
	public static final String USER_ORDER_ROOM_STARS = "room_type";

	
}