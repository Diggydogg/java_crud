package com.mycom.hotel;

import java.util.Scanner;

public class HotelManager {
	Scanner s = new Scanner(System.in);
	HotelCRUD hotelCRUD;
	/*
	 **** Hotel reservation option ****
	 
	 		1. View all rooms
	 		2. View rooms in type
	 		3. Search rooms by id
	 		4. Add room 
	 		5. Update room
	 		6. Delete room
	 		7. Save File
	 		0. Exit
	 		
	 **********************************
	 => Choose opt : 
	 * 
	 */
	HotelManager(){
		hotelCRUD = new HotelCRUD(s);
	}
	
	public int selectMenu() {
		System.out.print("**** Hotel reservation option ****\n"
				+ "\n"
				+ "	1. View all rooms\n"
				+ "	2. View rooms in type\n"
				+ "	3. Search rooms by room No\n"
				+ "	4. Add room \n"
				+ "	5. Update room\n"
				+ "	6. Delete room\n"
				+ "	7. Save File\n"
				+ "	0. Exit\n"
				+ "		\n"
				+ "**********************************\n"
				+ "=> Choose opt : ");
		return s.nextInt();
	}
	public void start() {
		//hotelCRUD.loadFile();
		while(true) {
			int menu = selectMenu();
			if(menu  ==0)return;
			if(menu == 4) {//create
				hotelCRUD.addItem();
			}
			else if(menu==1) {
				//view
				hotelCRUD.listAll("");
			}else if(menu==2) {
				hotelCRUD.SearchType();
			}
			else if(menu==3) {
				hotelCRUD.SearchNo();
			}
			else if(menu==5) {
				hotelCRUD.updateItem();
			}
			else if(menu==6) {
				//Delete
				hotelCRUD.deleteItem();
			}
			else if(menu==7) {
				hotelCRUD.saveFile();
			}
			
		}
		
	}
}
