// https://www.codetree.ai/cote/14/problems/sum-close-to-particular-number/description

import java.io.*;
import java.util.*;

public class Main {
	static int n, s, arrSum;
	static int[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());

		arr = new int[n];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			arrSum += arr[i];
		}

		int ans = Integer.MAX_VALUE;

		// 두 개의 수를 고른다.
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				// i번과 j번 수를 제외한 합을 구한다.
				int newSum = arrSum - (arr[i] + arr[j]);

				int diff = Math.abs(s - newSum);
				ans = Math.min(ans, diff);
			}
		}
		System.out.println(ans);
	}
}