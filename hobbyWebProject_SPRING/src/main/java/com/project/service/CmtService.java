package com.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;

import com.project.dao.CommentDAO;
import com.project.dao.HeartDAO;
import com.project.vo.CommentVO;
import com.project.vo.HeartVO;

public class CmtService{

	public List<CommentVO> getCmtList(int boardID, Model model){
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		CommentDAO cmtDAO = ctx.getBean("commentDAO", CommentDAO.class);
		return cmtDAO.getCmtList(boardID, model);
	}
	public int cmtWriteAction(HttpServletRequest request, HttpServletResponse response) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		CommentDAO cmtDAO = ctx.getBean("commentDAO", CommentDAO.class);
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		String userID = request.getParameter("userID");
		String cmtContent = request.getParameter("content");
		System.out.println(boardID);
		System.out.println(userID);
		System.out.println(cmtContent);
		CommentVO vo = new CommentVO(boardID, userID, cmtContent);

		return cmtDAO.cmtWriteAction(vo);
	}
	public int cmtDeleteAction(HttpServletRequest request, HttpServletResponse response) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		CommentDAO cmtDAO = ctx.getBean("commentDAO", CommentDAO.class);
		int cmtID = Integer.parseInt(request.getParameter("cmtID"));
		String userID = request.getParameter("userID");
		System.out.println("댓글번호:"+cmtID);
		return cmtDAO.cmtDeleteAction(cmtID, userID);
	}
	public int cmtAllDeleteAction(int boardID) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		CommentDAO cmtDAO = ctx.getBean("commentDAO", CommentDAO.class);
		return cmtDAO.cmtAllDeleteAction(boardID);
	}
}
