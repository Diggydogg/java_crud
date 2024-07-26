package com.mycom.hotel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class HotelCRUD implements ICRUD{
	ArrayList<Hotel> list;
	Scanner s;
	final String fname ="rooms.txt";
	public int cnt= 0;
	Connection conn;
	final String ITEM_SELECT_ALL = "select * from rooms";
	final String ITEM_SELECT = "select * from rooms where room_type like ? ";
	final String ITEM_SELECT_NO = "select * from rooms where room_no = ? ";
	final String ITEM_INSERT = "insert into rooms (room_no, room_capacity, room_type, rate, desc, regdate)"
			+ "values (?,?,?,?,?,?) ";
	final String ITEM_UPDATE = "update rooms set room_capacity = ?, room_type = ? ,rate =?, desc =? where room_no=?";
	final String ITEM_DELETE = "delete from rooms where room_no = ?";
	

	/*
	  => room type , capacity, rate($ per night) (ex: Dirux 4 300)  : 
	  Additional Description : 
	  new item added in the list
	 */
	
	HotelCRUD(Scanner s){
		list = new ArrayList<>();
		this.s = s;
		conn = DBConnection.getConnection();
	}
	
	public void loadData(String keyword) {
		list.clear();
		//String selectall = "select * from rooms";
		try {
			//Statement stmt = conn.createStatement();
			
			PreparedStatement stmt;
			ResultSet rs;
			
			if(keyword.equals("")) {
				stmt = conn.prepareStatement(ITEM_SELECT_ALL);
				rs = stmt.executeQuery();
			}else {
				stmt = conn.prepareStatement(ITEM_SELECT);
				stmt.setString(1, "%"+keyword+"%");
				rs = stmt.executeQuery();
			}
			
			while(true) {
				if(!rs.next())break;
				int room_no = rs.getInt("room_no");
				String room_type = rs.getString("room_type");
				int room_capacity = rs.getInt("room_capacity");
				int rate = rs.getInt("rate");
				String desc = rs.getString("desc");
				list.add(new Hotel(room_no, room_type, room_capacity,rate, desc));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void loadData(int keyword) {
		list.clear();
		//String selectall = "select * from rooms";
		try {
			//Statement stmt = conn.createStatement();
			
			PreparedStatement stmt;
			ResultSet rs;
			
			
			stmt = conn.prepareStatement(ITEM_SELECT_NO);
			stmt.setInt(1,keyword);
			rs = stmt.executeQuery();
			
			
			while(true) {
				if(!rs.next())break;
				int room_no = rs.getInt("room_no");
				String room_type = rs.getString("room_type");
				int room_capacity = rs.getInt("room_capacity");
				int rate = rs.getInt("rate");
				String desc = rs.getString("desc");
				list.add(new Hotel(room_no, room_type, room_capacity,rate, desc));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	@Override
	public Object add() {
		int id = cnt;
		System.out.print(" => Room no , room type , capacity, rate($ per night) (ex: 217 Deluxe 4 300)  : ");
		int room_no =s.nextInt();
		String room_type = s.next();
		int capacity =s.nextInt();
		int rate = s.nextInt();
		System.out.print("Additional Description : ");
		s.nextLine();
		String desc = s.nextLine();
		return new Hotel(room_no,room_type, capacity,rate, desc  );
		
	}
	*/

	
	public String getCurrentDate() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return now.format(f);
	}
	
	
	
	
	@Override
	public int add(Hotel one) {
		
		PreparedStatement pstmt;
		int retval = 0;
		try {
			pstmt = conn.prepareStatement(ITEM_INSERT);
			pstmt.setInt(1, one.getRoom_no());
			pstmt.setInt(2, one.getRoom_capacity());
			pstmt.setString(3, one.getRoom_type());
			pstmt.setInt(4, one.getRate());
			pstmt.setString(5, one.getDesc());
			pstmt.setString(6, getCurrentDate());
			retval = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return retval;
	}

	public void addItem() {
		int id = cnt;
		System.out.print(" => Room no , room type , capacity, rate($ per night) (ex: 217 Deluxe 4 300)  : ");
		int room_no =s.nextInt();
		String room_type = s.next();
		int capacity =s.nextInt();
		int rate = s.nextInt();
		System.out.print("Additional Description : ");
		s.nextLine();
		String desc = s.nextLine();
		Hotel one = new Hotel(room_no,room_type, capacity,rate, desc  );
		int retval = add(one);
		//list.add(one);
		if(retval > 0)System.out.println("new item added in the list succesfully");
		else System.out.println("Process Fail");
		
		
	}
	/*
	@Override
	public int update(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Object obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void selectOne(int id) {
		// TODO Auto-generated method stub
		
	}
	*/
	/*
	 ----------------------------------------------------------------------
	 Room no  | Occupancy |  Room Type | Rate ($ per night) | Description 
	 ----------------------------------------------------------------------
	 id  | room_capacity |RoomType   | rate | desc
	 */
	
	public void listAll(String keyword) {
		loadData(keyword);
		System.out.println("-------------------------------------------------------------------------\n"
				+ "ID |Room no  | Occupancy |  Room Type | Rate ($ per night) | Description \n"
				+ "-------------------------------------------------------------------------");
		for(int  i = 0; i < list.size();i++) {
			String str = String.format("%3s",i+1);
			System.out.print(str);
			System.out.println(list.get(i).toString());
			
		}
		System.out.println("-------------------------------------------------------------------------");
	}
	
	/*
	public ArrayList<Integer> listAll(String keyword) {
		ArrayList<Integer> idlist = new ArrayList<>();
		int j = 0;
		System.out.println("-------------------------------------------------------------------------\n"
				+ " ID |Room no  | Occupancy |  Room Type | Rate ($ per night) | Description \n"
				+ "-------------------------------------------------------------------------");
		for(int  i = 0; i < list.size();i++) {
			String type = list.get(i).getRoom_type();
			if(!type.contains(keyword)) continue;
			
			String str = String.format("%3s",i+1);
			System.out.print(str);
			System.out.println(list.get(i).toString());
			idlist.add(i);
			j++;
		}
		System.out.println("-------------------------------------------------------------------------");
		return idlist;
	}
	*/
	
	public void listAll(int keyword) {
		loadData(keyword);
		System.out.println("-------------------------------------------------------------------------\n"
				+ " ID |Room no  | Occupancy |  Room Type | Rate ($ per night) | Description \n"
				+ "-------------------------------------------------------------------------");
		
		for(int  i = 0; i < list.size();i++) {
			
			//int room_no = list.get(i).getRoom_no();
			//if(room_no!=keyword) continue;
			
			String str = String.format("%3s",i+1);
			System.out.print(str);
			System.out.println(list.get(i).toString());
			
		}
		System.out.println("-------------------------------------------------------------------------");
	}
	
	public void SearchType() {
		System.out.println("=> Search Item (Room Type): ");
		String keyword = s.next();
		//ArrayList<Integer> idlist = this.listAll(keyword);
		listAll(keyword);
	}
	
	public void SearchNo() {
		System.out.println("=> Search Item (Room No): ");
		int keyword = s.nextInt();
		//ArrayList<Integer> idlist = this.listAll(keyword);
		listAll(keyword);
	}
	
	
	@Override
	public int update(Hotel one) {
		PreparedStatement pstmt;
		int retval = 0;
		try {
			pstmt = conn.prepareStatement(ITEM_UPDATE);
			
			pstmt.setInt(1, one.getRoom_capacity());
			pstmt.setString(2, one.getRoom_type());
			pstmt.setInt(3, one.getRate());
			pstmt.setString(4, one.getDesc());
			//pstmt.setString(5, getCurrentDate());
			pstmt.setInt(5, one.getRoom_no());
			retval = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retval;
	}
		
	
	public void updateItem() {
		System.out.println("=> Search Item to Update(Room Type): ");
		String keyword = s.next();
		listAll(keyword);
		//ArrayList<Integer> idlist = this.listAll(keyword);
		System.out.print("=> Choose id to update (0 for cancel): ");
		int id = s.nextInt();
		if(id==0) {
			System.out.println("Update process canceled");
			return;
		}
		System.out.print("Update new room capacity : ");
		int capacity = s.nextInt();
		System.out.print("Update new Rate ($ per night) : ");
		int rate = s.nextInt();
		System.out.println("Update new Description : ");
		s.nextLine();
		String desc = s.nextLine();
		//Hotel hotel = list.get(idlist.get(id-1));
		//hotel.setRoom_capacity(capacity);
		//hotel.setRate(rate);
		//hotel.setDesc(desc);
		int val = this.update(new Hotel(list.get(id-1).getRoom_no(),"",capacity,rate,desc));
		if(val>0)System.out.println("Item Updated succesfully!");
		else System.out.println("Update process fail ");
		//System.out.println("Item Updated! ");
	}
	
	
	
	@Override
	public int delete(int keyword) {
		PreparedStatement pstmt;
		int retval = 0;
		try {
			pstmt = conn.prepareStatement(ITEM_DELETE);
			pstmt.setInt(1, keyword);
			retval = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retval;
	}

	
	public void deleteItem() {
		System.out.println("=> Search Item to delete (Room Type): ");
		String keyword = s.next();
		//ArrayList<Integer> idlist = this.listAll(keyword);\
		listAll(keyword);
		System.out.print("=> Choose id of item to delete : ");
		int id = s.nextInt();
		s.nextLine();
		
		System.out.print("Are sure of delete this item? (Y/N) : ");
		String ans = s.next();
		if(ans.equalsIgnoreCase("y")) {
			//list.remove((int)idlist.get(id-1));
			//System.out.println("Item has deleted");
			int room_no = list.get(id-1).getRoom_no();
			int val = delete(room_no);
			if(val>0) System.out.println("Item deleted successfully!");
			else System.out.println("Delete process has failed");
		}else {
			System.out.println("Process canceled");
		}
		
	}
	/*
	 * ID |Room no  | Occupancy |  Room Type | Rate ($ per night) | Description 
	 */
	/*
	public void loadFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line;
			int count = 0;
			while(true) {
				line = br.readLine();
				if(line == null) break;
				
				String data[] = line.split("\\|");
				int room_no = Integer.parseInt(data[0]);
				int capacity = Integer.parseInt(data[1]);
				String room_type = data[2];
				int rate = Integer.parseInt(data[3]);
				String desc = data[4];
				list.add(new Hotel(room_no, room_type, capacity, rate, desc ) );
				count++;
			}
			br.close();
			System.out.println("===>"+ count +"items loaded!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
*/
	public void saveFile() {
		try {
			PrintWriter pr = new PrintWriter(new FileWriter("test.txt"));
			for(Hotel one: list) {
				pr.write(one.toFileString()+"\n");
			}
			pr.close();
			System.out.println("==> Data Saved succesfully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	
	

}
