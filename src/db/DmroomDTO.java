package db;

import java.io.Serializable;

public class DmroomDTO implements Serializable{
	private String roomname;
	private String id;
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
