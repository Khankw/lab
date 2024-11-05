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
