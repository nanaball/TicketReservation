package post;



public class Connection {
	
	public static void main(String[] args) {

		
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver class 존재");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	}

}
