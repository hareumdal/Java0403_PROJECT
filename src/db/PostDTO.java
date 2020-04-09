package db;

import java.io.Serializable;
import java.sql.Blob;

public class PostDTO implements Serializable{
	private float no;
	private String day = null;
	private String id = null;
	private String text = null;
//	private Blob image = null;
	
	public float getNo() {
		return no;
	}
	
	public void setNo(float f) {
		this.no = f;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
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
	
//	public Blob getImage() {
//		return image;
//	}
//	
//	public void setImage(Blob image) {
//		this.image = image;
//	}

}
