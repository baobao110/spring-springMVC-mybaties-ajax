package com.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.user.User;


public class check implements Filter {

	public check() {
		// TODO Auto-generated constructor stub
		System.out.println("-----Filter init------ ");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("-----Filter end------ ");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;//����ע��ת��,��ҪתΪHttpServlet
		HttpServletResponse response=(HttpServletResponse)resp;
		String uri=request.getRequestURI();//�������Ȼ�ȡǰ�˵�URL��ַ
		System.out.println("<<<<<<<"+uri);
		Boolean flag=white(uri,request);//ͨ��white()�����ж��ǲ����ڰ�����֮��
		if(flag) {
			chain.doFilter(req, resp);//������ڰ�����֮�ھ�ֱ��ͨ����������,���������汣�����ע�����ת��¼ҳ�������
		}
		else {
			HttpSession session=request.getSession();
			/*
			 * ����������Ӳ��ǵ�¼ҳ����ת��ע�������,��ô���Ǿ����Ѿ���ͼ��������ҳ�����û�ҳ��,���Ǿ���Ҫ�ж����Ƿ��Ѿ���¼��
			 * ����ж��Ѿ���¼,��ʱ����Ҫһ��ȫ�ֱ���,������֮ǰ��̨ÿ�ε�¼�󶼻ᱣ��һ��"user"ȫ�ֱ���,����������ó�,���֮ǰ��¼��Session
			 * ��һ��������һ��"user"ȫ�ֱ���,��������ж�"user"�Ƿ�Ϊnull����ж��Ƿ��Ѿ���¼
			 */
			User user=(User)session.getAttribute("user");
			System.out.println("-------"+user);
			if(null==user) {
				response.sendRedirect("/user/toLogin.do");//null˵��û�е�¼��Ҫ��ת����¼ҳ��,����ע���������ӵ�ַ
				/*
				 * response.sendRedirect("/WEB-INF/pag/login.jsp") 
				 * Ϊʲôǰһ�ַ������Ե���,���Ǻ�һ�ַ����޷�����,������Դ�web.xml���ļ�ִ��˳���ж�
				 * ����web.xml�ļ���ִ��filter��ִ��servlet,��������������filter�����֮��
				 * ����servlet,��ΪServlet���ж���������.do��β��,������web.xml�ļ���Servlet���޷�ͨ��
				 * �����޷������¼ҳ��,���������ֳ�������һ������,Ϊʲô��̨��ҳ����ת,ͨ���ļ���·���Ϳ���ֱ����ת
				 * ���ǲ�ʹ��.do��������ʽ,������Ϊ�Ӻ�̨��ǰ�˽���ҳ����תʱ����Ҫ����web.xml�ļ�,����������ֱ��ͨ��·��
				 * ����ҳ����ת,���������̨����ҳ����תʱͨ��.do�����ӽ�����תͬ��������,��ʵ�����������ǰ�˵�ҳ����ת����
				 * ��ʵҲ��ͨ��Servlet���˽�����̨���ʵ�������ҳ����ת,����������Ϊ�ڲ�������Servlet֮ǰ������
				 * ��ʽ�����Զ���ķ�ʽ,��󶼽���Servlet���й���,������̨�������ҳ����ת,�ں�̨����ʵ�ʵ�ҳ����תʱ
				 * ������·���ķ�ʽ���,���Կ������Ϊ��Servlet֮ǰ���Ӷ������Զ���ķ�ʽ,Servlet֮�����Ӷ�����·���ķ�ʽ
				 * ��������Ϳ������Ϊʲô��Filter�������Զ���ķ�ʽ,���ﲻҪ������web��xml��ִ��˳��Filter��Servlet
				 * ֮ǰִ��,Filter�����ǰ�˺ͺ�̨֮������ݹ���
				 */
			}
			else {
				chain.doFilter(req, resp);
			}
		}
	}
	
	private Boolean white(String uri,HttpServletRequest req) {
		Boolean flag=false;
		InputStream input=req.getServletContext().getResourceAsStream("/WEB-INF/classes/white.properties");
		/*
		 * �����getServletContext()�����ǻ�ȡĬ�ϵĸ�·��,��ȡ������
		 */
		Properties pro=new Properties();
		try {
			pro.load(input);
			String msg=pro.getProperty("white_uri");//��������ʵ���ǻ�ȡproperties��Ϣ,�����ǹ̶�����·
			String[] message=msg.split(",");
			for(String i:message) {
				System.out.println(i);
				if(i.equals(uri)) {
					flag=true;
					System.out.println(111);
					break;
				}
				System.out.println(2222);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	private  void docheck(HttpServletRequest req) {
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	/*
	 * ������Filter��������Ϊ�˽����ҳ�洮������,�������ֻҪ֪��url���ӾͿ��Է����κε�ҳ��,��ʹû�е�¼�������,
	 * ������������ֻҪû�е�¼�Ͳ����Խ����û���ҳ��,���о��Ƿ������رպ�ˢ�������Զ���ת����¼ҳ��,���û��ٴε�¼,�����û���Ϣ
	 */
	
}
