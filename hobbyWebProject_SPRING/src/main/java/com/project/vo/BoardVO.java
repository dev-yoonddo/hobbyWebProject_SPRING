package com.project.vo;

public class BoardVO {
private int boardID;
private String boardTitle;
private String userID;
private String boardDate;
private String boardContent;
private int boardAvailable;
private String boardCategory;
private int viewCount;
private int heartCount;
private String filename;
private String fileRealname;
private int fileDownCount;


public BoardVO() {
	super();
	// TODO Auto-generated constructor stub
}

public BoardVO(int boardID, String boardTitle, String userID, String boardContent,
		String boardCategory, String filename, String fileRealname) {
	this.boardID = boardID;
	this.boardTitle = boardTitle;
	this.userID = userID;
	this.boardContent = boardContent;
	this.boardCategory = boardCategory;
	this.filename = filename;
	this.fileRealname = fileRealname;
}

public int getBoardID() {
	return boardID;
}
public void setBoardID(int boardID) {
	this.boardID = boardID;
}
public String getBoardTitle() {
	return boardTitle;
}
public void setBoardTitle(String boardTitle) {
	this.boardTitle = boardTitle;
}
public String getUserID() {
	return userID;
}
public void setUserID(String userID) {
	this.userID = userID;
}
public String getBoardDate() {
	return boardDate;
}
public void setBoardDate(String boardDate) {
	this.boardDate = boardDate;
}
public String getBoardContent() {
	return boardContent;
}
public void setBoardContent(String boardContent) {
	this.boardContent = boardContent;
}
public int getBoardAvailable() {
	return boardAvailable;
}
public void setBoardAvailable(int boardAvailable) {
	this.boardAvailable = boardAvailable;
}
public String getBoardCategory() {
	return boardCategory;
}
public void setBoardCategory(String boardCategory) {
	this.boardCategory = boardCategory;
}
public int getViewCount() {
	return viewCount;
}
public void setViewCount(int viewCount) {
	this.viewCount = viewCount;
}
public int getHeartCount() {
	return heartCount;
}
public void setHeartCount(int heartCount) {
	this.heartCount = heartCount;
}
public String getFilename() {
	return filename;
}
public void setFilename(String filename) {
	this.filename = filename;
}
public String getFileRealname() {
	return fileRealname;
}
public void setFileRealname(String fileRealname) {
	this.fileRealname = fileRealname;
}
public int getFileDownCount() {
	return fileDownCount;
}
public void setFileDownCount(int fileDownCount) {
	this.fileDownCount = fileDownCount;
}
@Override
public String toString() {
	return "BoardVO [boardID=" + boardID + ", boardTitle=" + boardTitle + ", userID=" + userID + ", boardDate="
			+ boardDate + ", boardContent=" + boardContent + ", boardAvailable=" + boardAvailable + ", boardCategory="
			+ boardCategory + ", viewCount=" + viewCount + ", heartCount=" + heartCount + ", filename=" + filename
			+ ", fileRealname=" + fileRealname + ", fileDownCount=" + fileDownCount + "]";
}


}
