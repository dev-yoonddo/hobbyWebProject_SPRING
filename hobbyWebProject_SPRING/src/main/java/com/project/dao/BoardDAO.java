package com.project.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

import com.project.together.Constant;
import com.project.vo.BoardVO;

public class BoardDAO{
	private JdbcTemplate template;
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);

	public BoardDAO() {
		this.template = Constant.template;
	}
	StringBuilder sql = new StringBuilder();
	
	public List<BoardVO> getBoardList(HttpServletRequest request, Model model){
		try {
		System.out.println("BoardDAO 클래스의 boardList() 메서드");
		System.out.println("선택한 카테고리: " + request.getParameter("category"));
		String category = request.getParameter("category");
		String sql = "select * from board where boardCategory = ? order by boardID desc";
		//sql.append("select * from board where boardCategory like '%" +boardCategory+"%' order by boardID desc"); 
		List<BoardVO> list = template.query(sql.toString() ,new BeanPropertyRowMapper(BoardVO.class), category);
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
	public BoardVO view(int boardID, Model model) {
		String sql = "select * from board where boardID = ?";
		BoardVO vo = (BoardVO)template.queryForObject(sql, new BeanPropertyRowMapper<BoardVO>(BoardVO.class), boardID);
		System.out.println("보기: " + vo);
		return vo;
	}
	public int max() {
		String sql = "select coalesce(MAX(boardID), 0) + 1 from board";
		return (int)template.queryForObject(sql.toString(), new BeanPropertyRowMapper(BoardVO.class));
	}
	public int writeAction(BoardVO vo) {
		int boardID = max();
		System.out.println("제일큰아이디+1" + boardID);
		System.out.println("글쓰기 : " + vo);
		String sql = "insert into board (boardID, boardTitle, userID, boardDate, boardContent, boardAvailable, boardCategory, viewCount, heartCount, filename, fileRealname, fileDownCount)"
				+ "values (?, ?, ?, now(), ?, 1, ?, 0, 0, ?, ?, 0)";
		template.update(sql.toString(), boardID, vo.getBoardTitle(), vo.getUserID(), vo.getBoardContent(), vo.getBoardCategory(), vo.getFilename(), vo.getFileRealname());
		return boardID;
	}
}
