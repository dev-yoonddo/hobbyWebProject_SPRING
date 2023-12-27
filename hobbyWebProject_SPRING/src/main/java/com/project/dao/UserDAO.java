package com.project.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.together.Constant;
import com.project.vo.BoardVO;
import com.project.vo.HeartVO;
import com.project.vo.UserVO;

@Repository
public class UserDAO{
	
	private JdbcTemplate template;
	private RowMapper<UserVO> userMapper = BeanPropertyRowMapper.newInstance(UserVO.class);
	private RowMapper<String> stringMapper = BeanPropertyRowMapper.newInstance(String.class);
	
	public UserDAO() {}
	//다른 클래스에서 UserDAO클래스의 메서드를 사용하려고 할 때 private으로 선언한 JdbcTemplate에 접근할 수 없기 때문에
	//생성자를 작성해준다.
	@Autowired
	public void setTemplate() {
		this.template = Constant.template;
	}
	//이메일 리스트를 넘겨 사용자가 입력한 이메일이 이미 존재하는지 확인
	public List<String> getEmailList(){
		String sql = "select userEmail from user where userEmail is not null";
		try {
			List<String> mailList = template.queryForList(sql.toString(), String.class);
			System.out.println(mailList);
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
		String sql = "select userID from user where userID = ? and userAvailable = 1";
		try {
			String exist = (String)template.queryForObject(sql, stringMapper, userID);
			System.out.println("입력 받은 아이디: "+userID);
			System.out.println("아이디 중복 결과: "+exist);
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
			String exist = (String)template.queryForObject(sql, stringMapper, userID, userPassword);
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
			UserVO vo = template.queryForObject(sql, userMapper, userID);
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
