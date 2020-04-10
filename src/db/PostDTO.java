package db;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

public class PostDTO implements Serializable{
	private String no = null;
	private String day = null;
	private String id = null;
	private String text = null;
//	private Blob image = null;
	
	public String getNo() {
		return no;
	}
	
	public void setNo(String no) {
		this.no = no;
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
