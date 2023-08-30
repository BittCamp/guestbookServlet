package guestbook.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import guestbook.bean.GuestbookDTO;

public class GuestbookDAO {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "1234";
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public GuestbookDAO() {
		try {
			Class.forName(driver);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(GuestbookDTO guestbookDTO) {
		int su =0;
		String sql = "INSERT INTO GUESTBOOK VALUES(SEQ_GUESTBOOK.NEXTVAL,?,?,?,?,?,SYSDATE)";
		getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1,guestbookDTO.getName()); 
			pstmt.setString(2,guestbookDTO.getEmail()); 
			pstmt.setString(3,guestbookDTO.getHomepage()); 
			pstmt.setString(4,guestbookDTO.getSubject()); 
			pstmt.setString(5,guestbookDTO.getContent());  
			
			su = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt !=null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return su;
	}

	public List<GuestbookDTO> select() {
		List<GuestbookDTO> list = new ArrayList<GuestbookDTO>();
		
		String sql = "SELECT SEQ, NAME, EMAIL , HOMEPAGE,SUBJECT,CONTENT,TO_CHAR(LOGTIME,'YY.MM.DD') AS LOGTIME FROM GUESTBOOK ORDER BY SEQ DESC";
		
		getConnection();//접속
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();//실행.
			
			while(rs.next()) {
				GuestbookDTO guestbookDTO = new GuestbookDTO();
				guestbookDTO.setSeq(rs.getString("seq"));
				guestbookDTO.setName(rs.getString("name"));
				guestbookDTO.setEmail(rs.getString("email"));
				guestbookDTO.setHomepage(rs.getString("homepage"));
				guestbookDTO.setSubject(rs.getString("subject"));
				guestbookDTO.setContent(rs.getString("content"));
				guestbookDTO.setLogtime(rs.getString("logtime"));
				
				list.add(guestbookDTO);
				
			}//while 현재 값이 없을때 까지 반복
			
		} catch (SQLException e) {
			e.printStackTrace();
			list = null;
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
		
		return list;
	}
	
}
