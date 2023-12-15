package member.dto;

import java.util.Date;

public class BackUpMemberDTO extends MemberDTO{
	
	// 회원 탈퇴 일 정보
	private Date deleteDate;
	
	public BackUpMemberDTO(int mNum, String mName, String mId, String mPw, long reg, Date deleteDate) {
		super(mNum, mName, mId, mPw, reg);
		this.deleteDate = deleteDate;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Override
	public String toString() {
		return super.toString()+" [deleteDate=" + deleteDate + "]";
	}
	
}

















