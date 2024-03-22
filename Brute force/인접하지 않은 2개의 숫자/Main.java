// https://www.codetree.ai/cote/14/problems/two-non-adjacent-numbers/description

import java.io.*;
import java.util.*;

public class Main {
	static int n;
	static int[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		n = Integer.parseInt(br.readLine());
		arr = new int[n];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		// 인접하지 않은 두 숫자의 합 중 최댓값을 구한다.
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 2; j < n; j++) {
				ans = Math.max(ans, arr[i] + arr[j]);
			}
		}
		System.out.println(ans);
	}
}
