package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

	private final int MAXUSER = 9000;

	private Scanner scan = new Scanner(System.in);
	private Random ran = new Random();

	private FileWriter fileWriter;
	private FileReader fileReader;
	private BufferedReader bufferedReader;

	private String fileName = "ATM.txt";
	private File file = new File(fileName);

	private ArrayList<User> users = new ArrayList<>();
	private int log = -1;

	private boolean isRun = true;

	public void run() {
		// 회원가입/탈퇴
		// 계좌신청/ 철회 (1인 3계좌까지)
		// 로그인
		// 뱅킹기능(입금,출금,조회,이체,계좌생성,계좌철회)
		// 파일기능(저장,로드)
		
		// 마지막 커밋 메세지 :
		// Release v1.0.0 with core features
		
		load();
		while (isRun) {
			runMain();
		}
	}

	private void runMain() {
		System.out.println("======== ATM ========");
		System.out.println("1)회원가입");
		System.out.println("2)회원탈퇴");
		System.out.println("3)로그인");
		System.out.println("4)로그아웃");
		System.out.println("5)뱅킹");
		System.out.println("6)저장");
		System.out.println("7)로드");
		System.out.println("0)종료");
		printUsersInfo();
		int sel = (int) input("메뉴 선택", NUM);

		if (log == -1 && (sel == LEAVE || sel == LOGOUT || sel == BANKING)) {
			System.err.println("로그인 후 이용가능한 메뉴입니다.");
			return;
		}
		if (log != -1 && (sel == JOIN || sel == LOGIN)) {
			System.err.println("로그아웃 후 이용가능한 메뉴입니다.");
			return;
		}

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
//			isRun = false;
		}
	}

	private void printUsersInfo() {
		System.out.println("------ 가입유저정보 ------");
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			String name = user.getName();
			int code = user.getCode();
			String pw = user.getPassword();
			int count = user.getAccountSize();
			String str = String.format("%s(%d) pw : %s  AccCount : %d", name, code, pw, count);
			System.out.println(str);
		}
		if (log != -1) {
			System.out.println("----- 로그인유저정보 -----");
			System.out.println(users.get(log));
		}
	}

	private void join() {
		if (users.size() == MAXUSER) {
			System.err.println("더 이상 가입불가합니다.");
			return;
		}

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
			int code = ran.nextInt(MAXUSER) + 1000;

			boolean isFound = false;
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
		log = -1;
		System.out.println("회원탈퇴완료");
	}

	private void login() {
		System.out.println("====== 로그인 ======");

		int code = (int) input("회원코드 입력", NUM);
		String pw = (String) input("비밀번호 입력", STR);

		User user = new User(code, pw);

		log = users.indexOf(user);
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
		// 뱅킹기능(입금,출금,조회,이체,계좌생성,계좌철회)
		while (true) {
			System.out.println("======== 뱅킹 ========");
			System.out.println("1)입금");
			System.out.println("2)출금");
			System.out.println("3)조회");
			System.out.println("4)이체");
			System.out.println("5)계좌생성");
			System.out.println("6)계좌철회");
			System.out.println("0)종료");
			User user = users.get(log);
			System.out.println(user);
			int sel = (int) input("메뉴 선택", NUM);

			int size = user.getAccountSize();
			if (size == 0 && (sel >= DEPOSIT && sel <= TRANSFER || sel == CLOSE_ACCOUNT)) {
				System.err.println("개설된 계좌가 없습니다.");
				continue;
			}

			if (sel == DEPOSIT)
				deposit();
			else if (sel == WITHDRAWAL)
				withdrawal();
			else if (sel == BALANCE)
				balance();
			else if (sel == TRANSFER)
				transfer();
			else if (sel == OPEN_ACCOUNT)
				openAccount();
			else if (sel == CLOSE_ACCOUNT)
				closeAccount();
			else if (sel == EXIT)
				break;
		}
	}

	private void deposit() {
		System.out.println("======== 입금 ========");
		Account account = selectAccount();

		if (account == null)
			return;

		System.out.println(account);
		int money = (int) input("입금 금액 입력", NUM);
		if (money <= 0) {
			System.err.println("0이하의 금액은 입금이 불가합니다.");
			return;
		}

		account.addMoney(money);
		System.out.println(account);
		System.out.println("입금완료");
	}

	private Account selectAccount() {
		User user = users.get(log);
		for (int i = 0; i < user.getAccountSize(); i++) {
			int code = user.getAccountByIndex(i).getCode();
			System.out.println(String.format("%d) %d", i + 1, code));
		}

		int sel = (int) input("계좌 선택", NUM) - 1;

		if (sel < 0 || sel >= user.getAccountSize()) {
			System.err.println("입력범위 오류");
			return null;
		}

		return user.getAccountByIndex(sel);
	}

	private void withdrawal() {
		System.out.println("======== 출금 ========");
		Account account = selectAccount();

		if (account == null)
			return;

		System.out.println(account);
		int money = (int) input("출금 금액 입력", NUM);
		if (money <= 0) {
			System.err.println("0이하의 금액은 출금이 불가합니다.");
			return;
		}

		if (money > account.getMoney()) {
			System.err.println("계좌의 잔액이 부족합니다.");
			return;
		}

		account.addMoney(-money);

		System.out.println(account);
		System.out.println("출금완료");
	}

	private void balance() {
		System.out.println("======== 조회 ========");
		Account account = selectAccount();

		if (account == null)
			return;

		System.out.println(account);
	}

	private void transfer() {
		System.out.println("======== 이체 ========");
		Account account = selectAccount();

		if (account == null)
			return;

		int code = (int) input("이체할 계좌번호 입력", NUM);

		if (code == account.getCode()) {
			System.err.println("동일계좌로는 이체불가합니다.");
			return;
		}

		Account transferAcc = getAccountByCode(code);
		if (transferAcc == null) {
			System.err.println("없는 계좌 번호입니다.");
			return;
		}

		System.out.println(account);
		int money = (int) input("이체 금액 입력", NUM);
		if (money <= 0) {
			System.err.println("0이하의 금액은 이체가 불가합니다.");
			return;
		}

		if (money > account.getMoney()) {
			System.err.println("계좌의 잔액이 부족합니다.");
			return;
		}

		account.addMoney(-money);
		transferAcc.addMoney(money);

		System.out.println(account);
		System.out.println("이체완료");
	}

	private Account getAccountByCode(int code) {
		for (int i = 0; i < users.size(); i++) {
			Account account = users.get(i).getAccountByCode(code);
			if (account != null)
				return account;
		}
		return null;
	}

	private void openAccount() {
		User user = users.get(log);
		if (user.getAccountSize() == 3) {
			System.err.println("더 이상 계좌를 개설할 수 없습니다.");
			return;
		}
		System.out.println("====== 계좌신청 ======");

		int code = generateAccountCode();
		user.addAccount(new Account(code));

		System.out.println("계좌신청완료.");
		System.out.println("발급된 계좌번호는 " + code + "입니다.");
	}

	private int generateAccountCode() {
		while (true) {
			int code = ran.nextInt(90000) + 10000;

			boolean isFound = false;
			for (int i = 0; i < users.size(); i++)
				if (users.get(i).getAccountByCode(code) != null)
					isFound = true;

			if (!isFound)
				return code;
		}
	}

	private void closeAccount() {
		System.out.println("====== 계좌철회 ======");
		User user = users.get(log);
		for (int i = 0; i < user.getAccountSize(); i++) {
			int code = user.getAccountByIndex(i).getCode();
			System.out.println(String.format("%d) %d", i + 1, code));
		}

		int sel = (int) input("계좌 선택", NUM) - 1;

		if (sel < 0 || sel >= user.getAccountSize()) {
			System.err.println("입력범위 오류");
			return;
		}

		user.removeAccount(sel);
		System.out.println("계좌철회완료");
	}

	private void save() {
		System.out.println("====== 저장 ======");
		try {
			fileWriter = new FileWriter(file);
			fileWriter.write(dataToString());
			System.out.println("저장완료");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("저장실패");
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String dataToString() {
		int size = users.size();
		String data = size + "\n";
		for (int i = 0; i < size; i++) {
			User user = users.get(i);
			int code = user.getCode();
			String name = user.getName();
			String pw = user.getPassword();
			int accSize = user.getAccountSize();
			data += String.format("%d,%s,%s,%d/", code, name, pw, accSize);
			for (int j = 0; j < accSize; j++) {
				Account account = user.getAccountByIndex(j);
				int accCode = account.getCode();
				int accMoney = account.getMoney();
				data += String.format("%d,%d", accCode, accMoney);
				if (j < accSize - 1)
					data += "/";
			}
			if (i < size - 1)
				data += "\n";

		}
		return data;
	}

	private void load() {
		if (!file.exists()) {
			System.err.println("파일이 존재하지 않습니다.");
			return;
		}
		System.out.println("====== 로드 ======");
		try {
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String data = "";
			while (bufferedReader.ready()) {
				data += bufferedReader.readLine() + "\n";
			}

			stringToData(data);
			log = -1;

			System.out.println("로드성공");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("로드실패");
		}

	}

	private void stringToData(String data) {
		String[] datas = data.split("\n");
		int length = datas.length;
		if (length == 0) {
			System.err.println("로드할 데이터가 없습니다.");
			return;
		}
		try {
			users.clear();
			int size = Integer.parseInt(datas[0]);
			if (length < size + 1)
				size = length - 1;
			for (int i = 1; i <= size; i++) {
				String[] info = datas[i].split("/");
				String[] userInfo = info[0].split(",");
				if (userInfo.length != 4) {
					System.err.println("잘못된 정보라인입니다.");
					continue;
				}
				int code = Integer.parseInt(userInfo[0]);
				String name = userInfo[1];
				String pw = userInfo[2];
				int accSize = Integer.parseInt(userInfo[3]);
				User user = new User(code, name, pw);
				length = info.length;
				if (length < accSize + 1)
					accSize = length - 1;
				for (int j = 1; j <= accSize; j++) {
					String[] accInfo = info[j].split(",");

					int accCode = Integer.parseInt(accInfo[0]);
					Account account = new Account(accCode);

					int accMoney = 0;
					if (accInfo.length > 1)
						accMoney = Integer.parseInt(accInfo[1]);
					account.addMoney(accMoney);

					user.addAccount(account);
				}
				users.add(user);
			}
		} catch (Exception e) {
			System.err.println("데이터 변환 실패");
		}
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
