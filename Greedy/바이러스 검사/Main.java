import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n;
	static int[] customers;
	static int leaderCapacity, memberCapacity;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		customers = new int[n];

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			customers[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		leaderCapacity = Integer.parseInt(st.nextToken()); // 검사 팀장이 검사할 수 있는 최대 고객 수
		memberCapacity = Integer.parseInt(st.nextToken()); // 검사 팀원이 검사할 수 있는 최대 고객 수

		long minCount = 0; // 검사자의 최소의 수
		for (int i = 0; i < n; i++) {
			// 검사 팀장은 꼭 한 명 필요하다.
			minCount++;
			// 필요한 검사 팀원을 더한다.
			minCount += requiredMemberNum(customers[i] - leaderCapacity);
		}
		System.out.println(minCount);
	}

	static int requiredMemberNum(int customerNum) {
		// 남은 고객이 없다면 검사 팀원은 필요 없다.
		if (customerNum <= 0) {
			return 0;
		}

		// 정확히 나누어떨어지는 경우 몫만큼의 검사 팀원이 필요하다.
		if (customerNum % memberCapacity == 0) {
			return customerNum / memberCapacity;
		}
		// 정확히 나누어떨어지지 않는 경우 1명이 추가적으로 더 필요하다.
		return customerNum / memberCapacity + 1;
	}
}