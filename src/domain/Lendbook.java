package domain;

import java.util.Date;

public class Lendbook {
	private String lendbookNum;
	private String lendbookName;
	private String lendbookAuthor;
	private String lendbookPress;
	private String lendbookType;
	private Date lenddate;
	private String backdate;
	public String getLendbookNum() {
		return lendbookNum;
	}
	public void setLendbookNum(String lendbookNum) {
		this.lendbookNum = lendbookNum;
	}
	public String getLendbookName() {
		return lendbookName;
	}
	public void setLendbookName(String lendbookName) {
		this.lendbookName = lendbookName;
	}
	public String getLendbookAuthor() {
		return lendbookAuthor;
	}
	public void setLendbookAuthor(String lendbookAuthor) {
		this.lendbookAuthor = lendbookAuthor;
	}
	public String getLendbookPress() {
		return lendbookPress;
	}
	public void setLendbookPress(String lendbookPress) {
		this.lendbookPress = lendbookPress;
	}
	public String getLendbookType() {
		return lendbookType;
	}
	public void setLendbookType(String lendbookType) {
		this.lendbookType = lendbookType;
	}
	public Date getLenddate() {
		return lenddate;
	}
	public void setLenddate(Date lenddate) {
		this.lenddate = lenddate;
	}
	public String getBackdate() {
		return backdate;
	}
	public void setBackdate(String backdate) {
		this.backdate = backdate;
	}
 


	
}
