import java.sql.Connection;

import utils.DBUtil;

public class TestDriver {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 등록 완료");
			Connection conn = DBUtil.getConnection();
			System.out.println(conn);
			conn = DBUtil.getConnection();
			System.out.println(conn);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	} // end main

}
