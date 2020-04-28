package ua.nure.ivanovv.SummaryTask4.db.entity;

import java.sql.Date;

public class UserOrder extends Entity{

	private static final long serialVersionUID = 5848330028589045978L;
	
	private Date dateIn;
	
	private Date dateOut;

	private String loginUser;
	
	private int price;
	
	private int place;
	
	private int roomStars;

	public Date getDateIn() {
		return dateIn;
	}

	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getRoomStars() {
		return roomStars;
	}

	public void setRoomStars(int roomStars) {
		this.roomStars = roomStars;
	}

	@Override
	public String toString() {
		return "UserOrder [dateIn=" + dateIn + ", dateOut=" + dateOut + ", loginUser=" + loginUser + ", price=" + price
				+ ", place=" + place + ", roomStars=" + roomStars + "]";
	}
	
}
