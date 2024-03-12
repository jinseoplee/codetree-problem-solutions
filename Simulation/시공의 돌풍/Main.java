import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static final int WIND = -1;
	static final int DIR_NUM = 4;
	static final int[] dr = { -1, 1, 0, 0 };
	static final int[] dc = { 0, 0, -1, 1 };

	static int n, m, t;
	static int[][] dust, nextDust;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		t = Integer.parseInt(st.nextToken());

		dust = new int[n][m];
		nextDust = new int[n][m];

		for (int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < m; c++) {
				dust[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		while (t-- > 0) {
			simulate();
		}

		int ans = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (dust[r][c] != WIND) {
					ans += dust[r][c];
				}
			}
		}
		System.out.println(ans);
	}

	static void simulate() {
		// 1. 먼지가 인접한 4방향으로 확산한다.
		spreadAll();

		// 2. 돌풍이 청소를 시작한다.
		clean();
	}

	static void spreadAll() {
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				nextDust[r][c] = 0;
			}
		}

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (dust[r][c] != WIND) {
					spread(r, c);
				}
			}
		}

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < m; c++) {
				if (dust[r][c] != WIND) {
					dust[r][c] = nextDust[r][c];
				}
			}
		}
	}

	static void spread(int r, int c) {
		int volume = 0;
		for (int dir = 0; dir < DIR_NUM; dir++) {
			int nextR = r + dr[dir];
			int nextC = c + dc[dir];
			if (canGo(nextR, nextC)) {
				nextDust[nextR][nextC] += dust[r][c] / 5;
				volume += dust[r][c] / 5;
			}
		}
		nextDust[r][c] += dust[r][c] - volume;
	}

	static boolean canGo(int r, int c) {
		return (0 <= r && r < n && 0 <= c && c < m) && dust[r][c] != WIND;
	}

	static void clean() {
		ArrayList<Integer> windRowLocation = new ArrayList<>();
		for (int r = 0; r < n; r++) {
			if (dust[r][0] == WIND) {
				windRowLocation.add(r);
			}
		}

		cleanCounterclockwise(windRowLocation.get(0), 0, 0, m - 1);
		cleanClockwise(windRowLocation.get(1), 0, n - 1, m - 1);
	}

	// 반시계 방향으로 청소한다.
	static void cleanCounterclockwise(int startRow, int startCol, int endRow, int endCol) {
		for (int r = startRow; r > 0; r--) {
			dust[r][startCol] = dust[r - 1][startCol];
		}
		for (int c = startCol; c < endCol; c++) {
			dust[endRow][c] = dust[endRow][c + 1];
		}
		for (int r = endRow; r < startRow; r++) {
			dust[r][endCol] = dust[r + 1][endCol];
		}
		for (int c = endCol; c > startCol; c--) {
			dust[startRow][c] = dust[startRow][c - 1];
		}
		dust[startRow][0] = WIND;
		dust[startRow][1] = 0;
	}

	// 시계 방향으로 청소한다.
	static void cleanClockwise(int startRow, int startCol, int endRow, int endCol) {
		for (int r = startRow; r < endRow; r++) {
			dust[r][startCol] = dust[r + 1][startCol];
		}
		for (int c = startCol; c < endCol; c++) {
			dust[endRow][c] = dust[endRow][c + 1];
		}
		for (int r = endRow; r > startRow; r--) {
			dust[r][endCol] = dust[r - 1][endCol];
		}
		for (int c = endCol; c > startCol; c--) {
			dust[startRow][c] = dust[startRow][c - 1];
		}
		dust[startRow][0] = WIND;
		dust[startRow][1] = 0;
	}
}