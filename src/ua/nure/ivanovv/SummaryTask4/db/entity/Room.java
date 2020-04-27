package ua.nure.ivanovv.SummaryTask4.db.entity;

/**
 * Rooms entity.
 * 
 * @author V.Ivanov
 * 
 */
public class Room extends Entity {

	private static final long serialVersionUID = 2386302708905518585L;

	private Integer price;
	private Integer placesInRoom;
	private Integer roomLvl;

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPlacesInRoom() {
		return placesInRoom;
	}

	public void setPlacesInRoom(Integer placesInRoom) {
		this.placesInRoom = placesInRoom;
	}

	public Integer getRoomLvl() {
		return roomLvl;
	}

	public void setRoomLvl(Integer roomLvl) {
		this.roomLvl = roomLvl;
	}

	@Override
	public String toString() {
		return "Room [price=" + price + ", placesInRoom=" + placesInRoom + ", roomLvl=" + roomLvl + "]";
	}

}
