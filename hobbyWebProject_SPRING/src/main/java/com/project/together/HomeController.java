package com.project.together;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.service.BoardService;
import com.project.service.FileUploadService;
import com.project.vo.BoardVO;

import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController{
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private JdbcTemplate template;
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
	@RequestMapping("/{category}")
	public String search(@PathVariable("category") String category, Model model) {
		System.out.println("컨트롤러의 search() 메소드");
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("board", BoardService.class);
		String recategory = category.toUpperCase();
		List<BoardVO> bdlist = service.getBoardList(recategory, model);
//		String category = request.getParameter("category");
		model.addAttribute("category", category);
		model.addAttribute("bdlist", bdlist);
		return "search";
	}
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") int boardID, Model model) {
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("board", BoardService.class);
		BoardVO vo = service.view(boardID, model);
		model.addAttribute("vo", vo);
		return "view";
	}
	//처음 사용자가 글쓰기 버튼을 누르면 GET으로 파라미터를 받고, 글 내용을 입력한 뒤 요청되는 write은 POST로 파라미터를 받는다.
	@RequestMapping(value= {"/write", "/{category}/write"}, method=RequestMethod.GET)
	public String write(@PathVariable("category") String category, HttpServletRequest request) {
		//service.write(request, model);
		//search 페이지에서 글쓰기 버튼을 누르면 해당 카테고리를 파라미터 값으로 받아와 write 페이지에 다시 넘겨준다.
		System.out.println("카테고리: "+ category);
		System.out.println("re카테고리: "+ category.toUpperCase());
		request.setAttribute("category", category);
		return "write";
	}

	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String writeAction(HttpServletRequest request, HttpServletResponse response, Model model){
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
		BoardService service = ctx.getBean("board", BoardService.class);
		FileUploadService upload = new FileUploadService();
		//multipart form으로 전송된 데이터를 fileupload 클래스로 넘겨 첨부파일이 존재하면 저장하고 vo객체를 가져온다.
		BoardVO vo = upload.fileupload(request, response);
		//BoardService 클래스의 writeAction메서드를 실행하고 결과값을 가져온다.
		int result = service.writeAction(vo);
//		result = 0이면 빈 값이 존재하거나 데이터베이스 저장 실패를 의미한다.
		if(result == 0) {
//			request.setAttribute("vo", vo);
			model.addAttribute("vo", vo);
			System.out.println("반환: "+vo);
			return "write";
//			return "write";
		}else {
//			저장이 완료되면 작성한 글의 view 페이지로 redirect한다.
//			model.addAttribute("boardID",boardID);
			System.out.println("글작성완료: " + result);
			return "redirect:view/" + result;
		}
	}
}
