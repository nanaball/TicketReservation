package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionExample {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver class 존재");
		
			// java.sql.Connection
			// 1번 방법
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306?user=root&password=1234"
					// ? 이전 필요 정보 ? 이후 부가 정보 (키와 밸류)
			);
			System.out.println(conn);
			conn.close();
			
			// 2번 방법
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306",
					"root",
					"1234"
			);
			System.out.println(conn);
			conn.close();
			
			// 3번 방법 - properties 객체 사용
			Properties prop = new Properties();
			prop.setProperty("user", "root");
			prop.setProperty("password", "1234");
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", prop);
			System.out.println(conn);
			conn.close();
			// 일반적으로 프로퍼티즈 파일로 관리함 
			
			// 4. properties file 활용
			File file = new File("prop/mysql.properties");
			FileReader reader = new FileReader(file);
			prop = new Properties();
			prop.load(reader);
			
			System.out.println(prop);
			
			String url = prop.getProperty("url");
			System.out.println(url);
			
			conn = DriverManager.getConnection(url,prop);
			System.out.println(conn);
			conn.close();
			
		} catch (ClassNotFoundException e) {
			// 드라이브클래스 찾지 못한것
			e.printStackTrace();
		} catch (SQLException e) {
			// 지정한 위치와 계정정보 존재 x
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// 해당위치에 파일 찾지 못함
			e.printStackTrace();
		} catch (IOException e) {
			// 입출력 예외 
			e.printStackTrace();
		}
		
	} // end main 

}
