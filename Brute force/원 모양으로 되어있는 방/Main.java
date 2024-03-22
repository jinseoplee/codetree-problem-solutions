// https://www.codetree.ai/cote/14/problems/a-room-in-a-circle/description

import java.io.*;

public class Main {
	static int n;
	static int[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		n = Integer.parseInt(br.readLine());
		arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}

		// i번째 방에서 출발했을 경우 결과를 구한다.
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			int sumOfDistance = 0;
			int distance = 0;
			for (int j = 0; j < n; j++) {
				sumOfDistance += distance * arr[(i + j) % n];
				distance++;
			}
			ans = Math.min(ans, sumOfDistance);
		}
		System.out.println(ans);
	}
}