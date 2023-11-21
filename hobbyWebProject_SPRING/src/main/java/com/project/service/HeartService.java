package com.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.project.dao.HeartDAO;
import com.project.vo.HeartVO;

public class HeartService{

	public int checkHeart(HeartVO vo) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		HeartDAO heartDAO = ctx.getBean("heartDAO", HeartDAO.class);
		System.out.println("글번호:"+vo.getBoardID());
		System.out.println("사용자:"+vo.getUserID());
		return heartDAO.checkHeart(vo);
	}
	public int heartAction(HttpServletRequest request, HttpServletResponse response){
		System.out.println("HeartService 클래스의 heartAction() 메소드");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		HeartDAO heartDAO = ctx.getBean("heartDAO", HeartDAO.class);
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		String userID = request.getParameter("userID");
		HeartVO vo = new HeartVO(boardID, userID);
		return heartDAO.heartAction(vo);
	}
	
	public int heartDelete(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("HeartService 클래스의 deleteHeart() 메소드");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		HeartDAO heartDAO = ctx.getBean("heartDAO", HeartDAO.class);
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		String userID = request.getParameter("userID");
		HeartVO vo = new HeartVO(boardID, userID);
		return heartDAO.heartDelete(vo);
	}

}