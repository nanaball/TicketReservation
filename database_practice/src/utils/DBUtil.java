package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	
	private static Connection conn;
	
	public static Connection getConnection() {
		
		// conn field에서 참조하는 객체 - 값이 없으면 Connection 객체 생성 
		if(conn == null) {
			
			try {
				File file = new File("prop/db.properties");
				Properties prop = new Properties();
				prop.load(new FileReader(file));
				
				String driver = prop.getProperty("driver");
				String url = prop.getProperty("url");
				
				Class.forName(driver);
				conn = DriverManager.getConnection(url,prop);
				
			
			} catch (FileNotFoundException e) {
				System.out.println("properties 파일을 찾을 수 없음");
			} catch (IOException e) {
				System.out.println("properties 파일을 찾을 수 없음");
			} catch (ClassNotFoundException e) {
				System.out.println("properties 파일을 찾을 수 없음");
			} catch (SQLException e) {
				System.out.println("DB 연결 url user password 오류");
			}
		}
		
		return conn;
	}

	public static void close(AutoCloseable... closer) {
		// ... 가변형 인자열 - 순서대로 나열되어있는 배열로 값이 전달됨 -> 주의/ 가변형 인자 뒤에 closer, String a 같이 매개변수 여러개x but 앞에는 선언 가능
		
		for(AutoCloseable c : closer) {
			if(closer != null) {
				
				try {
					c.close();
				} catch (Exception e) {}
			}
		}
	}

	
	/*
	public static void close(ResultSet rs) {
		if(rs != null) {

			try {
				rs.close();
			} catch (SQLException e) {}
		}
		
	}

	public static void close(Statement pstmt) {
		
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {}
		}
	}
	*/
}
