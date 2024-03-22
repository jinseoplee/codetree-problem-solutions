// https://www.codetree.ai/cote/14/problems/taking-a-taxi-in-the-middle-of-the-marathon-2/description

import java.io.*;
import java.util.*;

public class Main {
	static int n;
	static int[][] coordinates;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		n = Integer.parseInt(br.readLine());
		coordinates = new int[n][2];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			coordinates[i][0] = x;
			coordinates[i][1] = y;
		}

		// 각 i번째 체크포인트를 건너 뛰었을 때 거리를 구한다.
		int ans = Integer.MAX_VALUE;
		for (int i = 1; i < n - 1; i++) {
			int distance = 0;
			int curr = 0;
			for (int j = 1; j < n; j++) {
				if (i != j) {
					distance += Math.abs(coordinates[curr][0] - coordinates[j][0])
							+ Math.abs(coordinates[curr][1] - coordinates[j][1]);
					curr = j;
				}

			}
			ans = Math.min(ans, distance);
		}
		System.out.println(ans);
	}
}