import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Pos {
	int r, c;

	Pos(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

public class Main {
	static final int MAX_N = 10;
	static final int MAX_M = 10;

	static final int[] dr = { -1, 1, 0, 0 };
	static final int[] dc = { 0, 0, -1, 1 };

	static int n, m, k;
	static int ans;
	static int sr, sc, sSize;
	static int[][] map = new int[MAX_N + 1][MAX_N + 1];
	static int[][] nextMap = new int[MAX_N + 1][MAX_N + 1];

	static Pos[] players = new Pos[MAX_M + 1];
	static Pos exit = new Pos(0, 0);

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력 처리
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		for (int i = 1; i <= n; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			players[i] = new Pos(r, c);
		}

		st = new StringTokenizer(br.readLine());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		exit.r = r;
		exit.c = c;

		while (k-- > 0) {
			// 모든 참가자들이 이동한다.
			moveAll();

			// 모든 참가자들이 미로를 탈출했는지 확인한다.
			boolean isAllEscaped = true;
			for (int i = 1; i <= m; i++) {
				Pos p = players[i];
				if (p.r != exit.r || p.c != exit.c) isAllEscaped = false;
			}

			// 모든 참가자들이 탈출했을 경우 종료한다,
			if (isAllEscaped) break;

			// 가장 작은 정사각형을 찾는다.
			findMinSquare();

			// 가장 작은 정사각형을 회전시킨다.
			rotateSquare();

			// 참가자들과 출구를 회전시킨다.
			rotatePlayerAndExit();
		}

		System.out.println(ans);
		System.out.println(exit.r + " " + exit.c);
	}

	static void moveAll() {
		for (int i = 1; i <= m; i++) {
			// 이미 출구에 도착한 참가자는 제외한다.
			if (players[i].r == exit.r && players[i].c == exit.c) continue;

			// 상하로 이동
			if (players[i].r != exit.r) {
				int nr = players[i].r;
				int nc = players[i].c;

				if (nr > exit.r) nr--;
				else nr++;

				if (map[nr][nc] == 0) {
					players[i].r = nr;
					players[i].c = nc;
					ans++;
					continue;
				}
			}

			// 좌우로 이동
			if (players[i].c != exit.c) {
				int nr = players[i].r;
				int nc = players[i].c;

				if (nc > exit.c) nc--;
				else nc++;

				if (map[nr][nc] == 0) {
					players[i].r = nr;
					players[i].c = nc;
					ans++;
				}
			}
		}
	}

	static void findMinSquare() {
		for (int size = 2; size <= n; size++) {
			for (int r = 1; r <= n - size + 1; r++) {
				for (int c = 1; c <= n - size + 1; c++) {
					int r2 = r + size - 1;
					int c2 = c + size - 1;

					// 출구가 정사각형에 없는 경우 스킵한다.
					if (exit.r < r || exit.r > r2 || exit.c < c || exit.c > c2) continue;

					// 참가자가 있는지 확인한다.
					boolean isPlayerIn = false;
					for (int i = 1; i <= m; i++) {
						if (r <= players[i].r && players[i].r <= r2 && c <= players[i].c && players[i].c <= c2) {
							if (players[i].r != exit.r || players[i].c != exit.c) {
								isPlayerIn = true;
								break;
							}
						}
					}

					if (isPlayerIn) {
						sr = r;
						sc = c;
						sSize = size;
						return;
					}
				}
			}
		}
	}

	static void rotateSquare() {
		// 벽의 내구도를 감소시킨다.
		for (int i = sr; i < sr + sSize; i++) {
			for (int j = sc; j < sc + sSize; j++) {
				if (map[i][j] > 0) map[i][j]--;
			}
		}

		// 정사각형을 시계 방향으로 90도 회전시킨다.
		for (int i = sr; i < sr + sSize; i++) {
			for (int j = sc; j < sc + sSize; j++) {
				int or = i - sr, oy = j - sc;
				int rr = oy, rc = sSize - or - 1;
				nextMap[sr + rr][sc + rc] = map[i][j];
			}
		}

		// map 갱신
		for (int i = sr; i < sr + sSize; i++) {
			for (int j = sc; j < sc + sSize; j++) {
				map[i][j] = nextMap[i][j];
			}
		}
	}

	static void rotatePlayerAndExit() {
		// m명의 참가자를 모두 확인한다.
		for (int i = 1; i <= m; i++) {
			int r = players[i].r;
			int c = players[i].c;

			// 정사각형 범위에 없으면 스킵한다.
			if (r < sr || r >= sr + sSize || c < sc || c >= sc + sSize) continue;

			int or = r - sr, oc = c - sc;
			int rr = oc, rc = sSize - or - 1;
			players[i].r = sr + rr;
			players[i].c = sc + rc;
		}

		// 출구도 회전을 시켜준다.
		int r = exit.r;
		int c = exit.c;
		int or = r - sr, oc = c - sc;
		int rr = oc, rc = sSize - or - 1;
		exit.r = sr + rr;
		exit.c = sc + rc;
	}
}