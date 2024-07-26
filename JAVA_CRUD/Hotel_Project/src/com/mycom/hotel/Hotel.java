package com.mycom.hotel;

public class Hotel {
	private int room_no;
	private int room_capacity;
	private String room_type;
	private int rate;
	private String img;
	private String desc;
	private String[] free_date;
	
	Hotel(){}
	Hotel(int room_no, String room_type, int room_capacity, int rate,String desc){
		this.desc = desc;
		this.room_no = room_no;
		this.room_type = room_type;
		this.room_capacity = room_capacity;
		//this.img = img;
		this.rate = rate;
	}
	
	Hotel(int room_no, String room_type, int room_capacity, int rate, String img, String desc){
		this.desc = desc;
		this.room_no = room_no;
		this.room_type = room_type;
		this.room_capacity = room_capacity;
		this.img = img;
		this.rate = rate;
	}
	
	public int getRoom_no() {
		return room_no;
	}
	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}
	public String getRoom_type() {
		return room_type;
	}
	public void setRoom_type(String room_type) {
		this.room_type = room_type;
	}
	public int getRoom_capacity() {
		return room_capacity;
	}
	public void setRoom_capacity(int room_capacity) {
		this.room_capacity = room_capacity;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String[] getFree_date() {
		return free_date;
	}
	public void setFree_date(String[] free_date) {
		this.free_date = free_date;
	}
	/*
	 -------------------------------------------------------------------------
	 ID |Room no  | Occupancy |  Room Type | Rate ($ per night) | Description 
	 -------------------------------------------------------------------------
	 id  | room_capacity |RoomType   | rate | desc
	 */
	
	@Override
	public String toString() {
		String str = "|"+String.format("%9s", room_no)+ "|"+ String.format("%10s", room_capacity)+"|"+String.format("%12s", room_type)+"|"+ String.format("%20s",rate)+"|" +
		desc;
		
		return str;
	}
	
	public String toFileString() {
		return this.room_no+"|"+this.room_capacity+"|"+this.room_type+"|"+this.rate+"|"+this.desc;
	}
}
