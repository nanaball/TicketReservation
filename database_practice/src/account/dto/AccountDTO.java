package account.dto;

public class AccountDTO {

	private String ano;			// 계좌번호
	private String owner;		// 계좌주
	private int balance;		// 잔고
	private String password;	// 비밀번호
	
	// 기본생성자
	public AccountDTO() {}

	// 전체값을 넘겨받는 생성자
	public AccountDTO(String ano, String owner, int balance, String password) {
		this.ano = ano;
		this.owner = owner;
		this.balance = balance;
		this.password = password;
	}

	public String getAno() {
		return ano;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return "Account[ ano : "+this.ano+" owner : " + this.owner + " balance : " + this.balance +" ]";
	}	
}




