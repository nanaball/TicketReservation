package c3_view_controls;

// Data Transfer Object - Data를 전송하기 위한 객체 
// Phone Value Object - 값을 저장하는 용도로 사용되는 읽기 전용 객체
public class PhoneVO {
	
	private String name;
	private String path;
	
	public PhoneVO() {}
	
	public PhoneVO(String name, String path) {
		this.name = name;
		this.path = path;
	}

	// getter setter toString
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "PhoneVO [name=" + name + ", path=" + path + "]";
	}

 
	
	
}
