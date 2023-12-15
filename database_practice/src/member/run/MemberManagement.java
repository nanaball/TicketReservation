package member.run;

import java.util.ArrayList;

import member.dao.MemberDAO;
import member.dto.BackUpMemberDTO;
import member.dto.MemberDTO;

public class MemberManagement extends AppBase{
	
	public MemberManagement(MemberDAO dao) {
		super(dao);
	}

	@Override
	protected void isRun() {
		while(isRun) {
			System.out.println("===================================================================");
			System.out.println("1.회원가입 | 2.로그인 |3.회원정보 | 4.회원정보수정 |5.회원탈퇴 |6.회원탈퇴목록|7.종료" );
			System.out.println("===================================================================");
			selectNo = getNumberData("메뉴 선택 > ");
			
			switch(selectNo) {
			case 1 :
				System.out.println("== 회원가입 == ");
				join();
				break;
			case 2 :
				System.out.println("== 로그인 == ");
				login();
				break;
			case 3 :
				System.out.println("== 회원목록 == ");
				select();
				break;
			case 4 :
				System.out.println("== 회원정보수정 == ");
				update();
				break;
			case 5 :
				delete();
				System.out.println("== 회원탈퇴 == ");
				break;
			case 6 :
				System.out.println("== 탈퇴한 회원 목록 ==");
				deleteMember();
				break;
			case 7 : 
				System.out.println("== 프로그램 종료 == ");
				terminate();
				break;
			default :
				System.out.println("해당 메뉴가 존재 하지 않습니다.");
			}
		}
	}
	// 탈퇴한 회원 정보 
	// 관리자만 확인 가능
	private void deleteMember() {
		if(loginMember == null
				|| 
		   !loginMember.getmId().equals("root")) {
			System.err.println("확인 할 수 없는 사용자 입니다.");
			return;
		}
		
		ArrayList<BackUpMemberDTO> deletes = dao.deleteMember();
		System.out.println("========================");
		for(BackUpMemberDTO m : deletes) {
			System.out.println(m);
		}
		System.out.println("========================");
	}

	@Override
	protected void terminate() {
		isRun = false;
	}

	@Override
	protected void join() {
		/*
		 * mId - 중복아이디는 가입 불가
		 * mPw == rePw - 비밀번호가 일치하면 통과
		 * mName - 사용자 이름
		 * 회원정보 등록
		 * 완료 시 등록된 회원 정보를 넘겨받아 출력 
		 */
		String mId = getStringData("사용자 정보를 입력해주세요.\n아이디를 입력해주세요 > ");
		String mPw = getStringData("비밀번호를 입력해 주세요 > ");
		String rePw = getStringData("비밀번호를 확인해 주세요 > ");
		// 아이디가 존재하거나 비밀번호가 일치하지 않음
		if(!dao.selectMember(mId) || !mPw.equals(rePw)) {
			System.out.println("사용 할 수 없는 아이디 이거나 비밀번호가 일치하지 않습니다.");
			return;
		}
		// 회원가입 진행
		String mName = getStringData("이름을 입력해 주세요 > ");
		
		MemberDTO m = dao.join(new MemberDTO(mName,mId,mPw));
		
		if(m != null) {
			System.out.println(m);
			System.out.println("회원가입 완료");
		}else {
			System.out.println("정상적으로 처리되지 못하였습니다.");
			System.out.println("다시 시도해 주세요.");
		}
	}

	@Override
	protected void login() {
		/*
		 * mId 아이디
		 * mPw 비밀번호
		 * 아이디와 비밀번호가 일치하는 사용자가 존재할때
		 * 로그인 처리 - loginMember
		 */
		String mId = getStringData("사용자 정보를 입력해주세요.\n아이디를 입력해주세요 > ");
		String mPw = getStringData("비밀번호를 입력해 주세요 > ");
		MemberDTO member = dao.selectMember(mId, mPw);
		if(member == null) {
			System.out.println("일치하는 정보가 없습니다.(로그인 실패)");
			return;
		}
		
		loginMember = member;
		System.out.println(loginMember);
		if(loginMember.getmId().equals("root")) {
			System.out.println("관리자 계정으로 로그인 하셨습니다.");
		}else {
			System.out.println("일반회원으로 로그인 하셨습니다.");
		}
	}

