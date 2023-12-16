package com.project.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.project.together.Constant;
import com.project.vo.BoardVO;

@Repository
public class BoardDAO{
	private JdbcTemplate template;
	private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	
	public BoardDAO() {
		this.template = Constant.template;
	}
	StringBuilder sql = new StringBuilder();

	public List<BoardVO> getBoardList(String category, Model model){
		try {
		System.out.println("BoardDAO 클래스의 boardList() 메서드");
//		System.out.println("선택한 카테고리: " + request.getParameter("category"));
//		String category = request.getParameter("category");
		String sql = "select * from board where boardCategory = ? and boardAvailable = 1 order by boardID desc";
		//sql.append("select * from board where boardCategory like '%" +boardCategory+"%' order by boardID desc"); 
		List<BoardVO> list = template.query(sql.toString() , boardMapper, category.toUpperCase());
		System.out.println(sql);
		System.out.println(list);
		return list;
//		model.addAttribute("bdlist",list);
//		model.addAttribute("category",category);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public BoardVO getBoardVO(int boardID, Model model) {
		String sql = "select * from board where boardID = ?";
		BoardVO vo = template.queryForObject(sql, boardMapper , boardID);
		System.out.println("게시글 수정" + vo);
		return vo;
	}
	public String getDate() {
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return date;
	}
	public int max() {
		String sql = "select coalesce(MAX(boardID), 0) + 1 from board";
		int boardID = template.queryForInt(sql);
		System.out.println("boardID= " + boardID);
		return boardID;
	}
	//writeAction과 updateAction의 차이점은 boardID와 boardDate를 그대로 사용하는 점이 다르다.
	public int writeAction(BoardVO vo){
		int result = 0;
		//카테고리, 제목, 내용이 모두 입력되었을때만 데이터를 넣는다.
		if(!vo.getBoardCategory().equals("0") && !vo.getBoardTitle().isEmpty() && !vo.getBoardContent().isEmpty()) {
			int boardID = max();
			//전체 글의 max+1한 boardID값을 가져와 setBoardID로 넣어준다.
			vo.setBoardID(boardID);
			//현재시간을 구하는 메서드를 실행해 날자와 시간을 구한 후 setBoardDate로 넣어준다.
			vo.setBoardDate(getDate());
		
			System.out.println("글쓰기 : " + vo);
			System.out.println("시간: "+ vo.getBoardDate());
			String sql = "insert into board(boardID, boardTitle, userID, boardDate, boardContent, boardAvailable, boardCategory, viewCount, heartCount, filename, fileRealname, fileDownCount)"
					+ " values(?, ?, ?, ?, ?, 1, ?, 0, 0, ?, ?, 0)";
			System.out.println("boardIDDDDD: " + vo.getBoardID());
			result = template.update(sql,vo.getBoardID(), vo.getBoardTitle(), vo.getUserID(), vo.getBoardDate(), vo.getBoardContent(), vo.getBoardCategory(),  vo.getFilename(), vo.getFileRealname());
		}
		if(result == 1) {
			//성공적으로 데이터베이스에 저장되면 생성된 boardID를 반환한다.
			return vo.getBoardID();
		}else {
			//빈 값이 존재하거나 데이터베이스 전송에 실패하면 0을 반환한다.
			return result;
		}
	}
	public int updateAction(BoardVO vo) {
		int result = 0;
		Model model = null;
		BoardVO beforevo = getBoardVO(vo.getBoardID(), model);
		System.out.println("원래글파일" + beforevo.getFilename());
		vo.setUserID("user");
		//첨부파일을 변경했으면 다운로드 횟수를 0으로 초기화한다.
		if(vo.getFilename() != "" && !beforevo.getFilename().equals(vo.getFilename())) {
			vo.setFileDownCount(0);
		}else{
			//첨부파일이 그대로면 파일이름과 다운로드횟수를 그대로 저장한다.
			vo.setFilename(beforevo.getFilename());
			vo.setFileRealname(beforevo.getFileRealname());
			vo.setFileDownCount(beforevo.getFileDownCount());
		}
		System.out.println("다운로드횟수" + vo.getFileDownCount());
		if(!vo.getBoardCategory().equals("0") && !vo.getBoardTitle().isEmpty() && !vo.getBoardContent().isEmpty()) {
			String sql = "update board set boardTitle = ?, boardContent = ?, boardCategory = ?, filename = ?"
					+ ", fileRealname = ?, fileDownCount = ?"
					+ " where boardID = ? and userID = ?";
			result = template.update(sql, vo.getBoardTitle(), vo.getBoardContent(), vo.getBoardCategory(),  vo.getFilename(), vo.getFileRealname(), vo.getFileDownCount(), vo.getBoardID(), vo.getUserID());
		}
		if(result == 1) {
			//성공적으로 데이터베이스에 저장되면 생성된 boardID를 반환한다.
			return vo.getBoardID();
		}else {
			//빈 값이 존재하거나 데이터베이스 전송에 실패하면 0을 반환한다.
			return result;
		}
	}
	
	public String getFilename(int boardID) {
		String sql = "select filename from board where boardID = ?";
		BoardVO result = template.queryForObject(sql, boardMapper , boardID);
		System.out.println("다운로드파일: "+result.getFilename());
		return result.getFilename();
	}
	public int viewCount(int boardID) {
		String sql = "update board set viewCount = viewCount + 1 where boardID = ?";
		return template.update(sql, boardID);
	}
	public int downCount(int boardID) {
		String sql = "update board set fileDownCount = fileDownCount + 1 where boardID = ?";
		return template.update(sql, boardID);
	}
	//하트를 클릭하면 board 테이블의 heartCount 증가
	public int heartCount(int boardID) {
		String sql = "update board set heartCount = heartCount + 1 where boardID = ?";
		return template.update(sql, boardID);
	}
	//하트를 취소하면 board 테이블의 heartCount 감소
	public int heartMinus(int boardID) {
		String sql = "update board set heartCount = heartCount - 1 where boardID = ?";
		return template.update(sql, boardID);
	}
	//게시글 지우기
	public int deleteAction(int boardID) {
		String sql = "update board set boardAvailable = 0 where boardID = ?";
		return template.update(sql, boardID);
	}
}
