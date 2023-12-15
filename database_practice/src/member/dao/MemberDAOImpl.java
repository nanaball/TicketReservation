package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import member.dto.BackUpMemberDTO;
import member.dto.MemberDTO;

public class MemberDAOImpl implements MemberDAO {

	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// MemberVO 정보를 넘겨받아서 회원가입을 진행하고
	// 가입 완료된 회원의 정보를 검색하여 Member type으로 반환
	@Override
	public MemberDTO join(MemberDTO memberVO) {
		MemberDTO m = null;
		// 작성
		return m;
	}
	
	// 로그인 - id 와 pw 가 일치하는 사용자일시 정보 전달
	@Override
	public MemberDTO selectMember(String mId, String mPw) {
		MemberDTO member = null;
		// 작성
		return member;
	}
	
	// 회원번호로 회원 정보 검색
	@Override
	public MemberDTO selectMember(int mNum) {
		MemberDTO member = null;
		// 작성
		return member;
	}

	// 기존에 동일한 아이디가 존재하는지 확인
	// true 사용가능 - 아이디가 존재하지 않으면
	// false 사용불가 - 아이디가 존재하면
	@Override
	public boolean selectMember(String mId) {
		boolean isChecked = true;
		// 작성
		return isChecked;
	}

	// 전체 회원 목록을 ArrayList에 담아서 반환
	@Override
	public ArrayList<MemberDTO> select() {
		ArrayList<MemberDTO> members = new ArrayList<>();
		// 작성
		return members;
	}
	
	// mNum이 일치하는 사용자의 회원이름 정보 수정
	@Override
	public int update(MemberDTO member) {
		int result = 0;
		// 작성
		return result;
	}
	
	// 회원 탈퇴 - 회원번호를 입력받아 일치하는 회원 정보 삭제
	@Override
	public int delete(int mNum) {
		int result = 0;
		// 작성
		return result;
	}
	
	// 탈퇴한 회원 정보 검색
	@Override
	public ArrayList<BackUpMemberDTO> deleteMember() {
		ArrayList<BackUpMemberDTO> deletes = new ArrayList<>();
		// 작성
		return deletes;
	}
	
}












