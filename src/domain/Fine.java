package domain;
//罚款信息类
public class Fine {
	private String userID;
	private String fineReason;
	private String fineTime;
	private String fineMoney;
	public String getFineMoney() {
		return fineMoney;
	}
	public void setFineMoney(String fineMoney) {
		this.fineMoney = fineMoney;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getFineReason() {
		return fineReason;
	}
	public void setFineReason(String fineReason) {
		this.fineReason = fineReason;
	}
	public String getFineTime() {
		return fineTime;
	}
	public void setFineTime(String fineTime) {
		this.fineTime = fineTime;
	}
	
	
}
