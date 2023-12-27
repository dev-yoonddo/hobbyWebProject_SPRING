package com.project.service.Impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.project.dao.BoardDAO;
import com.project.dao.HeartDAO;
import com.project.dao.UserDAO;
import com.project.service.BoardService;
import com.project.vo.*;

@Service
public abstract class BoardServiceImpl implements BoardService {
	
	private BoardDAO boardDAO;
	
	@Autowired
	public BoardServiceImpl(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
//	@Override
//	public void execute(BoardVO boardVO) {
//		System.out.println("InsertService의 execute() 메소드 - VO 사용");
////		System.out.println(mvcboardVO);
//		
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		BoardDAO boardDAO = ctx.getBean("BoardDAO", BoardDAO.class);
////		메인글을 테이블에 저장하는 메소드를 호출한다.
//		boardDAO.write(boardVO);
//	}

//	@Override
//	public void execute(Model model) {
//		System.out.println("InsertService의 execute() 메소드 - Model 사용");
////		컨트롤러에서 Model 인터페이스 객체에 저장해서 넘겨준 HttpServletRequest 인터페이스 객체에서 insert.jsp에서
////		입력한 데이터를 읽어낸다.
////		Model 인터페이스 객체는 key와 value로 구성된 데이터 구조를 가지므로 asMap() 메소드로 Map<String, Object>
////		타입으로 변환시켜 저장한다.
//		Map<String, Object> map = model.asMap();
//		
////		Model 인터페이스 객체가 Map<String, Object> 타입으로 변환되서 저장된 객체에서 key가 "request"인 value
////		(insert.jsp에서 넘어온 데이터)를 얻어온다.
//		HttpServletRequest request = (HttpServletRequest) map.get("request");
//		
////		Model 인터페이스 객체에 저장되서 넘어온 HttpServletRequest 인터페이스 객체에서 insert.jsp에서 넘어온
////		데이터를 받는다.
//		String userID = request.getParameter("userID");
//		int boardID = Integer.parseInt(request.getParameter("boardID"));
//		String boardCategory = request.getParameter("category");
//		String boardTitle = request.getParameter("title");
//		String boardContent = request.getParameter("content");
//		int boardAvailable = Integer.parseInt(request.getParameter("available"));
//		
////		MvcboardVO 클래스의 bean을 얻어온다.
//		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
//		BoardVO boardVO = ctx.getBean("boardVO", BoardVO.class);
//
////		MvcboardVO 클래스의 bean에 insert.jsp에서 HttpServletRequest 인터페이스 객체로 넘어온 데이터를 저장한다.
//		boardVO.setBoardID(boardID);
//		boardVO.setUserID(userID);
//		boardVO.setBoardCategory(boardCategory);
//		boardVO.setBoardTitle(boardTitle);
//		boardVO.setBoardContent(boardContent);
//		boardVO.setBoardAvailable(boardAvailable);
//
//		
////		테이블에 메인글을 저장하는 메소드를 호출한다.
//		BoardService bdservice = ctx.getBean("board", BoardService.class);
//		bdservice.execute(boardVO);
//	}
	
@Override
public List<BoardVO> getBoardList(String category, Model model){
	System.out.println("BoardServiceImpl 실행 ~~~");
//	Map<String, Object> map = model.asMap();
//	HttpServletRequest request = (HttpServletRequest) map.get("request");

//	BoardService bdlist = ctx.getBean("board", BoardService.class);
//	bdlist.getBoardList(model);
	return boardDAO.getBoardList(category, model);
}
@Override
public BoardVO getBoardVO(int boardID, Model model) {

	return boardDAO.getBoardVO(boardID, model);
}

@Override
public int writeAction(BoardVO vo){
	//BoardVO vo = ctx.getBean("boardVO", BoardVO.class);
	System.out.println("글쓰기 유저: " + vo.getUserID());
	int result = boardDAO.writeAction(vo);
	System.out.println("글쓰기 결과: " + result);
	return result;
}
@Override
public int updateAction(BoardVO vo) {
	int result = boardDAO.updateAction(vo);
	return result;
}
@Override
public String getFilename(int boardID) {
	String result = boardDAO.getFilename(boardID);
	return result;
}
@Override
public int viewCount(int boardID) {
	return boardDAO.viewCount(boardID);
}
@Override
public int downCount(int boardID) {
	int result = boardDAO.downCount(boardID);
	return result;
}
@Override
public int heartCount(HttpServletRequest request, HttpServletResponse response) {
	int boardID = Integer.parseInt(request.getParameter("boardID"));
	int result = boardDAO.heartCount(boardID);
	return result;
}
@Override
public int heartMinus(HttpServletRequest request, HttpServletResponse response) {
	int boardID = Integer.parseInt(request.getParameter("boardID"));
	int result = boardDAO.heartMinus(boardID);
	return result;
}
@Override
public int deleteAction(int boardID) {
	int result = boardDAO.deleteAction(boardID);
	System.out.println("게시글삭제: " + result);
	return result;
}

}
