package base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class StatementExample {

	public static void main(String[] args) {

		// Statement - Connection 객체의 연결정보를 이용하여 질의를 실행하고 결과를 반환
		// DBMS 와 연결된 session 정보를 저장하고 있는 객체
		Connection conn = null;
		// session 내에서 질의 전송을 도와주는 객체
		Statement stmt = null;
		// 검색 질의의 결과 정보를 행단위로 저장하는 객체
		ResultSet rs = null;
		
		try {
			// Class.forName("com.mysql.cj.jdbc.Driver");
			Properties prop = new Properties();
			prop.load(new FileReader("prop/mysql.properties"));
			System.out.println(prop);
			
			conn = DriverManager.getConnection(
				prop.getProperty("url"),
				prop
			);
			System.out.println(conn);
			
			// 연결된 DBMS에서 지정된 데이터베이스에서 질의를 수행할 Statement 객체 반환
			stmt = conn.createStatement();
			String sql = "SELECT * FROM userTbl";
			
			// 검색 질의 실행 후 결과를 저장
			rs = stmt.executeQuery(sql);
			
			// rs.nest()
			// 읽어올 행 정보가 있으면 행당 위치로 cursor를 이동한 후 true반환
			// 익을 행 정보가 존재하지 않으면 false 반환
			while(rs.next()) {
				//    1      2        3      4       5        6       7        8
				// userID, name, birthYear, addr, mobile1, mobile2, height, mDate
				String userId = rs.getString(1);
				String name = rs.getString(2);
				int birthYear = rs.getInt("birthYear");
				String addr = rs.getString("addr");
				String mobile1 = rs.getString(5);
				String monile2 = rs.getString(6);
				int height = rs.getInt("height");
				// java.util.Date
				Date mDate = rs.getDate(8);
				String result = String.format("%s-%s-%d-%s",userId,name,birthYear,addr);
				System.out.println(result);
				System.out.println("----------------------------------------");
			}
			rs.close();
			stmt.close();
			// 검색 쿼리 작업 자원해제
			/*
			'userID', 'char(8)', 'NO', 'PRI', NULL, ''
			'name', 'varchar(10)', 'NO', '', NULL, ''
			'birthYear', 'int', 'NO', '', NULL, ''
			'addr', 'char(2)', 'NO', '', NULL, ''
			*/
			
			// 데이블에 삽입할 값을 입력받음
			Scanner sc = new Scanner(System.in);
			System.out.println("userId를 입력해주세요");
			String id = sc.next();
			System.out.println("이름을 입력해주세요");
			String name = sc.next();
			System.out.println("생년월일을 입력해주세요 ex)1982");
			int birthYear = sc.nextInt();
			System.out.println("주소를 입력해주세요 도시이름 2자");
			String addr = sc.next();
			
			sql = "INSERT INTO userTbl(userID,name,birthYear,addr)"+" VALUES('"+id+"','"+name+"',"+birthYear+" ,'"+addr+"')";
			System.out.println(sql);
			
			// 질의를 수행하기 위한 statement 객체 생성
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			System.out.println(result + "개의 행 삽입 완료");
			
			stmt.close();
			conn.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("properties 파일을 찾을 수 없음");
		} catch (IOException e) {
			System.out.println("properties 파일을 읽을 수 없음");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 정보 오류");
		}
		
	} // end main

}
