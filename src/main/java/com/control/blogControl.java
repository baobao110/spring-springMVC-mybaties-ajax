package com.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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

import com.AccountFlow.Account;
import com.ajax.ajaxDAO;
import com.blog.Blog;
import com.fenye.Fenye;
import com.service.BlogService;
import com.service.Service;
import com.user.User;


@Controller	//�����ע��д���̶�,SpringMVC���Զ�����ע���ҵ�Control��
@RequestMapping("/blog")//����ͱȽ�����˼,SpringMVC�����ע���ǻ�ȡǰ�˵�url��ַƬ,����url��ַ�ҵ���Ӧ����ͷ���������Ӧ�Ĳ���
public class blogControl extends control{
	
	@Autowired	//���ע�������Զ��ҵ���ص���,�����Զ��ĳ�ʼ��,�����Ͳ���Ҫ��set�������г�ʼ��,������Խ��Service���@Component
	private Service service;
	
	@Autowired
	private BlogService blogservice;
	
	@RequestMapping("/down")
	public void down(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// TODO Auto-generated method stub
		javax.servlet.http.HttpSession session = req.getSession(); 
		User user=(User) session.getAttribute("user");
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		Blog cad=service.Get(number, password);
		String filename = number + ".csv";
		resp.setContentType("application/octet-stream");  
		resp.setHeader("Content-Disposition", "attachment;filename="+ filename);  
		
		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream()))) {
			int currentPage = 1;
			String header = "����,����,����,��ע,ʱ��";
			bw.write(header);
			bw.newLine();
			bw.flush();
			while (true) {
				Fenye list = service.List(number, password, currentPage);
				System.out.println(currentPage);
				 ArrayList<Account>account=(ArrayList<Account>) list.getObject();
				if(null==account) {
					break;
				}
				if(account.isEmpty()) {//�����ر�ע�ⲻȻ�����������
					break;
				}
				for(Account i:account) {
					StringBuilder text = new StringBuilder();
					text.append(i.getNumber()).append(",")
					.append(i.getTitle()).append(",")
					.append(i.getContext()).append(",")
					.append(i.getDescription()).append(",")
					.append(i.getCreatetime()).append(",");
					bw.write(text.toString());
					bw.newLine();
					bw.flush();
					}
				System.out.println(111);
				currentPage++;
			}
		}
	}

	
	@RequestMapping("/toDelete")
	public String toDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "delete";
	}
	
	@RequestMapping("/delete")
	public String  delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		User user=(User)session.getAttribute("user");
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		Blog blog=service.Get(number,password);
		if(blog==null) {
			return "failure";
		}
		else {
			service.delete(number);
			session.setAttribute("user",user);
			return toUsercenter(req, resp);
		}
	}
	
	@RequestMapping("/openAccount")
	public String openAccount(HttpServletRequest req, HttpServletResponse resp,HttpSession session) throws ServletException, IOException {
		User user=(User) session.getAttribute("user");
		String username=user.getUsername();
		String password=req.getParameter("password");
		Blog blog=service.open(username,password);
		req.setAttribute("blog", blog);
		req.setAttribute("password", password);//������ͨ����ֵ�Ե���ʽ�洢��ص���ֵ,����Attribute������set����Ҳ��get����������Ժ�Parameter��������
		return "success";
	}
	/*
	 * ע������Ĳ���session,��ǰ�����ڷ�������request��ȡ����������Spring-MVC�ͼ���,�����ڲ�����ֱ�Ӵ���
	 */
	
	@RequestMapping("/toOpenAccount")
	public String toOpenAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		javax.servlet.http.HttpSession session = req.getSession();
		return "OpenAccount";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest req, HttpServletResponse resp,String number,String title,String password,String context) throws ServletException, IOException {
		String a=req.getParameter("number");
		int num=Integer.parseInt(number);
		Blog bog=service.Get(num,password);
		System.out.println(bog);
		if(bog==null) {
			return "failure";
		}
		else {
			service.write(num, password, title, context);
			Blog blog=service.Get(num, password);
			System.out.println("//////"+blog.getTotal());
			req.setAttribute("blog", blog);
			return "success2";
		}
	}
	
	@RequestMapping("/toWrite")
	public String  toWrite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "write";
	}
	
	/*@RequestMapping("/transfer")
	public String transfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		String b=req.getParameter("money");
		double money=Double.parseDouble(b);
		Blog cad=service.Get(number,password);
		if(cad==null) {
			return "failure";
		}
		String c=req.getParameter("InNumber");
		int InNumber=Integer.parseInt(c);
		service.transfer(number, password, money, InNumber);
		Blog card1=service.GetCard(number);
		Blog card2=service.GetCard(InNumber);
		if((card1==null)||(card2==null)) {
			return "failure";
		}
		else {
			req.setAttribute("card1", card1);
			req.setAttribute("card2", card2);
			return "success2";
		}
	}
	
	@RequestMapping("/toTransfer")
	public String toTransfer(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "transfer";
	}
	
	@RequestMapping("/check")
	public String  check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String password=req.getParameter("password");
		String b=req.getParameter("money");
		double money=Double.parseDouble(b);
		Blog cad=service.Get(number,password);
		if(cad==null) {
			return "failure";
		}
		service.draw(number, password, money);
		Blog card=service.GetCard(number);
		if(card==null) {
			return "failure";
		}
		else {
			req.setAttribute("card", card);
			return "success";
		}
	}
	
	@RequestMapping("/toCheck")
	public String toCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "check";
	}*/
	
	@RequestMapping("/list")
	public String list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			String currentPage = req.getParameter("currentPage");
			System.out.println(currentPage);
			String a=req.getParameter("number");
			int number=Integer.parseInt(a);
			System.out.println("number"+number);
			String password=req.getParameter("password");
			System.out.println("password"+password);
			Blog cad=service.Get(number,password);
			System.out.println(">>>>>>>>1"+cad);
			if(null==cad) {
				return "failure";
			}
			else {
				currentPage=currentPage ==null ? "1" : currentPage;//ע������ķ�ҳ��̨ʵ��
				Fenye list = service.List(number, password, Integer.parseInt(currentPage));//��ȡ��¼
				System.out.println("22222"+list);
				req.setAttribute("fenye", list);//�����¼
				req.setAttribute("number", number);
				req.setAttribute("password", password);//����֮���Ա���number��password��Ϊ��ǰ��ҳ����ʾ������
				req.setAttribute("currentPage", currentPage);
				return "list";
			}
		}
	
	@RequestMapping("/toList")
	public String toList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "list";
	
	}
	
	@RequestMapping("/toChangePassword")
	public String toChangePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		req.setAttribute("number", number);
		return "password";
	}
	
	@RequestMapping("/password")
	public String  password(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a=req.getParameter("number");
		int number=Integer.parseInt(a);
		String oldPassword= req.getParameter("oldPassword");
		Blog bog=service.Get(number,oldPassword);
		System.out.println("ererer"+bog);
		if(bog==null) {
			return "failure";
		}
		else {
			String newPassword= req.getParameter("newPassword");
			Blog blog=service.ChangePassword(number, oldPassword, newPassword);
			req.setAttribute("blog", blog);
			req.setAttribute("password", newPassword);
			return "success";
		}
	}
	
	@RequestMapping("/toUsercenter")
	public String toUsercenter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("--------");
		javax.servlet.http.HttpSession session = req.getSession();
		String username= (String) session.getAttribute("username");
		System.out.println(username);
		String currentPage = req.getParameter("currentPage");
		currentPage=currentPage ==null ? "1" : currentPage;//ע������ķ�ҳ��̨ʵ��
		Fenye list=blogservice.list(username, Integer.parseInt(currentPage));
		System.out.println("\\\\\\"+list);
		req.setAttribute("fenye", list);
		req.setAttribute("currentPage", Integer.parseInt(currentPage));
		req.setAttribute("username", username);
		return "usercenter";
	}
	
