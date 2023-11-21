package com.project.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.project.vo.BoardVO;

public interface Service {
	//void execute(BoardVO boardVO);
	//void execute(Model model);
	List<BoardVO> getBoardList(String category, Model model);
	BoardVO getBoardVO(int boardID, Model model);
	int writeAction(BoardVO vo) throws Exception;
	int updateAction(BoardVO vo);
//	int fileDownload(HttpServletRequest request, HttpServletResponse response);
	String getFilename(int boardID);
	int viewCount(int boardID);
	int downCount(int boardID);
	int deleteAction(int boardID);
	int heartCount(HttpServletRequest request, HttpServletResponse response);
	int heartMinus(HttpServletRequest request, HttpServletResponse response);
}
