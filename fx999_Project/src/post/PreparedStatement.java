package post;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatement {
	
	public static void main(String[] args) {

		Connection conn = null;
		// 동적인 쿼리객체
		// 쿼리문을 먼저 등록 시켜놓고 질의 실행에 필요한 데이터를 나중에 추가
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
			try {
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdb","root","1234");
				System.out.println("DB 연결 완료");
			} catch (SQLException e) {
				e.printStackTrace();
			}
}
}