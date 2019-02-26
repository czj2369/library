package domain;

import java.util.Date;

public class Bookedbook {
	private String userID;
	private String bookBookName;
	private String bookBookNum;
	private String bookTime;
	
	private String bookedbookNum;
	private String bookedbookName;
	private String bookedbookAuthor;
	private String bookedbookPress;
	private String bookedbookType;
	private Date bookedDate;
	private String getDate;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getBookBookName() {
		return bookBookName;
	}
	public void setBookBookName(String bookBookName) {
		this.bookBookName = bookBookName;
	}
	public String getBookBookNum() {
		return bookBookNum;
	}
	public void setBookBookNum(String bookBookNum) {
		this.bookBookNum = bookBookNum;
	}
	public String getBookTime() {
		return bookTime;
	}
	public void setBookTime(String bookTime) {
		this.bookTime = bookTime;
	}

	
	
	public String getBookedbookNum() {
		return bookedbookNum;
	}
	public void setBookedbookNum(String bookedbookNum) {
		this.bookedbookNum = bookedbookNum;
	}
	public String getBookedbookName() {
		return bookedbookName;
	}
	public void setBookedbookName(String bookedbookName) {
		this.bookedbookName = bookedbookName;
	}
	public String getBookedbookAuthor() {
		return bookedbookAuthor;
	}
	public void setBookedbookAuthor(String bookedbookAuthor) {
		this.bookedbookAuthor = bookedbookAuthor;
	}
	public String getBookedbookPress() {
		return bookedbookPress;
	}
	public void setBookedbookPress(String bookedbookPress) {
		this.bookedbookPress = bookedbookPress;
	}
	public String getBookedbookType() {
		return bookedbookType;
	}
	public void setBookedbookType(String bookedbookType) {
		this.bookedbookType = bookedbookType;
	}
	public Date getBookedDate() {
		return bookedDate;
	}
	public void setBookedDate(Date bookedDate) {
		this.bookedDate = bookedDate;
	}
	public String getGetDate() {
		return getDate;
	}
	public void setGetDate(String getDate) {
		this.getDate = getDate;
	}
}
