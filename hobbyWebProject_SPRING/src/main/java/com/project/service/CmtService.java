package com.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.project.vo.CommentVO;

public interface CmtService {

	List<CommentVO> getCmtList(int boardID, Model model);
	int cmtWriteAction(HttpServletRequest request, HttpServletResponse response);
	int cmtDeleteAction(HttpServletRequest request, HttpServletResponse response);
	int cmtAllDeleteAction(int boardID);

}
