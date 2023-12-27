package com.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.project.vo.UserVO;

@Component
public interface UserService extends BoardService{
		
	//UserService
	List<String> getEmailList();
	int joinAction(HttpServletRequest request, HttpServletResponse response);
	int joinCheckID(String userID) throws Exception;
	int loginAction(HttpServletRequest request, HttpServletResponse response);
	UserVO getUserVO(String userID);
	int userUpdateAction(HttpServletRequest request, HttpServletResponse response);
	int userDeleteAction(String userID);
}
