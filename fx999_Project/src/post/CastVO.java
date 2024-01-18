package post;

public class CastVO {
	
	private String 날짜;
	private String 시간;
	private String 캐스팅;
	
	 
	public CastVO() {}
	
	public CastVO(String 날짜, String 시간, String 캐스팅) {
		this.날짜 = 날짜;
		this.시간 = 시간;
		this.캐스팅 = 캐스팅;
	}

	public String get날짜() {
		return 날짜;
	}

	public void set날짜(String 날짜) {
		this.날짜 = 날짜;
	}

	public String get시간() {
		return 시간;
	}

	public void set시간(String 시간) {
		this.시간 = 시간;
	}

	public String get캐스팅() {
		return 캐스팅;
	}

	public void set캐스팅(String 캐스팅) {
		this.캐스팅 = 캐스팅;
	}

	@Override
	public String toString() {
		return "CastVO [날짜=" + 날짜 + ", 시간=" + 시간 + ", 캐스팅=" + 캐스팅 + "]";
	}

	

}
