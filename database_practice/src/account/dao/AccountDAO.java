package account.dao;

import account.dto.AccountDTO;

public interface AccountDAO {
	// 계좌생성
	void insert(AccountDTO account);
	
	// 계좌정보를 이용하여 잔고정보 수정
	int update(AccountDTO account);
	
	// 계좌번호로 계좌 정보 조회
	AccountDTO selectAccount(String ano);
	
	// 계좌번호와 비밀번호가 일치하는 계좌정보 조회
	AccountDTO selectAccount(String ano, String password);
}










