// https://www.codetree.ai/cote/14/problems/best-place-of-13/description

import java.io.*;
import java.util.*;

public class Main {
	static int n;
	static int[][] grid;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		n = Integer.parseInt(br.readLine());
		grid = new int[n][n];

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				grid[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int maxCount = 0;
		for (int i = 0; i < n; i++) {
			// 격자를 벗어나지 않을 범위로만 잡는다.
			for (int j = 0; j < n - 2; j++) {
				int count = grid[i][j] + grid[i][j + 1] + grid[i][j + 2];
				maxCount = Math.max(maxCount, count);
			}
		}
		System.out.println(maxCount);
	}
}