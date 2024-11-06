package atm;

import java.util.ArrayList;

public class User {
	private int code; // 부여 ID?
	private String name;
	private String pw;
	private ArrayList<Account> accounts =new ArrayList<>();

	public User(int code, String pw) {
		this.code = code;
		this.name = name;
		this.pw = pw;
	}

	public User(int code, String name, String pw) {
		this.code = code;
		this.name = name;
		this.pw = pw;
	}

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.pw;
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public void removeAccount(int index) {
		accounts.remove(index);
	}

	public Account getAccountByIndex(int index) {
		return accounts.get(index);
	}

	public Account getAccountByCode(int code) {
		Account account = new Account(code);

		int idx = accounts.indexOf(account);
		if (idx == -1)
			return null;

		return accounts.get(idx);
	}

	public int getAccountSize() {
		return accounts.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User) obj;
			return other.code == this.code && other.pw.equals(this.pw);
		}
		return false;
	}

	@Override
	public String toString() {
		String msg = String.format("%s(%d)님 계좌 목록\n", this.name, this.code);
		int size = accounts.size();
		for (int i = 0; i < size; i++) {
			msg += String.format("%d) %s", i + 1, accounts.get(i));
			if (i < size - 1)
				msg += "\n";
		}

		if (size == 0)
			msg += "만들어진 계좌가 없습니다.";

		return msg;
	}
}