	// 회원 목록 - 로그인한회원(loginMember)
	// 관리자 - 전체회원목록 출력
	// 일반회원 - 자기자신의 정보만 출력
	// 비로그인 - 권한이없다고 출력
	@Override
	protected void select() {
		if(loginMember == null) {
			System.err.println("로그인 이후 사용 가능 합니다.");
			return;
		}
		if(!loginMember.getmId().equals("root")) {
			System.out.println("일반 회원 입니다.");
			System.out.println(loginMember);
		}else {
			System.out.println("=======================");
			ArrayList<MemberDTO> list = dao.select();
			for(MemberDTO m : list) {
				System.out.println(m);
			}
			System.out.println("=======================");
		}
	}

	/*
	 * 회원정보 수정
	 * 로그인한 회원정보랑 일치하는지 비밀번호 확인
	 * 일치하는 사용자는 자기자신의 정보 수정
	 * 
	 * 관리자 일 경우 회원 번호로 이름정보를 수정
	 * 
	 * 비로그인은 권한이 없다고 출력
	 */
	@Override
	protected void update() {
		if(loginMember == null) {
			System.out.println("로그인 이후에 사용할 수 있습니다.");
			return;
		}
		
		if(loginMember.getmId().equals("root")) {
			// 관리자 계정
			System.out.println("== 관리자 회원 정보 수정 ==");
			select();
			int mNum = getNumberData("수정할 회원 번호를 입력해주세요 > ");
			MemberDTO member = dao.selectMember(mNum);
			if(member == null) {
				System.err.println("수정 할 회원의 정보가 없습니다.");
				return;
			}
			String name = getStringData(mNum+"번 사용자의 수정할 이름을 입력해주세요 > ");
			member.setmName(name);
			int result = dao.update(member);
			if(result != 1) {
				System.out.println("수정 실패");
			}else {
				System.out.println("수정 완료");
			}
		}else {
			// 일반 회원
			System.out.println("== 내 정보 수정 ==");
			String pw = getStringData("로그인된 사용자의 비밀번호를 입력해주세요 > ");
			if(!loginMember.getmPw().equals(pw)) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				return;
			}
			String name = getStringData("수정할 이름을 입력해주세요 > ");
			loginMember.setmName(name);
			int result = dao.update(loginMember);
			String resultStr = (result != 1) ? "수정 실패" : "수정 완료";
			System.out.println(resultStr);
					
		}
		
	}

	/*
	 * 회원탈퇴 - 삭제할려는 정보가 본인 정보와 일치할때만 삭제
	 * - 관리자 계정은 삭제 불가
	 * - 삭제 여부를 확인하여 확인 되었을때만 삭제
	 * - 삭제된 회원 정보는 backup table에 저장 
	 */
	@Override
	protected void delete() {
		if(loginMember == null 
				|| 
		   loginMember.getmId().equals("root")) {
			System.out.println("회원정보를 삭제 할 수 없습니다.");
			return;
		}
		
		String pw = getStringData("탈퇴하려는 회원의 비밀번호를 입력해주세요 > ");
		if(!pw.equals(loginMember.getmPw())) {
			System.out.println("회원정보가 일치하지 않습니다.");
			return;
		}
		
		String str = getStringData("정말로 탈퇴하시겠습니까? y/n");
		switch(str) {
			case "Y" : case "y" : case "ㅛ" :
				int result = dao.delete(loginMember.getmNum());
				if(result > 0) {
					loginMember = null;
					System.out.println("회원탈퇴 완료");
				}else {
					System.out.println("회원 탈퇴 실패");
				}
				break;
			default : 
				System.out.println("탈퇴가 취소 되었습니다.");
		}
		
	}

}
















