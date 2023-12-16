package com.project.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.together.Constant;
import com.project.vo.HeartVO;

@Repository
public class HeartDAO {
	private JdbcTemplate template;
//	private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	public HeartDAO() {
		this.template = Constant.template;
	}
	public int checkHeart(HeartVO vo) {
		try {
			String sql = "select * from heart where boardID = ? and userID = ?";
			HeartVO check = (HeartVO) template.queryForObject(sql.toString(),new BeanPropertyRowMapper<HeartVO>(HeartVO.class),vo.getBoardID(), vo.getUserID());
			if(check != null) {
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	public int heartAction(HeartVO vo) {
		String sql = "insert into heart (boardID, userID) values(?, ?)";
		return template.update(sql, vo.getBoardID(), vo.getUserID());
	}
	public int heartDelete(HeartVO vo) {
		String sql = "delete from heart where boardID = ? and userID = ?";
		return template.update(sql, vo.getBoardID(), vo.getUserID());
	}
}
