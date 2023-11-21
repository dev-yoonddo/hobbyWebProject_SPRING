package com.project.vo;

public class CommentVO {
	private int cmtID;
	private int boardID;
	private String userID;
	private String cmtContent;
	private int cmtAvailable;
	private String cmtDate;
	
	public CommentVO() {}
	public CommentVO(int boardID, String userID, String cmtContent) {
		this.boardID = boardID;
		this.userID = userID;
		this.cmtContent = cmtContent;
	}
	public int getCmtID() {
		return cmtID;
	}
	public void setCmtID(int cmtID) {
		this.cmtID = cmtID;
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
	public String getCmtContent() {
		return cmtContent;
	}
	public void setCmtContent(String cmtContent) {
		this.cmtContent = cmtContent;
	}
	public int getCmtAvailable() {
		return cmtAvailable;
	}
	public void setCmtAvailable(int cmtAvailable) {
		this.cmtAvailable = cmtAvailable;
	}
	public String getCmtDate() {
		return cmtDate;
	}
	public void setCmtDate(String cmtDate) {
		this.cmtDate = cmtDate;
	}
	@Override
	public String toString() {
		return "CommentVO [cmtID=" + cmtID + ", boardID=" + boardID + ", userID=" + userID + ", cmtContent="
				+ cmtContent + ", cmtAvailable=" + cmtAvailable + ", cmtDate=" + cmtDate + "]";
	}
	
	
}
