package com.project.together;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.service.BoardService;
import com.project.service.CmtService;
import com.project.service.FileDownService;
import com.project.service.FileUploadService;
import com.project.service.HeartService;
import com.project.service.UserService;
import com.project.vo.BoardVO;
import com.project.vo.CommentVO;
import com.project.vo.HeartVO;
import com.project.vo.UserVO;

import org.springframework.ui.Model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	AbstractApplicationContext ctx = new GenericXmlApplicationContext("classpath:applicationCTX.xml");
	UserService urservice = ctx.getBean("user", UserService.class);
	BoardService service = ctx.getBean("board", BoardService.class);
	HeartService htservice = ctx.getBean("heart", HeartService.class);
	CmtService cmtservice = ctx.getBean("cmt", CmtService.class);
	FileDownService download = ctx.getBean("file", FileDownService.class);

	@Autowired		// @Autowired 어노테이션을 붙여준 메소드는 프로젝트가 시작될 때 자동으로 실행된다.
	public void setTemplate(JdbcTemplate template) {
		this.template = template;		
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
	@RequestMapping("/search/{category}")
	public String search(@PathVariable("category") String category, Model model) {
		System.out.println("컨트롤러의 search() 메소드");
		List<BoardVO> bdlist = service.getBoardList(category, model);
//		String category = request.getParameter("category");
		model.addAttribute("category", category);
		model.addAttribute("bdlist", bdlist);
		return "search";
	}
	@RequestMapping("/view/{id}")
	public String view(@PathVariable("id") int boardID, Model model) {
		service.viewCount(boardID);
		BoardVO vo = service.getBoardVO(boardID, model);
		HeartVO htvo = new HeartVO(boardID, "user");
		List<CommentVO> cmtlist = cmtservice.getCmtList(boardID, model);
		int exist = htservice.checkHeart(htvo);
		if(exist == 1) {
			model.addAttribute("exist", "Y");
		}else {
			model.addAttribute("exist", "N");
		}
		model.addAttribute("vo", vo);
		model.addAttribute("cmtlist", cmtlist);
		return "view";
	}
	
	//처음 사용자가 글쓰기 버튼을 누르면 GET으로 파라미터를 받고, 글 내용을 입력한 뒤 요청되는 write은 POST로 파라미터를 받는다.
	@RequestMapping(value= {"/write"}, method=RequestMethod.GET)
	public String write(HttpServletRequest request) {
		//service.write(request, model);
		//search 페이지에서 글쓰기 버튼을 누르면 해당 카테고리를 파라미터 값으로 받아와 write 페이지에 다시 넘겨준다.
//		System.out.println("카테고리: "+ category);
//		System.out.println("re카테고리: "+ category.toUpperCase());
		request.setAttribute("category", request.getParameter("category"));
		return "write";
	}

	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String writeAction(HttpServletRequest request, HttpServletResponse response, Model model){
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
	@RequestMapping("/update/{boardID}")
	public String update(@PathVariable("boardID") int boardID, Model model) {
		System.out.println("Controlller 의 update()");
		BoardVO vo = service.getBoardVO(boardID,model);
		model.addAttribute("vo", vo);
		return "update";
	}
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateAction(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("Controlller 의 updateAction()");
		FileUploadService upload = new FileUploadService();
		BoardVO vo = upload.fileupload(request, response);
		int result = service.updateAction(vo);
//		result = 0이면 빈 값이 존재하거나 데이터베이스 저장 실패를 의미한다.
		if(result == 0) {
			model.addAttribute("vo", vo);
			System.out.println("반환: "+vo);
			return "update";
		}else {
			System.out.println("글작성완료: " + result);
			return "redirect:view/" + result;
		}
	}
	@RequestMapping("/download")
	public void downloadAction(HttpServletRequest request, HttpServletResponse response) {
		int boardID = Integer.parseInt(request.getParameter("boardID"));
		int result = download.fileDownload(request, response);
		if(result == 0) {
			System.out.println("다운로드실패");
		}else {
			int count = service.downCount(boardID);
			if(count == 1) {
			System.out.println("다운로드성공");
			}
			
		}
	}
	@RequestMapping("/delete/{category}/{id}")
	public String deleteAction(@PathVariable("category")String category, @PathVariable("id") int boardID, Model model) {
		int result = cmtservice.cmtAllDeleteAction(boardID);
		if(result > 0) {
			int bdDel = service.deleteAction(boardID);
			if(bdDel > 0) {
				System.out.println("게시글 삭제 성공");
				result++;				
			}
		}
		if(result == 2) {
			return "redirect:/search/"+category;
		}else {
			return "redirect:/view/"+boardID;
		}
	}
	//하트 클릭시 ajax로 넘긴 값을 가져와 이미 하트를 눌렀는지 누르지 않았는지 판별하고 각 메서드 실행
	@RequestMapping("/heart")
	public void heartAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter script = response.getWriter();
		//ajax로 넘어온 value값 가져오기
		String value = request.getParameter("value");
		int result = 0;
		if(value.equals("none")) { //하트를 클릭했을때 넘어온 value값이 none이면 heartAction, exist이면 heartDelete 메서드 실행
			result = htservice.heartAction(request, response);
			if(result == 1){
				result += service.heartCount(request, response); //데이터가 정상적으로 들어가면 board테이블의 heartCount 수 증가
			}
		}else{
			result = htservice.heartDelete(request, response);
			if(result == 1){
				result += service.heartMinus(request, response); //데이터가 정상적으로 들어가면 board테이블의 heartCount 수 감소
			}
		}
		if(result == 2) { //두 가지의 메서드가 모두 정상적으로 실행되면 success 넘겨주기
			script.print("success");
		}
	}
	//댓글 작성하기
	@RequestMapping("/cmt")
	public void cmtWriteAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter script = response.getWriter();
	
		int result = cmtservice.cmtWriteAction(request, response);
		if(result == 1) {
			script.print("success");
		}

	}
	//댓글 삭제하기
	@RequestMapping("/cmt/delete")
	public void cmtDeleteAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter script = response.getWriter();

		int result = cmtservice.cmtDeleteAction(request, response);
		if(result == 1) {
			script.print("success");
		}
	}
	//로그인 페이지
	@RequestMapping("/login")
	public String login() {
		return "/login";
	}
	//회원가입 페이지
	@RequestMapping("/join")
	public String join(HttpServletRequest request, HttpServletResponse response) {
		List<String> emailList = urservice.getEmailList();
		request.setAttribute("emailList", emailList);
		return "/join";
	}
	//아이디 중복체크
	@RequestMapping("/join/checkID")
	public void joinCheckID(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter script = response.getWriter();
		int exist = urservice.joinCheckID(request, response);
		if(exist == 1) {
			script.print("fail");
		}else {
			script.print("success");
		}
	}
	//회원가입실행
	@RequestMapping("/join/action")
	public String joinAction(HttpServletRequest request, HttpServletResponse response) {
		int result = urservice.joinAction(request, response);
		if(result == 1) {
			return "/join";
		}
		return "redirect:login";
	}
	//로그인 실행
	@RequestMapping("/login/action")
	public String loginAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String userID = request.getParameter("userID");
		int result = urservice.loginAction(request, response);
		if(result == 1) {
			request.setAttribute("loginError", "sc");
			session.setAttribute("userID", userID);
			session.setMaxInactiveInterval(1800);
			System.out.println("로그인성공");
			return "redirect:/community";
		}else {
			System.out.println("로그인실패");
			request.setAttribute("loginError", "fail");
			return "/login";

		}
	}
	//로그아웃
	@RequestMapping("/logout")
	public String logoutAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:community";
	}
	//회원 정보 업데이트 페이지
	@RequestMapping("/user")
	public String userUpdate(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String userID = (String)session.getAttribute("userID");
		if(userID != null) {
			UserVO vo = urservice.getUserVO(userID);
			System.out.println("유저 정보: "+vo);
			request.setAttribute("vo", vo);
			return "userUpdate";
		}
		return "redirect:/community";
	}
	//회원 정보 업데이트 실행
	@RequestMapping("/user/action")
	public String userUpdateAction(HttpServletRequest request, HttpServletResponse response) {
		urservice.userUpdateAction(request, response);
		return "redirect:/user";
	}
	//회원 탈퇴
	@RequestMapping("/user/delete")
	public String userDeleteAction(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String userID = (String)session.getAttribute("userID");
		int result = urservice.userDeleteAction(userID);
		if(result == 1) {
			session.invalidate();
			return "redirect:/community";
		}else {
			return "userUpdate";
		}
	}
}	
