package com.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ajax.ajaxDAO;
import com.fenye.Fenye;
import com.service.BlogService;
import com.service.Service;
import com.service.UserService;
import com.user.User;


@Controller
@RequestMapping("/user")
public class userControl extends control {
	
	@Autowired
	private Service service;
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private BlogService cardservice;
	
	@RequestMapping("/back")
	public String back(HttpSession session) {
		session.invalidate();
		return "login";
	}//����֮����Ҫ��һ���˳�������Ϊ��ʹ�ỰʧЧ,��Ȼ�Ƴ�����url��ַ���ɿ���������¼�������
	
	@RequestMapping("/load")
	public String load( String name, MultipartFile file,HttpSession session,HttpServletRequest req, HttpServletResponse resp) throws Exception, IOException { 
			if (!file.isEmpty()) {
		            byte[] bytes = file.getBytes();
		        	String src = req.getServletContext().getRealPath("/");
		    		
		    		String path=null;
		    		
		    		if(src.endsWith(File.separator)) {
		    			path="WEB-INF";
		    		}
		    		else {
		    			path="/WEB-INF";
		    		}
		    	 User user = (User)session.getAttribute("user");
			     String fileName = "" + user.getUsername();
		    	 File loadedFile = new File(src +path+"/load/" +  fileName);//���ﴴ���ļ���������ϴ��ļ���ʵ�ʴ�ŵ�ַ
		    	 file.transferTo(loadedFile);//transferTo(�ϴ��ļ��Ĵ��·��)
		    	 return toUsercenter(req, resp);
			}
			 return toUsercenter(req, resp);
	}//������ϴ������ʽ�̶�ֻ��Ҫ����Ҫ�󴴽���ͬ���ļ����ڵ�ַ�Ϳ�����,�����Ķ�ֻ��Ҫ�����ĵ��е�web���־Ϳ���
	
	@RequestMapping("/toUpload")
	public String toUpload(HttpSession session) {
		return "load";
	}

	
	 @RequestMapping("/showPicture")
	public void showPicture(HttpServletRequest req, HttpServletResponse resp) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		String src=req.getServletContext().getRealPath("/");
		System.out.println(">>>>>>>"+src);
		HttpSession session=req.getSession();//�����һ��֪ʶ�����session��request������һ����ȫ�ֱ���һ���Ǿֲ�����
		User user=(User)session.getAttribute("user");

		String path=null;
		
		if(src.endsWith(File.separator)) {
			path="WEB-INF";
		}
		else {
			path="/WEB-INF";
		}
		try (FileInputStream in = new FileInputStream(src+path+"/load/" + user.getUsername());
				OutputStream out = resp.getOutputStream()) {
			byte[] data = new byte[1024];
			int length = -1;
			while((length=in.read(data))!=-1) {
				out.write(data, 0, length);
				out.flush();
			}
		}
	}
	
	@RequestMapping("/toRegister")
	public String  toRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		return "register";
	
	}
	
	@RequestMapping("/Register")
	@ResponseBody
	public ajaxDAO Register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();//����н��и�ֵ����ԭ����Session�����û�д����µ�Session
		String username= req.getParameter("username");
		System.out.println("username"+username);
		String password= req.getParameter("password");
		System.out.println("password"+password);
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");//ajax�����ٲ���
		User user= userservice.register(username,password);
		System.out.println(user);
		if(user!=null) {
			return ajaxDAO.success();
		}
		else {
			return ajaxDAO.failure();
		}
	}
	//������Ҫע�����������ajax ��spring-MVC����Ҫ�����ļ�,������Ҫ��@ResponseBody
	
	@RequestMapping("/toLogin")
	public String toLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		return "login";																																								
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public ajaxDAO  login(HttpSession session,	String username,String password, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		User user=userservice.login(username, password);
		if(null==user) {
			//return "login";	
			return ajaxDAO.failure();	
		}
		else {
			session.setAttribute("user", user);
			session.setAttribute("username",username);
			return ajaxDAO.success();
			//return toUsercenter(req, resp);
		}
	}
	//ע������Ĳ�������Spring-MVC�����÷�����֮���ڲ����б��д���ǰ�˵�����
	
	@RequestMapping("/toUsercenter")
	public String toUsercenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("--------");
		javax.servlet.http.HttpSession session = req.getSession();
		String username= (String) session.getAttribute("username");
		System.out.println(username);
		String currentPage = req.getParameter("currentPage");
		currentPage=currentPage ==null ? "1" : currentPage;//ע������ķ�ҳ��̨ʵ��
		Fenye list=cardservice.list(username, Integer.parseInt(currentPage));
		System.out.println("\\\\\\"+list);
		req.setAttribute("fenye", list);
		req.setAttribute("currentPage", Integer.parseInt(currentPage));
		req.setAttribute("username", username);
		return "usercenter";
	}
	
	/*@RequestMapping("/toFlow")
	@ResponseBody
	public ajaxDAO toFlow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		String username= (String) session.getAttribute("username");
		System.out.println(username);
		String currentPage = req.getParameter("currentPage");
		currentPage=currentPage ==null ? "1" : currentPage;//ע������ķ�ҳ��̨ʵ��
		Fenye list=cardservice.list(username, Integer.parseInt(currentPage));
		System.out.println("\\\\\\"+list);
		return ajaxDAO.success(list);
	}*/
}
