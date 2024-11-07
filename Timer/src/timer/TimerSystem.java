package timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class TimerSystem {
	private static Scanner scan = new Scanner(System.in);

	private static long remainingTime;

	private TimerSystem() {
	}

	private static TimerSystem instance = new TimerSystem();

	public static void run() {
		setTimer();
		runTimer();
	}

	private static void setTimer() {
		System.out.println("타이머 설정>>");
		int minute = inputNum("분");
		int second = inputNum("초");
		remainingTime = (minute * 60 + second) * 1000;
	}

	private static void runTimer() {
		long currentTime = 0;
		long timeElapsed = 0;
		int ticksPerSecond = 1000;
		SimpleDateFormat sdf = new SimpleDateFormat("HH시 mm분 ss초");

		Calendar cal = Calendar.getInstance();
		long lastTime = cal.getTimeInMillis() - ticksPerSecond;
		while (remainingTime > 0) {
			cal = Calendar.getInstance();
			currentTime = cal.getTimeInMillis();
			if (currentTime - lastTime >= ticksPerSecond) {
				String curTime = sdf.format(cal.getTime());
				String timeStr = String.format("%s(남은 시간 : %d초)", curTime, remainingTime / ticksPerSecond);
				System.out.println(timeStr);
				timeElapsed = currentTime - lastTime;
				remainingTime -= timeElapsed;
				lastTime = currentTime;
			}
		}
		System.out.println("TimeOut!!!");
	}

	private static int inputNum(String msg) {
		System.out.print(msg + " : ");
		String input = scan.nextLine();

		try {
			int num = Integer.parseInt(input);
			return num;
		} catch (Exception e) {
			System.err.println("숫자만 입력가능");
		}
		return 0;
	}
}
