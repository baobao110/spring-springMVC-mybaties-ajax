package com.inter;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.blog.Blog;

public interface BlogDAO {
	public Blog Get(@Param("number")int number,@Param("password")String password);
	/*
	 * �����DAO�����ǰJDBCʱ��һ����������Ϊ�ӿڣ�ֻ��Ҫ���巽���Ϳ���,ͬʱ��Ҫע�������@Param()�����Ǹ�������ע��
	 */
	public int modifyPassword(@Param("number")int number,@Param("newPassword")String newPassword);//�������Ҫ�����
	//public int modifyMoney(@Param("number")int number,@Param("money")double money);
	public int open(Blog blog);
	
	public ArrayList<Blog> List(@Param("number")int number,@Param("currentNumber")int currentNumber,@Param("move")int move);
	public ArrayList<Blog> list(@Param("username")String usernamer,@Param("currentNumber")int currentNumber,@Param("move")int move);
	public int add(@Param("number")int number,@Param("total") int total);
	public int totalNumber(int number);
	public int total(String username);
	public Blog GetBog(int number);
	public Blog GetBlog(@Param("number")int number,@Param("password")String password);
	public int  delete(int number);
}
