package db;

import java.io.Serializable;

public class FavoriteDTO implements Serializable{
	private String no;
	private String id;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
