package atm;

public class Account { 
	private int code;

	public Account(int code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account other = (Account) obj;
			return other.code==this.code;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("계좌 번호 - %d", code);
	}
}
