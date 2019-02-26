package domain;

import java.util.Date;

public class Hbook {
	private String hbookNum;
	private String hbookName;
	private String hbookAuthor;
	private String hbookPress;
	private String hbookType;
	private Date hlendDate;
	private Date hbackDate;
	private String readerID;
	public String getReaderID() {
		return readerID;
	}
	public void setReaderID(String readerID) {
		this.readerID = readerID;
	}
	
	public int getHbookTimes() {
		return hbookTimes;
	}
	public void setHbookTimes(int hbookTimes) {
		this.hbookTimes = hbookTimes;
	}
	private int hbookTimes;
	public void setHlendDate(Date hlendDate) {
		this.hlendDate = hlendDate;
	}
	public void setHbackDate(Date hbackDate) {
		this.hbackDate = hbackDate;
	}
	
	public Date getHlendDate() {
		return hlendDate;
	}
	public Date getHbackDate() {
		return hbackDate;
	}
	public String getHbookNum() {
		return hbookNum;
	}
	public void setHbookNum(String hbookNum) {
		this.hbookNum = hbookNum;
	}
	public String getHbookName() {
		return hbookName;
	}
	public void setHbookName(String hbookName) {
		this.hbookName = hbookName;
	}
	public String getHbookAuthor() {
		return hbookAuthor;
	}
	public void setHbookAuthor(String hbookAuthor) {
		this.hbookAuthor = hbookAuthor;
	}
	public String getHbookPress() {
		return hbookPress;
	}
	public void setHbookPress(String hbookPress) {
		this.hbookPress = hbookPress;
	}
	public String getHbookType() {
		return hbookType;
	}
	public void setHbookType(String hbookType) {
		this.hbookType = hbookType;
	}
 

}
