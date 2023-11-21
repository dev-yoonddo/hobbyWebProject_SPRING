package com.project.vo;

public class UserVO {
	private String userID;
	private String userName;
	private String userEmail;
	private String userBirth;
	private String userPhone;
	private String userPassword;
	private int userAvailable;
	private String userEmailHash;
	private int userEmailChecked;
	
	public UserVO() {}

	public UserVO(String userID, String userName, String userEmail, String userBirth, String userPhone,String userPassword) {
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userBirth = userBirth;
		this.userPhone = userPhone;
		this.userPassword = userPassword;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserBirth() {
		return userBirth;
	}
	public void setUserBirth(String userBirth) {
		this.userBirth = userBirth;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public int getUserAvailable() {
		return userAvailable;
	}
	public void setUserAvailable(int userAvailable) {
		this.userAvailable = userAvailable;
	}
	public String getUserEmailHash() {
		return userEmailHash;
	}
	public void setUserEmailHash(String userEmailHash) {
		this.userEmailHash = userEmailHash;
	}
	public int getUserEmailChecked() {
		return userEmailChecked;
	}
	public void setUserEmailChecked(int userEmailChecked) {
		this.userEmailChecked = userEmailChecked;
	}
	@Override
	public String toString() {
		return "UserVO [userID=" + userID + ", userName=" + userName + ", userEmail=" + userEmail + ", userBirth="
				+ userBirth + ", userPhone=" + userPhone + ", userPassword=" + userPassword + ", userAvailable="
				+ userAvailable + ", userEmailHash=" + userEmailHash + ", userEmailChecked=" + userEmailChecked + "]";
	}
	
	
}
