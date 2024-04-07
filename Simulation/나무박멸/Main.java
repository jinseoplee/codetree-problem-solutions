import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 20;
	static final int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static final int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	static int n, m, k, c;
	static int ans;
	static int[][] tree = new int[MAX_N][MAX_N];
	static int[][] addTree = new int[MAX_N][MAX_N];
	static int[][] herbicide = new int[MAX_N][MAX_N];

	public static void main(String[] args) throws Exception {
		init();

		while (m-- > 0) {
			// 1. 나무의 성장
			stepOne();

			// 2. 나무의 번식
			stepTwo();

			// 제초제의 기간을 1년 감소시킨다.
			deleteHerbicide();

			// 3. 제초제 작업 진행
			stepThree();
		}

		System.out.println(ans);
	}

	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		for (int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				tree[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}

	static boolean isOutRange(int r, int c) {
		return r < 0 || r == n || c < 0 || c == n;
	}

	static void deleteHerbicide() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (herbicide[i][j] > 0) --herbicide[i][j];
			}
		}
	}

	static void stepOne() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tree[i][j] <= 0) continue;

				int count = 0;
				for (int dir = 0; dir < 4; dir++) {
					int nextR = i + dr[dir];
					int nextC = j + dc[dir];
					if (isOutRange(nextR, nextC)) continue;
					if (tree[nextR][nextC] > 0) count++;
				}

				tree[i][j] += count;
			}
		}
	}

	static void stepTwo() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				addTree[i][j] = 0;
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tree[i][j] <= 0) continue;

				int count = 0;
				for (int dir = 0; dir < 4; dir++) {
					int nextR = i + dr[dir];
					int nextC = j + dc[dir];
					if (isOutRange(nextR, nextC)) continue;
					if (herbicide[nextR][nextC] > 0) continue;
					if (tree[nextR][nextC] == 0) count++;
				}

				for (int dir = 0; dir < 4; dir++) {
					int nextR = i + dr[dir];
					int nextC = j + dc[dir];
					if (isOutRange(nextR, nextC)) continue;
					if (herbicide[nextR][nextC] > 0) continue;
					if (tree[nextR][nextC] == 0) addTree[nextR][nextC] += tree[i][j] / count;
				}
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				tree[i][j] += addTree[i][j];
			}
		}
	}

	static void stepThree() {
		int maxCount = 0;
		int maxR = 0;
		int maxC = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (tree[i][j] <= 0) continue;

				int count = tree[i][j];
				for (int dir = 4; dir < 8; dir++) {
					int nextR = i;
					int nextC = j;
					for (int t = 0; t < k; t++) {
						nextR += dr[dir];
						nextC += dc[dir];
						if (isOutRange(nextR, nextC)) break;
						if (tree[nextR][nextC] <= 0) break;
						count += tree[nextR][nextC];
					}
				}

				if (count > maxCount) {
					maxCount = count;
					maxR = i;
					maxC = j;
				}
			}
		}

		ans += maxCount;

		if (tree[maxR][maxC] > 0) {
			tree[maxR][maxC] = 0;
			herbicide[maxR][maxC] = c;
			for (int dir = 4; dir < 8; dir++) {
				int nextR = maxR;
				int nextC = maxC;
				for (int t = 1; t <= k; t++) {
					nextR += dr[dir];
					nextC += dc[dir];
					if (isOutRange(nextR, nextC)) break;
					if (tree[nextR][nextC] < 0) break;
					if (tree[nextR][nextC] == 0) {
						herbicide[nextR][nextC] = c; 
						break;
					}
					tree[nextR][nextC] = 0;
					herbicide[nextR][nextC] = c;
				}
			}
		}
	}
}