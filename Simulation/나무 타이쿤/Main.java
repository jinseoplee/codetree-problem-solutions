import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Fertilizer {
	int r, c;

	Fertilizer(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

public class Main {
	static int n, m;
	static int[][] height;
	static boolean[][] visited;
	static List<Fertilizer> fertilizers = new ArrayList<>();

	static final int[] dr = { 0, 0, -1, -1, -1, 0, 1, 1, 1 };
	static final int[] dc = { 0, 1, 1, 0, -1, -1, -1, 0, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		height = new int[n][n];
		visited = new boolean[n][n];

		for (int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < n; c++) {
				height[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		for (int r = n - 2; r < n; r++) {
			for (int c = 0; c < 2; c++) {
				fertilizers.add(new Fertilizer(r, c));
			}
		}

		while (m-- > 0) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken());
			int p = Integer.parseInt(st.nextToken());
			simulate(d, p);
		}

		int ans = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				ans += height[r][c];
			}
		}
		System.out.println(ans);
	}

	static void simulate(int d, int p) {
		// 1. 이동 규칙에 따라 영양제를 이동시킨다.
		move(d, p);

		// 2. 영양제가 있는 땅의 리브로수 높이를 증가시킨다.
		grow();

		// 3. 높이가 2 이상인 리브로수를 높이 2만큼 자르고 영양제를 준다.
		cut();
	}

	static void move(int d, int p) {
		int size = fertilizers.size();
		for (int i = size - 1; i >= 0; i--) {
			Fertilizer fertilizer = fertilizers.get(i);
			int nextR = (n + (fertilizer.r + (dr[d] * p))) % n;
			int nextC = (n + (fertilizer.c + (dc[d] * p))) % n;
			fertilizers.add(new Fertilizer(nextR, nextC));
			fertilizers.remove(i);
		}
	}

	static void grow() {
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				visited[r][c] = false;
			}
		}

		for (Fertilizer fertilizer : fertilizers) {
			height[fertilizer.r][fertilizer.c]++;
			visited[fertilizer.r][fertilizer.c] = true;
		}

		for (Fertilizer fertilizer : fertilizers) {
			int count = 0;
			for (int dir = 2; dir <= 8; dir += 2) {
				int nextR = fertilizer.r + dr[dir];
				int nextC = fertilizer.c + dc[dir];
				if (inRange(nextR, nextC) && height[nextR][nextC] >= 1) {
					count++;
				}
			}
			height[fertilizer.r][fertilizer.c] += count;
		}

		fertilizers.clear();
	}

	static boolean inRange(int r, int c) {
		return 0 <= r && r < n && 0 <= c && c < n;
	}

	static void cut() {
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (height[r][c] >= 2 && !visited[r][c]) {
					height[r][c] -= 2;
					fertilizers.add(new Fertilizer(r, c));
				}
			}
		}
	}
}