// https://www.codetree.ai/cote/14/problems/on-the-checkboard-2/description

import java.io.*;
import java.util.*;

public class Main {
	static int n, m;
	static char[][] grid;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		grid = new char[n][m];
		for (int i = 0; i < n; i++) {
			String[] input = br.readLine().split(" ");
			for (int j = 0; j < m; j++) {
				grid[i][j] = input[j].charAt(0);
			}
		}

		int count = 0;
		for (int i = 1; i < n - 2; i++) {
			for (int j = 1; j < m - 2; j++) {
				for (int k = i + 1; k < n - 1; k++) {
					for (int l = j + 1; l < m - 1; l++) {
						// 색깔이 전부 달라지는 경우에만 개수를 센다.
						if (grid[0][0] != grid[i][j] && 
							grid[i][j] != grid[k][l] && 
							grid[k][l] != grid[n - 1][m - 1])
							count++;
					}
				}
			}
		}
		System.out.println(count);
	}
}