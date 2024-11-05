package atm;

import java.util.ArrayList;

public class User {
	private int code;
	private String name;
	private ArrayList<Account> accounts;
	
	public User( int code, String name) {
		this.code=code;
		this.name=name;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
	}
	
	public Account getAccountByIndex(int index) {
		return accounts.get(index);
	}
	
	public Account getAccount(int code) {
		Account account=new Account(code);
		
		int idx=accounts.indexOf(account);
		if(idx == -1)
			return null;
		
		return accounts.get(idx);
	}
	
	public int getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.code+") "+this.name;
	}
}
