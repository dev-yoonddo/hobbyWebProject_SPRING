package com.project.together;

import org.springframework.jdbc.core.JdbcTemplate;

public class Constant {

//	다른 클래스(MvcboardDAO)에서 접근해서 사용을 해야되기 때문에 public으로 선언한다.
	public static JdbcTemplate template;
	
}
