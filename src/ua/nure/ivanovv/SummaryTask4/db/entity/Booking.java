package ua.nure.ivanovv.SummaryTask4.db.entity;

import java.util.Date;

/**
 * Booking entity.
 * 
 * @author V.Ivanov
 * 
 */
public class Booking extends Entity{
	
	private static final long serialVersionUID = -3757052046268022689L;

	private int roomId;
	
	private int status;
	
	private Date dateIn; 
	
	private Date dateOut; 
	
	private Date datePaid;
	
	private String loginUser;
	

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	public int getStatus() {
		return this.status;
	}
	
	public void setStatus(int bookingStatusName) {
		this.status = bookingStatusName;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

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

	public void setDatePaid(Date date) {
		this.datePaid = date;
	}
	
	public Date getDatePaid() {
		return datePaid;
	}

	@Override
	public String toString() {
		return "Booking [roomId=" + roomId + ", status=" + status + ", dateIn=" + dateIn + ", dateOut=" + dateOut
				+ ", loginUser=" + loginUser + "]";
	}

}
