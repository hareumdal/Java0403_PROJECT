package db;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

public class PostDTO implements Serializable{
	private int no = 0;
	private String day = null;
	private String id = null;
	private String text = null;
//	private Blob image = null;
	
	public int getNo() {
		return no;
	}
	
	public void setNo(int i) {
		this.no = i;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String date) {
		this.day = date;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
//	
//	public Blob getImage() {
//		return image;
//	}
//	
//	public void setImage(Blob image) {
//		this.image = image;
//	}

}
