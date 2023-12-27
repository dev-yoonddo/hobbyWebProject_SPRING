package com.project.together;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.project.service.AllService;
import com.project.service.BoardService;
import com.project.service.CmtService;
import com.project.service.FileService;
import com.project.service.HeartService;
import com.project.service.UserService;
import com.project.service.Impl.AllServiceImpl;

@Configuration
public class Constant {

//	다른 클래스(MvcboardDAO)에서 접근해서 사용을 해야되기 때문에 public으로 선언한다.
	public static JdbcTemplate template;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CmtService cmtService;

	@Autowired
	private HeartService heartService;

	@Autowired
	private FileService fileService;
	
	@Bean
    public AllService allServiceImplementation() {
        return new AllServiceImpl(userService, boardService, cmtService, heartService, fileService);
    }
}
