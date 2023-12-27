package application;

public class score {
	
	private String name;
	private Integer kor;
	private Integer math;
	private Integer eng;
	
	public score(String name, Integer kor, Integer math, Integer eng) {
		this.name = name;
		this.kor = kor;
		this.math = math;
		this.eng = eng;
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getKor() {
		return kor;
	}

	public void setKor(Integer kor) {
		this.kor = kor;
	}

	public Integer getMath() {
		return math;
	}

	public void setMath(Integer math) {
		this.math = math;
	}

	public Integer getEng() {
		return eng;
	}

	public void setEng(Integer eng) {
		this.eng = eng;
	}

	@Override
	public String toString() {
		return "score [name=" + name + ", kor=" + kor + ", math=" + math + ", eng=" + eng + "]";
	}
	
	

}
