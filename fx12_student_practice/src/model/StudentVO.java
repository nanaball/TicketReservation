package model;

/**
 * 학생 별 점수를 저장할 class
 * Value Object - 값을 저장하는 class
 * tableView와 binding 되는 instance
 */
public class StudentVO {
	
	private String name;
	private int kor;
	private int math;
	private int eng;
	
	// alt + s+ a ->deselect all
	public StudentVO() {
		super();
	}

	// alt + s+ a ->select all
	public StudentVO(String name, int kor, int math, int eng) {
		super();
		this.name = name;
		this.kor = kor;
		this.math = math;
		this.eng = eng;
	}

	// alt s + r
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getKor() {
		return kor;
	}


	public void setKor(int kor) {
		this.kor = kor;
	}


	public int getMath() {
		return math;
	}


	public void setMath(int math) {
		this.math = math;
	}


	public int getEng() {
		return eng;
	}


	public void setEng(int eng) {
		this.eng = eng;
	}


	// alt s + s+ s
	@Override
	public String toString() {
		return "StudenVO [name=" + name + ", kor=" + kor + ", math=" + math + ", eng=" + eng + "]";
	}


	

}
