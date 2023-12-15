package member.run;

import java.util.Scanner;

import member.dao.MemberDAO;
import member.dto.MemberDTO;


/**
 * 회원 관리에 대한 전반적인 내용의 정의한다.
 */
public abstract class AppBase {

	// 사용자 입력
	protected Scanner sc;

	// 로그인한 회원
	protected MemberDTO loginMember;

	// 프로그램 flag (true : 진행 , false : 종료)
	protected boolean isRun;

	// 메뉴 번호 선택
	protected int selectNo;
	
	// 회원 테이블 관리 클래스
	MemberDAO dao;
	
	// 프로그램 실행용 생성자
	protected AppBase(MemberDAO dao) {
		sc = new Scanner(System.in);
		this.dao = dao;
		isRun = true;
		isRun();
	}

	/*
	 * 기능 관리
	 */
	// 프로그램 실행 - 메뉴 정보별 기능 수행 Method
	protected abstract void isRun();

	// 프로그램 종료
	protected abstract void terminate();

	// 회원 가입 - 회원정보를 입력받아 회원목록 배열에 저장
	protected abstract void join();

	// 로그인 - 회원 목록에서 일치하는 회원을 찾아 loginMember Field에 저장
	protected abstract void login();

	// 회원목록 - 로그인한 회원이 관리자 일때만 노출
	protected abstract void select();

	// 회원정보 수정 - 로그인한 회원 정보랑 일치 하거나 관리자 일때만 수정(이름정보만 수정)
	protected abstract void update();

	// 회원탈퇴 - 삭제 할려는 정보가 본인 정보일때만 삭제
	protected abstract void delete();

	protected String getStringData(String message) {
		System.out.println(message);
		return sc.next();
	}

	protected int getNumberData(String message) {
		System.out.println(message);
		if (!sc.hasNextInt()) {
			System.out.println("사용할 수 없는 값입니다.");
			sc.next();
			return 0;
		}
		return sc.nextInt();
	}
}
