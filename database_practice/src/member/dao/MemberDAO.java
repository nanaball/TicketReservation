package member.dao;

import java.util.ArrayList;

import member.dto.BackUpMemberDTO;
import member.dto.MemberDTO;

public interface MemberDAO {
	// 회원가입
	// 회원정보를 저장하고 있는 member를 넘겨받아서
	// 회원정보를 테이블에 추가하고 등록된 회원 정보를 반환
	MemberDTO join(MemberDTO member);
	
	// 회원 검색
	// mId , mPw가 일치하는 사용자 검색
	MemberDTO selectMember(String mId, String mPw);
	
	// mNum 회원번호로 회원 검색
	MemberDTO selectMember(int mNum);
	
	// 아이디가 기존에 존재하는지 확인
	boolean selectMember(String mId);
	
	// 회원 목록 검색
	ArrayList<MemberDTO> select();
	
	// 회원정보 수정
	int update(MemberDTO member);
	
	// 회원 탈퇴 - 회원 정보 삭제
	int delete(int mNum);
	
	// 탈퇴한 회원 목록 
	ArrayList<BackUpMemberDTO> deleteMember();
	
}

















