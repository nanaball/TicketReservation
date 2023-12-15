package account.dao.pstmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import account.dao.AccountDAO;
import account.dto.AccountDTO;

public class AccountPSTMTDAOImpl implements AccountDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	@Override
	public void insert(AccountDTO account) {
		// 작성
	}

	@Override
	public int update(AccountDTO account) {
		int result = 0;
		// 작성
		return result;
	}

	@Override
	public AccountDTO selectAccount(String ano) {
		AccountDTO account = null;
		// 작성
		return account;
	}
	
	public AccountDTO getAccount(ResultSet rs) throws SQLException {
		AccountDTO account = null;
		// 작성
		return account;
	}
	@Override
	public AccountDTO selectAccount(String ano, String password) {
		AccountDTO account = null;
		// 작성
		return account;
	}

}







