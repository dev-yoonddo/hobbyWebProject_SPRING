package com.project.service.Impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.project.dao.HeartDAO;
import com.project.service.HeartService;
import com.project.vo.HeartVO;

@Service
public class HeartServiceImpl implements HeartService{
	private HeartDAO heartDAO;
	
	@Autowired
	public HeartServiceImpl(HeartDAO heartDAO) {
		// TODO Auto-generated constructor stub
		this.heartDAO = heartDAO;
	}
	@Override
	public int checkHeart(HeartVO vo) {
		System.out.println("글번호:"+vo.getBoardID());
		System.out.println("사용자:"+vo.getUserID());
		return heartDAO.checkHeart(vo);
	}
	@Override
	public int heartAction(HttpServletRequest request, HttpServletResponse response){
		System.out.println("HeartService 클래스의 heartAction() 메소드");
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		String userID = request.getParameter("userID");
		HeartVO vo = new HeartVO(boardID, userID);
		return heartDAO.heartAction(vo);
	}
	@Override
	public int heartDelete(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("HeartService 클래스의 deleteHeart() 메소드");
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		String userID = request.getParameter("userID");
		HeartVO vo = new HeartVO(boardID, userID);
		return heartDAO.heartDelete(vo);
	}

}
