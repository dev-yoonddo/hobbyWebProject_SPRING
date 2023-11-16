package com.project.together;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.service.BoardService;
import com.project.service.FileUploadService;
import com.project.vo.BoardVO;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private JdbcTemplate template;
	public JdbcTemplate getTemplate() {
		return template;
	}
	@Autowired		// @Autowired 어노테이션을 붙여준 메소드는 프로젝트가 시작될 때 자동으로 실행된다.
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		System.out.println("꺄오~~~~~~~~~~~~~~~");
		
//		여기까지 정상적으로 실행되면 컨트롤러에서 JdbcTemplate을 사용할 수 있다.
//		데이터베이스 연결은 주로 DAO 클래스에서 사용하므로 컨트롤러 이외의 클래스에서 JdbcTemplate을 사용할
//		수 있게 하기 위해서 적당한 이름의 패키지에 적당한 이름으로 클래스를 만들고 선언한 정적 변수에
//		servlet-context.xml 파일에서 생성된 JdbcTemplate 클래스의 bean을 넣어준다.
		Constant.template = this.template;
	}
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		System.out.println("컨트롤러의 home() 메소드");
		return "redirect:community";
	}
	@RequestMapping("/community")
	public String community(HttpServletRequest request, Model model) {
		System.out.println("컨트롤러의 community() 메소드");
		return "community";
	}
	@RequestMapping("/search")
	public String search(HttpServletRequest request, Model model) {
		System.out.println("컨트롤러의 search() 메소드");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("board", BoardService.class);
		List<BoardVO> bdlist = service.getBoardList(request, model);
		String category = request.getParameter("category");
		model.addAttribute("category", category);
		model.addAttribute("bdlist", bdlist);
		return "search";
	}
	@RequestMapping("/view")
	public String view(@RequestParam("id") int boardID, Model model) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("board", BoardService.class);
		BoardVO vo = service.view(boardID, model);
		model.addAttribute("vo", vo);
		return "view";
	}
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		//service.write(request, model);
		return "write";
	}

	@RequestMapping("/write/action")
	public String writeAction(HttpServletRequest request, HttpServletResponse response) throws Exception{
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("board", BoardService.class);
		FileUploadService upload = ctx.getBean("fileupload", FileUploadService.class);
		BoardVO vo = upload.fileupload(request, response);
		System.out.println(vo);
		int boardID = service.writeAction(vo);
//		model.addAttribute("boardID",boardID);
		request.setAttribute("boardID", boardID);
		System.out.println("글작성완료: " + boardID);
		return "view?id=" + boardID;
	}
}
