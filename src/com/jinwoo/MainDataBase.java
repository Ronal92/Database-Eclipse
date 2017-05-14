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
		// try - with 문을 사용한 close 처리
		try(Connection conn = connect()){
			// 1. 쿼리작성
			String sql = "select * from bbs1;";
			// 2. 데이터베이스에 쓰기 위한 한개의 실행단위
			Statement stmt = conn.createStatement();
			// 3. 쿼리 실행 후 결과셋을 변수에 전달
			ResultSet rs = stmt.executeQuery(sql);
			// 4. 변수에 담긴 결과셋을 반복문을 돌면서 화면에 출력
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
			// 1. try - with문을 사용한 close 처리
		try(Connection conn = connect()){
			// 2. 쿼리 작성
			String sql = "insert into bbs1(title, content, nDate) values('"+ title + "','"+content +"',now());";
			// 3. 데이터베이스에 쓰기 위한 한개의 실행단위
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
			String pw = "god3927"; // mysql root 비밀번호
			String dbName = "test"; // db 이름
			// db 연결 주소 = 프로토콜 // 아이피:포트 / db 이름
			String url = "jdbc:mysql://localhost:3306/" + dbName;
			// 드라이버 클래스를 동적으로 load한다.
			Class.forName("com.mysql.jdbc.Driver"); // 서버 쪽을 위한 드라이버 라이브러리를 런타임시에 메모리에 올린다. 
			Connection conn = DriverManager.getConnection(url, id, pw); // conn = dbHelper
			System.out.println("Database" + dbName+"에 연결되었습니다.");			
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
}
