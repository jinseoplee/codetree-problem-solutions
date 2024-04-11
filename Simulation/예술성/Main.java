import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 29;
	static final int DIR_NUM = 4;

	// 상, 하, 좌, 우
	static final int[] dx = { -1, 1, 0, 0 };
	static final int[] dy = { 0, 0, -1, 1 };

	static int n;
	static int groupN;

	static int[][] grid = new int[MAX_N][MAX_N];
	static int[][] nextGrid = new int[MAX_N][MAX_N];
	static int[][] group = new int[MAX_N][MAX_N];
	static int[] groupCnt = new int[MAX_N * MAX_N + 1];
	static boolean[][] visited = new boolean[MAX_N][MAX_N];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		n = Integer.parseInt(br.readLine());

		// grid 초기화
		for (int x = 0; x < n; x++) {
			st = new StringTokenizer(br.readLine());
			for (int y = 0; y < n; y++) {
				grid[x][y] = Integer.parseInt(st.nextToken());
			}
		}

		// 3회전까지의 예술 점수를 더한다.
		int ans = 0;
		for (int i = 0; i < 4; i++) {
			ans += getScore();
			rotate();
		}
		System.out.println(ans);
	}

	static int getScore() {
		groupN = 0;

		// 1. 그룹을 만든다.
		makeGroup();

		// 2. 예술 점수의 합을 구한다.
		return getArtScore();
	}

	static void makeGroup() {
		// visited 배열을 초기화한다.
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				visited[x][y] = false;
			}
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (!visited[x][y]) {
					visited[x][y] = true;
					groupN += 1;
					group[x][y] = groupN;
					groupCnt[groupN] = 1;
					dfs(x, y);
				}
			}
		}
	}

	static void dfs(int x, int y) {
		for (int dir = 0; dir < DIR_NUM; dir++) {
			int nx = x + dx[dir];
			int ny = y + dy[dir];
			if (canGo(nx, ny) && grid[x][y] == grid[nx][ny]) {
				visited[nx][ny] = true;
				group[nx][ny] = groupN;
				groupCnt[groupN]++;
				dfs(nx, ny);
			}
		}
	}

	static int getArtScore() {
		int artScore = 0;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				for (int dir = 0; dir < DIR_NUM; dir++) {
					int nx = x + dx[dir];
					int ny = y + dy[dir];
					if (inRange(nx, ny) && grid[x][y] != grid[nx][ny]) {
						int g1 = group[x][y], g2 = group[nx][ny];
						int cnt1 = groupCnt[g1], cnt2 = groupCnt[g2];
						int num1 = grid[x][y], num2 = grid[nx][ny];
						artScore += (cnt1 + cnt2) * num1 * num2;
					}
				}
			}
		}
		return artScore / 2;
	}

	static void rotate() {
		// nextGrid 초기화
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				nextGrid[x][y] = 0;
			}
		}

		// 십자 모양 반시계 방향 회전
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (y == n / 2) {
					nextGrid[y][x] = grid[x][y];
				}

				if (x == n / 2) {
					nextGrid[n - y - 1][x] = grid[x][y];
				}
			}
		}

		// 4개의 사각형 시계 방향 회전
		int half = n / 2;
		rotateSquare(0, 0, half);
		rotateSquare(0, half + 1, half);
		rotateSquare(half + 1, 0, half);
		rotateSquare(half + 1, half + 1, half);

		// grid 생신
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				grid[x][y] = nextGrid[x][y];
			}
		}
	}

	static void rotateSquare(int sx, int sy, int half) {
		for (int x = sx; x < sx + half; x++) {
			for (int y = sy; y < sy + half; y++) {
				int ox = x - sx, oy = y - sy;
				int rx = oy, ry = half - ox - 1;
				nextGrid[sx + rx][sy + ry] = grid[x][y];
			}
		}
	}

	static boolean canGo(int x, int y) {
		return inRange(x, y) && !visited[x][y];
	}

	static boolean inRange(int x, int y) {
		return 0 <= x && x < n && 0 <= y && y < n;
	}
}