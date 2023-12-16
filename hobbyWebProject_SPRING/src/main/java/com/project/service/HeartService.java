package com.project.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.project.vo.HeartVO;

public interface HeartService {

	int checkHeart(HeartVO vo);
	int heartAction(HttpServletRequest request, HttpServletResponse response);
	int heartDelete(HttpServletRequest request, HttpServletResponse response);

}
