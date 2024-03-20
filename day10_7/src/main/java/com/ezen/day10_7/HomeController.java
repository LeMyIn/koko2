package com.ezen.day10_7;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class HomeController {
	@Autowired  
	SqlSession sqlSession; 
	
	@RequestMapping(value = "/")
	public String kk1(HttpServletRequest request) {
		HttpSession hs=request.getSession();
		hs.setAttribute("loginstate", false);
		boolean a= (Boolean) hs.getAttribute("loginstate");

		System.out.println("로그상태 : "+a);
		
		
		return "main";
		  
	}
	@RequestMapping(value = "/main")
	public String kk2() {
		return "main";
	}
	@RequestMapping(value = "/test")
	public String book2() {
		return "test";
	}
	@RequestMapping(value = "/aaa")
	public String book3() {
		return "aaa";
	}

	@RequestMapping(value = "/book_input")
	public String book1() {
		return "book_input";
	}
	
	
	@RequestMapping(value = "/bsave")
	public String book4( MultipartHttpServletRequest mul) throws IllegalStateException, IOException {
		Service service = sqlSession.getMapper(Service.class);
		mul.setCharacterEncoding("utf-8");
		System.out.println();
		MultipartFile mf =mul.getFile("image");
		String fname = mf.getOriginalFilename();
		String sname =filenames(fname,mf.getBytes());
		
		mf.transferTo(new File("C:\\이젠디지탈12\\spring\\day10_7\\src\\main\\webapp\\image"+"\\"+fname));
		System.out.println(fname);
		service.binsert(
				mul.getParameter("bname"),
				mul.getParameter("writer"),
				mul.getParameter("pday"),
				mul.getParameter("company"),
				sname,
				mul.getParameter("summary")
				);
		
		
		return "book_input";
	}

	private String filenames(String fname, byte[] bytes) throws IOException {
		UUID uid = UUID.randomUUID();//38자
		String sname =uid.toString()+"_"+fname;
		File aa = new File("C:\\이젠디지탈12\\spring\\day10_7\\src\\main\\webapp\\image\\"+sname);
		FileCopyUtils.copy(bytes, aa);// 경로를 집어 넣는다
		
		return sname;
	}

	@RequestMapping(value = "/book_out")
	public String bookout_view(Model mo) {
		Service service= sqlSession.getMapper(Service.class);
		 ArrayList<Book_DTO> list = service.book_out();
		 System.out.println(list);
		mo.addAttribute("list", list);
		return "book_out";
	}
//아레 부터 게시글
	@RequestMapping(value = "/board_list")
	public String board1(Model mo) {
		Service service = sqlSession.getMapper(Service.class);

		 ArrayList<Board_DTO> list = service.boarder_list();
		 System.out.println(list);
		mo.addAttribute("list", list);
		mo.addAttribute("list2", list);
		//페이징 추가 해야함
				
				
		return "board_list";
	}

	

	
	@RequestMapping(value = "/boardinsert_view")
	public String board2() {
		return "boardinsert_view";
	}

	@RequestMapping(value = "/board_view",method = RequestMethod.GET )
	public String board4(HttpServletRequest request , Model mo) throws UnsupportedEncodingException {
		Service service = sqlSession.getMapper(Service.class);
		request.setCharacterEncoding("utf-8");
		Board_DTO dto =service.board_view(request.getParameter("num"));
		
		service.count(request.getParameter("num"));
		
		mo.addAttribute("dto", dto);
		return "board_view";
	}

	@RequestMapping(value = "/board_save",method = RequestMethod.POST )
	public String board3(HttpServletRequest request ) throws UnsupportedEncodingException {
		Service service = sqlSession.getMapper(Service.class);
		request.setCharacterEncoding("utf-8");

		String name = request.getParameter("name");
		String pw = request.getParameter("pw");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		System.out.println(name+" "+pw+" "+subject+" "+content);
		service.board_insert(name,pw,subject,content);
		System.out.println("게시글 업로드 : "+subject );
		return "main";
	}

	@RequestMapping(value = "/board_update")
	public String board5(HttpServletRequest request,HttpSession session,Model mo) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		
		Service service = sqlSession.getMapper(Service.class);
		
		int ch= service.board_update(
				request.getParameter("num"),
				request.getParameter("id")
				);
		if (ch==1) {
			System.out.println("수정 가능 : "+request.getParameter("num")+"  "+request.getParameter("id") );
			
			
			Board_DTO dto =service.board_view(request.getParameter("num"));
			mo.addAttribute("dto", dto);
			return "board_update";
			
		}else {
			System.out.println("수정 불가 : "+request.getParameter("num")+"  "+request.getParameter("id") );
			session.setAttribute("alr", "회원 정보가 맞지 않습니다.");
			session.setAttribute("alret", true);
		}
		return "main";
	}

	@RequestMapping(value = "/board_update_do")
	public String board6(HttpServletRequest request ) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		Service service = sqlSession.getMapper(Service.class);
		service.board_update_do(
				request.getParameter("num"),
				request.getParameter("subject"),
				request.getParameter("content")
				);
		
		return "main";
	}

}
