// https://www.codetree.ai/cote/14/problems/find-hidden-words-2/description

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n, m;
	static char[][] arr;

	static int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		arr = new char[n][];
		for (int i = 0; i < n; i++) {
			arr[i] = br.readLine().toCharArray();
		}

		// 모든 좌표를 확인한다.
		int ans = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (arr[i][j] != 'L')
					continue;
				for (int dir = 0; dir < 8; dir++) {
					int count = 0;
					int nextR = i;
					int nextC = j;
					for (int k = 1; k <= 2; k++) {
						nextR += dr[dir];
						nextC += dc[dir];
						if (nextR < 0 || nextR == n || nextC < 0 || nextC == m) break;
						if (arr[nextR][nextC] != 'E') break;
						count++;
					}
					if (count == 2) ans++;
				}
			}
		}
		System.out.println(ans);
	}
}