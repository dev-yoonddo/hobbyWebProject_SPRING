package com.project.service.Impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.dao.HeartDAO;
import com.project.dao.UserDAO;
import com.project.service.BoardService;
import com.project.service.UserService;
import com.project.vo.BoardVO;
import com.project.vo.UserVO;

@Service
public class UserServiceImpl implements UserService{
	private final UserDAO userDAO;
	public UserServiceImpl(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Override
	public List<String> getEmailList(){
		return userDAO.getEmailList();
	}
	@Override
	public int joinAction(HttpServletRequest request, HttpServletResponse response) {
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
	@Override
	public int joinCheckID(String userID) {

		return userDAO.joinCheckID(userID);
	}
	//로그인 실행
	@Override
	public int loginAction(HttpServletRequest request, HttpServletResponse response) {
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		return userDAO.loginAction(userID, userPassword);
	}
	//회원 정보 가져오기
	@Override
	public UserVO getUserVO(String userID) {
		System.out.println("UserService: "+userID);
		UserVO vo = userDAO.getUserVO(userID);
		System.out.println("회원 정보: "+vo);
		return vo;
	}
	//회원 정보 업데이트
	@Override
	public int userUpdateAction(HttpServletRequest request, HttpServletResponse response) {
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
	@Override
	public int userDeleteAction(String userID) {
		System.out.println("탈퇴유저: " +userID);
		return userDAO.userDeleteAction(userID);
	}
}
