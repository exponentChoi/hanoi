package yetop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AcessDB {

	Connection conn = null;	
	PreparedStatement pstm = null;	
	ResultSet rs = null;
	
	PreparedStatement pstmt = null;
	ResultSet st = null;
	Connection con= null;
	public AcessDB() {
		
	}
	String url = "jdbc:mysql://localhost:3306/hanoi?serverTimezone=UTC";
	public AcessDB(int count) {
	
	}
	
	public void insert_db( int second,long count,String name,int tower,String nick) {
		
		int chk = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "1234");

			String sql = "insert into score values(?, ?, ?,?,?);";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, second);
			pstm.setLong(2, count);
			pstm.setString(3, name);
			pstm.setInt(4,tower);
			pstm.setString(5, nick);
			
			
			chk = pstm.executeUpdate();
			
			if(chk != 0) {
				System.out.println("성공");
			}else {
				System.out.println("ㅇㄴ마ㅣ외");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
	}
	public void select_db(long count, String name) {
		int rank;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, "root", "1234");
			
			String sql="select min(count) from hanoi.score;";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			
			rs.next();
			System.out.print(rs);
			
			}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
