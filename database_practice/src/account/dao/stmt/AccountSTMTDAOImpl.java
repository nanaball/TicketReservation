package account.dao.stmt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import account.dao.AccountDAO;
import account.dto.AccountDTO;

// Account
// Statement
// Database Access Object
public class AccountSTMTDAOImpl implements AccountDAO {

	// DBMS 연결 객체
	private Connection conn;
	// 연결정보를 가지고 질의를 실행하는 객체
	private Statement stmt;
	// 검색질의의 결과를 전달 받는 객체
	private ResultSet rs;

	public AccountSTMTDAOImpl() {
		// Connection 초기화
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sqldb", "root", "1234");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			return;
		}
	}

	// account의 정보를 tbl_account table에 삽입
	@Override
	public void insert(AccountDTO account) {
		// 작성
		
		try {
			stmt = conn.createStatement();
			String sql = "INSERT INTO tbl_account " 
						+ "VALUES('"+account.getAno()+"','"+account.getOwner()+"',"+account.getBalance()+",'"+account.getPassword()+"')";
			System.out.println(sql);
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {}
		}
		
	}

	// 계좌번호가 일치하는 계좌주의
	// 잔고를 수정
	// UPDATE tbl_account SET balance = 값 WHERE ano = '계좌번호'
	@Override
	public int update(AccountDTO account) {
		int result = 0;
		// 작성
		
		String sql = "UPDATE tbl_account SET balance = "+account.getBalance()+" WHERE ano = '"+account.getAno()+"'";
		System.out.println(sql);	
		
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {}
		}
		return result;
	}

	// SELECT * FROM tbl_account WHERE ano = '계좌번호';
	@Override
	public AccountDTO selectAccount(String ano) {
		AccountDTO acc = null;
		// 작성
		
		String sql = "SELECT * FROM tbl_account WHERE ano = '"+ano+"'";
		// "SELECT * FROM tbl_account WHERE ano = 'ano';
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		
			if(rs.next()) {
				acc = new AccountDTO(
					rs.getString("ano"),		// 1
					rs.getString("owner"),		// 2
					rs.getInt("balance"),		// 3
					rs.getString("password")	// 4
				);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			
			if(rs != null)
				try {
					rs.close();
				} catch (SQLException e) {}
			
			if(stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {}
		}
		return acc;
	}

	// SELECT * FROM tbl_account
	// WHERE ano = '계좌번호' AND password = '비밀번호';
	@Override
	public AccountDTO selectAccount(String ano, String password) {
		AccountDTO acc = null;
		// 작성
		String sql = "SELECT * FROM tbl_account "
				+ " WHERE ano = '"+ano+"' AND password = '"+password+"' ";
	System.out.println(sql);
	
	try {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		
		if(rs.next()) {
			acc = new AccountDTO();
			String a = rs.getString(1);
			String owner = rs.getString(2);
			int balance = rs.getInt(3);
			String pass = rs.getString(4);
			
			acc.setAno(a);
			acc.setOwner(owner);
			acc.setBalance(balance);
			acc.setPassword(pass);
		}
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		
		if(rs != null)
			try {
				rs.close();
			} catch (SQLException e) {}
		
		if(stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {}
	}
		
		return acc;
	}
}
