package atm;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AtmSystem {
	private final int JOIN = 1;
	private final int LEAVE = 2;
	private final int LOGIN = 3;
	private final int LOGOUT = 4;
	private final int BANKING = 5;
	private final int SAVE = 6;
	private final int LOAD = 7;
	private final int EXIT = 0;
	// 뱅킹메뉴
	private final int DEPOSIT = 1;
	private final int WITHDRAWAL = 2;
	private final int BALANCE = 3;
	private final int TRANSFER = 4;
	private final int OPEN_ACCOUNT = 5;
	private final int CLOSE_ACCOUNT = 6;
	
	private final int NUM = 1;
	private final int STR = 2;

	private Scanner scan = new Scanner(System.in);
	private Random ran = new Random();

	private ArrayList<User> users = new ArrayList<>();
	private int log = -1;

	private boolean isRun = true;
	public void run() {
		// 회원가입/탈퇴
		// 계좌신청/ 철회 (1인 3계좌까지)
		// 로그인
		// 뱅킹기능(입금,출금,조회,이체,계좌생성,계좌철회)
		// 파일기능(저장,로드)
		//load();
		while (isRun) {
			runMain();
		}
	}
	private void runMain() {
		System.out.println("1)회원가입");
		System.out.println("2)회원탈퇴");
		System.out.println("3)로그인");
		System.out.println("4)로그아웃");
		System.out.println("5)뱅킹");
		System.out.println("6)저장");
		System.out.println("7)로드");
		System.out.println("0)종료");
		
		int sel = (int) input("메뉴 선택", NUM);

		if (sel == JOIN)
			join();
		else if (sel == LEAVE)
			leave();
		else if (sel == LOGIN)
			login();
		else if (sel == LOGOUT)
			logout();
		else if (sel == BANKING)
			banking();
		else if (sel == SAVE)
			save();
		else if (sel == LOAD)
			load();
		else if (sel == EXIT) {
			System.out.println("시스템을 종료합니다.");
			isRun=false;
		}
	}

	private void printUsersInfo() {
		for(int i=0;i<users.size();i++) {
			System.out.println(users.get(i));
		}
		
	}
	
	private void runMenu() {
		int sel = (int) input("메뉴 선택", NUM);

		if (log != -1 && (sel == JOIN || sel == LOGIN)) {
			System.err.println("로그아웃 후 이용가능");
			return;
		} else if (log == -1 && (sel >= LEAVE && sel <= CLOSE_ACCOUNT || sel == LOGOUT)) {
			System.err.println("로그인 후 이용가능");
			return;
		}
		System.out.println(log);

		if (sel == JOIN)
			join();
		else if (sel == LEAVE)
			leave();
		else if (sel == OPEN_ACCOUNT)
			openAccount();
		else if (sel == CLOSE_ACCOUNT)
			closeAccount();
		else if (sel == LOGIN)
			login();
		else if (sel == LOGOUT)
			logout();

	}

	private void join() {
		System.out.println("====== 회원가입 ======");
		String name = (String) input("이름 입력", STR);
		String pw = (String) input("비밀번호 입력", STR);
		int code = generateUserCode();

		User user = new User(code, name, pw);
		users.add(user);

		System.out.println("회원가입완료.");
		System.out.println("발급된 회원코드는 " + code + "입니다.");
	}

	private int generateUserCode() {
		while (true) {
			int code = ran.nextInt(9000) + 1000;
			System.out.println(code);
			boolean isFound = false;
			System.out.println(users.size());
			for (int i = 0; i < users.size(); i++)
				if (users.get(i).getCode() == code)
					isFound = true;
			if (!isFound)
				return code;
		}
	}

	private void leave() {
		System.out.println("====== 회원탈퇴 ======");
		String pw = (String) input("비밀번호 입력", STR);

		if (!users.get(log).getPassword().equals(pw)) {
			System.err.println("비밀번호가 틀렸습니다.");
			return;
		}

		users.remove(log);
		System.out.println("회원탈퇴완료");
		log = -1;
	}
	

	private void login() {
		System.out.println("====== 로그인 ======");

		int code = (int) input("회원코드 입력", NUM);
		String pw = (String) input("비밀번호 입력", STR);

		User user = new User(code, pw);

		log = users.indexOf(user);
		System.out.println(log);
		if (log != -1)
			System.out.println("로그인 완료");
		else
			System.err.println("회원정보를 확인해주세요.");
	}

	private void logout() {
		System.out.println("====== 로그아웃 ======");
		log = -1;
		System.out.println("로그아웃 완료");
	}
	
	private void banking() {
		
	}
	
	private void openAccount() {
		User user = users.get(log);
		if (user.getAccountSize() == 3) {
			System.err.println("더 이상 계좌를 개설할 수 없습니다.");
			return;
		}
		System.out.println("====== 계좌신청 ======");

		String pw = (String) input("비밀번호 입력", STR);

		if (!user.getPassword().equals(pw)) {
			System.err.println("비밀번호가 틀렸습니다.");
			return;
		}

		int code = generateAccountCode();
		user.addAccount(new Account(code));

		System.out.println("계좌신청완료.");
		System.out.println("발급된 계좌번호는 " + code + "입니다.");
	}

	private int generateAccountCode() {
		while (true) {
			int code = ran.nextInt(9000) + 1000;

			boolean isFound = false;
			for (int i = 0; i < users.size(); i++)
				if (users.get(i).getAccountByCode(code) != null)
					isFound = true;

			if (!isFound)
				return code;
		}
	}

	private void closeAccount() {
		User user = users.get(log);
		int size = user.getAccountSize();
		if (size == 0) {
			System.err.println("개설된 계좌가 없습니다.");
			return;
		}
		System.out.println("====== 계좌철회 ======");
		System.out.println(user);
		int sel = (int) input("철회할 계좌 선택", NUM) - 1;

		if (sel < 0 || sel >= size) {
			System.err.println("입력범위 오류");
			return;
		}

		String pw = (String) input("비밀번호 입력", STR);

		if (!user.getPassword().equals(pw)) {
			System.err.println("비밀번호가 틀렸습니다.");
			return;
		}
		
		user.removeAccount(sel);
		System.out.println("계좌철회완료");
	}
	
	private void save() {
		
	}
	
	private void load() {
		
	}

	private Object input(String msg, int type) {
		System.out.print(msg + " : ");
		String input = scan.nextLine();

		if (type == NUM) {
			int num = -1;
			try {
				num = Integer.parseInt(input);
			} catch (Exception e) {
				System.err.println("숫자만 입력 가능합니다.");
			}
			return num;
		} else if (type == STR) {
			while (input.isBlank()) {
				input = scan.nextLine();
			}
			return input;
		}
		return null;
	}
}
