package com.project.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.project.together.Constant;
import com.project.vo.BoardVO;
import com.project.vo.CommentVO;

@Repository
public class CommentDAO {
	private JdbcTemplate template;
	private RowMapper<CommentVO> cmtMapper = BeanPropertyRowMapper.newInstance(CommentVO.class);
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	public CommentDAO() {
		this.template = Constant.template;
	}
	
	public List<CommentVO> getCmtList(int boardID, Model model){
		try {
			System.out.println("CmtDAO 클래스의 cmtList() 메서드");
	//		System.out.println("선택한 카테고리: " + request.getParameter("category"));
	//		String category = request.getParameter("category");
			String sql = "select * from comment where boardID = ? and cmtAvailable = 1 order by cmtID desc";
			//sql.append("select * from board where boardCategory like '%" +boardCategory+"%' order by boardID desc"); 
			List<CommentVO> cmtlist = template.query(sql.toString() ,cmtMapper, boardID);
			System.out.println(cmtlist);
			return cmtlist;
	//		model.addAttribute("bdlist",list);
	//		model.addAttribute("category",category);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public int cmtWriteAction(CommentVO vo) {
		String sql = "insert into comment (cmtID, boardID, userID, cmtContent, cmtAvailable, cmtDate)"
				+ " values (?, ?, ?, ?, 1, ?)";
		int cmtID = max();
		String date = getDate();
		return template.update(sql, cmtID, vo.getBoardID(), vo.getUserID(), vo.getCmtContent(), date);
	}
	public int cmtDeleteAction(int cmtID, String userID) {
		String sql = "update comment set cmtAvailable = 0 where cmtID = ? and userID = ?";
		return template.update(sql, cmtID, userID);
	}
	//게시글 삭제시 댓글 전체삭제
	public int cmtAllDeleteAction(int boardID) {
		String sql = "update comment set cmtAvailable = 0 where boardID = ?";
		return template.update(sql, boardID);
	}
	public String getDate() {
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return date;
	}
	public int max() {
		String sql = "select coalesce(MAX(cmtID), 0) + 1 from comment";
		int cmtID = template.queryForInt(sql);
		System.out.println("cmtID= " + cmtID);
		return cmtID;
	}
}
