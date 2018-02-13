package com.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Hand {

	private static ApplicationContext context;
	
	public static void init() {
		context = new ClassPathXmlApplicationContext(new String[] {"classpath*:mapper/spring/config.xml"});//��������SpringΨһ��Ҫע��ľ���spring��xml�ļ���·��
	}
	
	public static Object getController(String value) {
		System.out.println(value);
		
		return context.getBean(value);
	}

}
