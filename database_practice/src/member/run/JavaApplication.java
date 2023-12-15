package member.run;

import member.dao.MemberDAOImpl;

/**
 * Application 실행 class
 * API class와 구분 
 */
public class JavaApplication {
	public static void main(String[] args) {
		new MemberManagement(new MemberDAOImpl());
	}
}
