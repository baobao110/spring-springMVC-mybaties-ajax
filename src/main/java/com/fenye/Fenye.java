package com.fenye;

public class Fenye {

	public static final int move=3;//�涨ÿҳ����ʾ����
	private int totalNumber=0;//��¼������
	private int totalPage=1;//��ʾ����ҳ��
	private int currentNumber=0;//��ǰ��¼�ĺ���
	private int currentPage=1;//��ǰҳ
	private Object object;//��ǰҳ�����Ϣ
	public Fenye(int totalNumber, int currentPage) {
		this.totalNumber = totalNumber;
		this.currentPage = currentPage;
	}
	
	public int getcurrentNumber() {
		return (currentPage-1)*move;
	}
	
	public int getTotalPage() {
		return totalNumber% move==0 ? totalNumber/ move :(totalNumber/move +1);
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	
	
	
	
	
	

}
