package com.base;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DataBase {
	/*private static SqlSessionFactory ssf;
	static {
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream("config.xml");
			ssf = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static SqlSession open(boolean flag) {
		return ssf.openSession(flag);
		
		 * ������Ҫ���ý�SqlSession����Ϊtrue,�����Զ��ύ���������Ϊtrue������Ĭ��Ϊfalse,
		 * �����������Ҫ�Լ��ֶ����ύ�����Ҫ���session.commit(),��������ڿ�ʼ������Ϊtrue
		 * ���JDBC�еĲ�һ������JDBC��Ĭ��Ϊ�Զ�
		 
	}*/
	//�����Ѿ�����Ҫ��ʹ�õ�mybaties�� SqlSessionFactory
	
	public static int CreateNumber() {
		String a="";
		Random b=new Random();
		a+=(b.nextInt(9)+1);
		for(int i=0;i<5;i++) {
			Random c=new Random();
			a+=c.nextInt(10);
		}
		return Integer.parseInt(a);
	}
}
