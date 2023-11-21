package com.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.project.together.Constant;
import com.project.vo.BoardVO;
import com.project.vo.HeartVO;
import com.project.vo.UserVO;

public class UserDAO {
	private JdbcTemplate template;
//	private RowMapper<BoardVO> boardMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	public UserDAO() {
		this.template = Constant.template;
	}
	
	//이메일 리스트를 넘겨 사용자가 입력한 이메일이 이미 존재하는지 확인
	public List<String> getEmailList(){
		try {
			String sql = "select not null userEmail from user";
			List<String> mailList = template.queryForList(sql.toString(), String.class);
			//System.out.println(mailList);
			return mailList;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//회원가입 실행
	public int joinAction(UserVO vo) {
		String sql = "insert into user(userID, userName, userEmail, userBirth, userPhone, userPassword, userAvailable, userEmailHash, userEmailChecked)"
				+ " values(?, ?, ?, ?, ?, ?, 1, null, 0)";
		template.update(sql, vo.getUserID(), vo.getUserName(), vo.getUserEmail(), vo.getUserBirth(),vo.getUserPhone(), vo.getUserPassword());
		return 0;
	}
	//아이디 중복검사
	public int joinCheckID(String userID) {
		try {
			String sql = "select userID from user where userID = ?";
			String exist = (String)template.queryForObject(sql, new BeanPropertyRowMapper<String>(String.class), userID);
			if(exist != null) {
				return 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	//로그인 실행
	public int loginAction(String userID, String userPassword) {
		try {
			String sql = "select * from user where userID = ? and userPassword = ?";
			String exist = (String)template.queryForObject(sql, new BeanPropertyRowMapper<String>(String.class), userID, userPassword);
			if(exist != null) {
				return 1; //회원 정보가 존재하면 return 1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0; //존재하지 않으면 return 0;
	}
	//회원 정보 가져오기
	public UserVO getUserVO(String userID) {
		try {
			String sql = "select * from user where userID = ? and userAvailable = 1";
			UserVO vo = template.queryForObject(sql, new BeanPropertyRowMapper<UserVO>(UserVO.class), userID);
			System.out.println("userDAO 회원 정보: " +vo);
			return vo;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	//회원 정보 업데이트
	public int userUpdateAction(UserVO vo) {
		//업데이트 하기 전 정보를 가져온다.
		UserVO beforevo = getUserVO(vo.getUserID());
		//이전 이메일과 업데이트 이메일이 같으면 userEmailChecked를 그대로 사용한다.
		int checked = 0;
		if(beforevo.getUserEmail().equals(vo.getUserEmail())) {
			checked = beforevo.getUserEmailChecked();
		}
		String sql = "update user set userName = ?, userEmail = ?, userBirth = ?, userPhone = ?, userPassword = ?, userEmailChecked = ?"
				+ " where userID = ?";
		return template.update(sql, vo.getUserName(), vo.getUserEmail(), vo.getUserBirth(), vo.getUserPhone(), vo.getUserPassword(), checked, vo.getUserID());
		
	}
	//회원 탈퇴
	public int userDeleteAction(String userID) {
		String sql = "update user set userAvailable = 0 where userID = ?";
		return template.update(sql, userID);
	}
}