//	@RequestMapping("/toInformation")
//	public String toInformation	(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
//		String a=req.getParameter("number");
//		int number=Integer.parseInt(a);
//		req.setAttribute("number", number);
//		return "information";
//	}
//	
	@RequestMapping("/information")
	public String information(HttpServletRequest req,String Title,String password)throws ServletException, IOException {
		Account account=blogservice.get(Title);
		System.out.println(account);
		req.setAttribute("account", account);
		req.setAttribute("password",password);
		return "information";
	}
	
	@RequestMapping("/deleteflow")
	public String deleteflow(HttpServletRequest req)throws ServletException, IOException {
		String title=(String) req.getParameter("Title");
		System.out.println("title"+title);
		String a=req.getParameter("number");
		System.out.println("aaa"+a);
		int number=Integer.parseInt(a);
		System.out.println("number"+number);
		service.deletefFlow(number, title);
		req.setAttribute("number", number);
		return "success3";
		
		
	}
	/*
	 * ע������ķ�������,���е�number��password�����ǰ�˵�nameһ��,
	 * �����ǰ�˵�Ҫȡ����������ͬ,������Ϊ���Ǵ�ǰ�˻�ȡ�����Ա��붼����ΪString����
	 * ������Խ��information��jspҳ�濴�Ͷ���,��Ҳ��Spring-MVC���÷���ɫ,��ȡǰ�˵�����
	 * ����Ҫ��request��ȡ,����ֱ�ӻ�ȡ���ǲ��������ǰ�˵�name��ͬ
	 */
}
