package com.project.vo;

public class HeartVO {
	private int boardID;
	private String userID;


public HeartVO() {
}

public HeartVO(int boardID, String userID) {
	this.boardID = boardID;
	this.userID = userID;
}

public int getBoardID() {
	return boardID;
}

public void setBoardID(int boardID) {
	this.boardID = boardID;
}

public String getUserID() {
	return userID;
}

public void setUserID(String userID) {
	this.userID = userID;
}



}
