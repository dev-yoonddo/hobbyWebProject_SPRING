package com.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.project.dao.HeartDAO;
import com.project.dao.UserDAO;
import com.project.vo.UserVO;

public class UserService {
	
	public List<String> getEmailList(){
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		return userDAO.getEmailList();
	}
	public int joinAction(HttpServletRequest request, HttpServletResponse response) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		String userID = request.getParameter("userID");
		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String userBirth = request.getParameter("userBirth");
		String userPhone = request.getParameter("userPhone");
		String userPassword = request.getParameter("userPassword");
		UserVO vo = new UserVO(userID, userName, userEmail,userBirth, userPhone, userPassword);
		int exist = userDAO.joinCheckID(userID); //아이디가 이미 존재하는지 다시 검사
		if(exist == 0) { //존재하지 않으면 회원가입 실행
			userDAO.joinAction(vo);
		}
//		}else {
//			List<String> emailList = userDAO.getEmailList();
//			request.setAttribute("emailList", emailList);
//			request.setAttribute("vo", vo);
//		}
		return exist;
	}
	//아이디 중복 검사
	public int joinCheckID(HttpServletRequest request, HttpServletResponse response) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		String userID = request.getParameter("userID");
		return userDAO.joinCheckID(userID);
	}
	//로그인 실행
	public int loginAction(HttpServletRequest request, HttpServletResponse response) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		return userDAO.loginAction(userID, userPassword);
	}
	//회원 정보 가져오기
	public UserVO getUserVO(String userID) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		System.out.println("UserService: "+userID);
		UserVO vo = userDAO.getUserVO(userID);
		System.out.println("회원 정보: "+vo);
		return vo;
	}
	//회원 정보 업데이트
	public int userUpdateAction(HttpServletRequest request, HttpServletResponse response) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		String userID = request.getParameter("userID");
		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		String userBirth = request.getParameter("userBirth");
		String userPhone = request.getParameter("userPhone");
		String userPassword = request.getParameter("userPassword");
		UserVO vo = new UserVO(userID, userName,userEmail, userBirth, userPhone, userPassword);
		return userDAO.userUpdateAction(vo);
	}
	//회원 탈퇴
	public int userDeleteAction(String userID) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		UserDAO userDAO = ctx.getBean("userDAO", UserDAO.class);
		System.out.println("탈퇴유저: " +userID);
		return userDAO.userDeleteAction(userID);
	}
}
