package com.project.service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.dao.BoardDAO;
import com.project.dao.CommentDAO;
import com.project.dao.HeartDAO;
import com.project.service.CmtService;
import com.project.vo.CommentVO;
import com.project.vo.HeartVO;

@Service
public class CmtServiceImpl implements CmtService{
	
	private CommentDAO cmtDAO;
	
	@Autowired
	public CmtServiceImpl(CommentDAO cmtDAO) {
		this.cmtDAO = cmtDAO;
	}
	@Override
	public List<CommentVO> getCmtList(int boardID, Model model){
		return cmtDAO.getCmtList(boardID, model);
	}
	@Override
	public int cmtWriteAction(HttpServletRequest request, HttpServletResponse response) {
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		String userID = request.getParameter("userID");
		String cmtContent = request.getParameter("content");
		System.out.println(boardID);
		System.out.println(userID);
		System.out.println(cmtContent);
		CommentVO vo = new CommentVO(boardID, userID, cmtContent);

		return cmtDAO.cmtWriteAction(vo);
	}
	@Override
	public int cmtDeleteAction(HttpServletRequest request, HttpServletResponse response) {
		int cmtID = Integer.parseInt(request.getParameter("cmtID"));
		String userID = request.getParameter("userID");
		System.out.println("댓글번호:"+cmtID);
		return cmtDAO.cmtDeleteAction(cmtID, userID);
	}
	@Override
	public int cmtAllDeleteAction(int boardID) {
		return cmtDAO.cmtAllDeleteAction(boardID);
	}
}
