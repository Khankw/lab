package atm;

public class Account { // 각 유저가 갖는 복수의 계좌?
	private int code;
	private int money;

	public Account(int code) {
		this.code = code;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Account) {
			Account other = (Account) obj;
		}

		return false;
	}

	@Override
	public String toString() {
		return String.format("계좌 %d - 잔액 %d원", code, money);
	}
}
