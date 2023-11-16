package com.project.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.project.vo.BoardVO;

public interface Service {
	//void execute(BoardVO boardVO);
	//void execute(Model model);
	List<BoardVO> getBoardList(HttpServletRequest request, Model model);
	BoardVO view(int boardID, Model model);
	int writeAction(BoardVO vo);
}
