package com.mycom.hotel;

public interface ICRUD {
	//public Object add();
	//public int update(Object obj);
	//public int delete(Object obj);
	//public void selectOne(int id);
	
	public int add(Hotel one);
	public int update(Hotel one);
	public int delete(int keyword);
	
}
