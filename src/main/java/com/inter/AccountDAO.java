package com.inter;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;

import com.AccountFlow.Account;

public interface AccountDAO {
	public int add(Account a);//��Ӽ�¼
	public ArrayList<Account> List(@Param("number")int number,@Param("currentNumber")int currentNumber,@Param("move")int move);
	/*
	 * ��������Ƿ��ط�ҳ�и�ҳ�����м�¼��number�ǲ�ѯ�Ŀ���,currentNumber��ָ��ǰҳ����ʼҳ������¼,moveָ��ҳ��ʾҳ�������
	 */
	public ArrayList<Account> list(@Param("number")int number,@Param("currentNumber")int currentNumber,@Param("move")int move);
	public Account get(String title);
	
	public int totalNumber(int number);//��ȡ���еļ�¼����
	
	public int delete(@Param("number")int number,@Param("title") String title);
}
