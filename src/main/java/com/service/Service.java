package com.service;

import java.util.ArrayList;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.AccountFlow.Account;
import com.base.DataBase;
import com.blog.Blog;
import com.fenye.Fenye;
import com.inter.AccountDAO;
import com.inter.BlogDAO;



@Component	//������Spring��ע������ʱSpring���Զ�ɨ���ע���������,���������Control��ʹ�õ�
@Transactional	//�����ע��������spring��mybaties���ʱ���������,����ͬ��mybaties�е�sqlSession��connection,���ﲻ��Ҫ�ֶ��Ŀ����͹ر�����
public class Service {
	
	@Autowired
	private BlogDAO blogDao;
	
	@Autowired
	private AccountDAO accountDao;
	
	@Transactional(rollbackFor=Exception.class)
	public Blog open(String username,String password) {
		Blog blog=new Blog();
		blog.setNumber(DataBase.CreateNumber());
		blog.setPassword(DigestUtils.md5Hex(password));
		blog.setTotal(0);
		blog.setUsername(username);
		System.out.println(blog);
		if(blogDao.open(blog)==1) {//�������Card.xml���е�SQL���
			return blog;
			}
		else {
			return null;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void write(int number,String password,String title,String context)  {
			Blog blog=blogDao.GetBog(number);//������Ժ�֮ǰ��ʹ��mybaties�Ļ�ȡ������бȽϾͿ��Է�����spring+mybaties���ŵ�,�����Զ���������
			if(null==blog) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			if(!DigestUtils.md5Hex(password).equals(blog.getPassword())) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			System.out.println(blog.getTotal()+">>>>>>>");
			int total=blog.getTotal()+1;
			System.out.println("??????"+total);
			blogDao.add(number, total);
			System.out.println("'uuuuu"+blogDao.GetBog(number));
			Account account=new Account();
			account.setNumber(number);
			account.setContext(context);
			account.setTitle(title);
			account.setDescription("д��");
			accountDao.add(account);
			System.out.println("���Ͳ���");
		}
		
/*	@Transactional(rollbackFor=Exception.class)
	public void draw(int number,String password,double money) {
			Blog card=cardDao.GetCad(number);
			if(null==card) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			if(!DigestUtils.md5Hex(password).equals(card.getPassword())) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			if(money<0) {
				System.out.println("���С����");
				return;
			}
			double x=card.getMoney();
			if(money>x) {
				System.out.println("����");
				return;
			}
			x-=money;
			if(cardDao.modifyMoney(number, x)!=0) {
				System.out.println("ȡǮ�ɹ�");	
			}
			Account account=new Account();
			account.setNumber(number);
			account.setMoney(money);
			account.setType(1);
			account.setDescription("ȡǮ");
			accountDao.add(account);
			System.out.println("��ˮ�˲���");
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void transfer(int OutNumber,String password,double money,int InNumber) {
			Blog card1=cardDao.GetCad(OutNumber);
			if(null==card1) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			if(!DigestUtils.md5Hex(password).equals(card1.getPassword())) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			if(money<0) {
				System.out.println("���С����");
				return;
			}
			double x=card1.getMoney();
			if(money>x) {
				System.out.println("����");
				return;
			}
			x-=money;
			if(cardDao.modifyMoney(OutNumber, x)!=0) {
				System.out.println("ȡǮ�ɹ�");	
			}
			Account account1=new Account();
			account1.setNumber(OutNumber);
			account1.setMoney(money);
			account1.setType(1);
			account1.setDescription("ȡǮ");
			accountDao.add(account1);
			System.out.println("��ˮ�˲���");
			Blog card2=cardDao.GetCad(InNumber);
			if(null==card2) {
				System.out.println("�˺Ż������벻����");
				return;
			}
			double y=card2.getMoney();
			money+=y;
			if(cardDao.modifyMoney(InNumber, money)!=0) {
				System.out.println("ת��ɹ�");	
			}
			Account account2=new Account();
			account2.setNumber(InNumber);
			account2.setMoney(money);
			account2.setType(1);
			account2.setDescription("��Ǯ");
			accountDao.add(account2);
			System.out.println("��ˮ�˲���");
		}//����ע��cardDao��ʹ��
*/	
	@Transactional(rollbackFor=Exception.class)
	public Blog ChangePassword(int number,String oldPassword,String newPassword) {
			Blog a=blogDao.GetBog(number);
			if(null==a) {
				System.out.println("�˺Ż������벻����");
				return null;
			}
			if(!DigestUtils.md5Hex(oldPassword).equals(a.getPassword())) {
				System.out.println("�˺Ż������벻����");
				return null;
			}
			if(blogDao.modifyPassword(number, DigestUtils.md5Hex(newPassword))!=0) {
				a=blogDao.GetBog(number);
				return a;
			}
			else {
				return null;
			}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public Fenye List(int number, String password, int currentPage) {
		Blog blog=blogDao.GetBog(number);
		if(null==blog) {
			System.out.println("�˺Ż������벻����");
			return null;
		}
		if(!DigestUtils.md5Hex(password).equals(blog.getPassword())) {
			System.out.println("�˺Ż������벻����");
			return null;
		}
		int totalNumber=accountDao.totalNumber(number);//����totalNumber������ȡ�ܼ�¼��Ŀ
		Fenye fenye=new Fenye(totalNumber, currentPage);//��ʼ��������Ϊ�ܼ�¼�͵�ǰҳ��
		 ArrayList<Account>list=accountDao.List(number, fenye.getcurrentNumber(), fenye.move);//����List()������ȡ��number�ĸ�ҳ����
		 fenye.setObject(list);//����ȡ�ļ�¼����
		 if(null==fenye) {
			 return null;
		 }
		 else {
			 return fenye;
		 }
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int total(int number) {
		return accountDao.totalNumber(number);
	}
	
	public Blog GetBlog(int number,String password) {
			Blog a=blogDao.Get(number,password);
			System.out.println(a.getTotal()+"xxxxxx>>>>");
			if(null==a) {
				System.out.println("�˺Ż������벻����");
				return null;
			}
			else {
				return a;
			}
		}
	
	@Transactional(rollbackFor=Exception.class)
	public Blog Get(int number,String password) {
			Blog a=blogDao.Get(number,DigestUtils.md5Hex(password));
			return a;
		}
	
	@Transactional(rollbackFor=Exception.class)//���ô��������쳣
	public void delete(int number) {
			int i=blogDao.delete(number);
			System.out.println(i);
			if(i==0) {
				System.out.println("�˺Ż������벻����");
			}
	}
	
	public void deletefFlow(int number,String title) {
		accountDao.delete(number, title);
	}
}
/*
 * ʲô�Ǽ�����쳣,ʲô�ǷǼ�����쳣,��������ַ�����:
 * 1 ������쳣�̳���Exception �Ǽ�����쳣�̳���RuntimException����Error
 * 2 ������쳣���벶��,���ǷǼ�����쳣���Բ�����
 * /
 */