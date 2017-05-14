package com.jinwoo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainDataBase {

	public static void main(String[] args) {
		Connection conn = connect();
		
//		create("Lunch", "PIZZA");
//		readAll();
//		update(2, "ChangedTitle2","ChangedContents2");
//		read(2);
//		delete(3);
		readAll();
		
	}
	
	public static void delete(int bbsno){
		try(Connection conn = connect()){
			String sql = "delete from bbs1 where bbsno = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsno);
			pstmt.executeUpdate();
						
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void update(int bbsno, String title, String content){
		try(Connection conn = connect()){
			String sql = "update bbs1 set title = ?"
					+ " ,content = ? "
					+ " ,ndate = now()"
					+ " where bbsno = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,title);
			pstmt.setString(2,content);
			pstmt.setInt(3,bbsno);			
			pstmt.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			}		
	}
	
	public static void read(int bbsno){
		try(Connection conn = connect()){
			String sql = "select * from bbs1 where bbsno = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bbsno);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				int id = rs.getInt("bbsno");
				String title = rs.getString("title");
				String content = rs.getString("content");	
				String date = rs.getString("nDate");
				System.out.printf("One = bbsno:%d, title:%s, content:%s, date: %s\n", id, title, content, date);
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void readAll(){
		// try - with ���� ����� close ó��
		try(Connection conn = connect()){
			// 1. �����ۼ�
			String sql = "select * from bbs1;";
			// 2. �����ͺ��̽��� ���� ���� �Ѱ��� �������
			Statement stmt = conn.createStatement();
			// 3. ���� ���� �� ������� ������ ����
			ResultSet rs = stmt.executeQuery(sql);
			// 4. ������ ��� ������� �ݺ����� ���鼭 ȭ�鿡 ���
			while(rs.next()){
				int id = rs.getInt("bbsno");
				String title = rs.getString("title");
				String content = rs.getString("content");	
				String date = rs.getString("nDate");
				System.out.printf("Set = bbsno:%d, title:%s, content:%s, date: %s\n", id, title, content, date);
			}		
		} catch (Exception e){
			e.printStackTrace();
		} 
	}

	public static void create(String title, String content){
		try(Connection conn = connect()){
	
		String sql = "insert into bbs1(title, content, nDate) values(?,?,now());";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1,title);
		pstmt.setString(2,content);
		pstmt.execute();	
		
		} catch (Exception e){
			e.printStackTrace();
		} 

	}

	/*
	public static void create(String title, String content){
			// 1. try - with���� ����� close ó��
		try(Connection conn = connect()){
			// 2. ���� �ۼ�
			String sql = "insert into bbs1(title, content, nDate) values('"+ title + "','"+content +"',now());";
			// 3. �����ͺ��̽��� ���� ���� �Ѱ��� �������
			Statement stmt = conn.createStatement();
			// 4. execute(sql);		
			stmt.execute(sql);		
		}catch(Exception e){
			e.printStackTrace();
		}						
	}
	*/
	
	public static Connection connect(){
		try{
			String id = "root"; // mysql user id
			String pw = "god3927"; // mysql root ��й�ȣ
			String dbName = "test"; // db �̸�
			// db ���� �ּ� = �������� // ������:��Ʈ / db �̸�
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			// ����̹� Ŭ������ �������� load�Ѵ�.
			Class.forName("com.mysql.jdbc.Driver"); // ���� ���� ���� ����̹� ���̺귯���� ��Ÿ�ӽÿ� �޸𸮿� �ø���. 
			Connection conn = DriverManager.getConnection(url, id, pw); // conn = dbHelper
			System.out.println("Database" + dbName+"�� ����Ǿ����ϴ�.");			
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
