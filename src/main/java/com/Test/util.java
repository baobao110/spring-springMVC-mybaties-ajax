package com.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.Request;

import com.handler.Hand;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class util extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
//		// TODO Auto-generated method stub
//		Handler.init(config.getServletContext().getRealPath("/"));
//		/*
//		 * �����ServletConfig config ����������
//		 * config.getServletContext().getRealPath("/")����Ŀ�ĸ�·����app
//		 * config.getInitParameter("pack")��ȡweb.xml�е�init paramater��"pack"��Ӧ��ֵ
//		 */
		System.out.println("Servlet init");
		/*
		 * ������Ҫ�ر�ע��,��Ҫ���ȶ�Spring����г�ʼ����ʼ���Ǵ�����ʼ��Spring ����,
		 * ��Ȼ�ͻ�����Լ���ʼ�Ŀ�ָ���쳣,�������Լ�ûע��,�Ժ��޸Ĵ������������ʼ
		 */
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String msg=req.getRequestURI();//��ȡurl��ַ������ķ����ǳ�����
		System.out.println(msg);
		if(msg==null) {
			req.getRequestDispatcher("/WEB-INF/pag/failure.jsp");
		}
		String[]result=msg.split("\\/");
		msg=result[result.length-1];
		System.out.println(msg);
		String parent=result[result.length-2];
		System.out.println(parent);
		if(parent==null) {
			req.getRequestDispatcher("/WEB-INF/pag/failure.jsp");
		}
		result=msg.split("\\.");//����Ҫ�ر�ע��ָ�ķ���
		String method=result[0];
		System.out.println(method);
		if(method==null) {
			req.getRequestDispatcher("/WEB-INF/pag/failure.jsp");
		}
		Object object=Hand.getController(parent);
		System.out.println("<<<<"+object);
		try {
			System.out.println(object.getClass());
			Method action=object.getClass().getMethod(method,HttpServletRequest.class,HttpServletResponse.class );//����鿴API��Class��Method
			System.out.println(">>>>>"+action);
			String url=(String)action.invoke(object,req,resp);
			if(url!=null) {
				url="/WEB-INF/pag/"+url+".jsp";
				System.out.println("333333"+url);
				System.out.println(req.getAttribute("fenye"));
				req.getRequestDispatcher(url).forward(req, resp);
			}
			else {
				req.getRequestDispatcher("/WEB-INF/pag/failure.jsp").forward(req, resp);;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
	
