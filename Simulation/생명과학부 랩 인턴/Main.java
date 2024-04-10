import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Mold {
	int dist, dir, size;

	Mold(int dist, int dir, int size) {
		this.dist = dist;
		this.dir = dir;
		this.size = size;
	}

	boolean isSmaller(int size) {
		return this.size < size;
	}
}

public class Main {
	static final Mold EMPTY = new Mold(-1, -1, -1);
	static final int MAX_N = 100;
	static final int MAX_M = 100;
	static final int DIR_NUM = 4;

	static final int[] dx = { -1, 1, 0, 0 };
	static final int[] dy = { 0, 0, 1, -1 };

	static int n, m, k;
	static int ans;

	static Mold[][] map = new Mold[MAX_N][MAX_M];
	static Mold[][] nextMap = new Mold[MAX_N][MAX_M];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		// 빈 공간 초기화
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				map[x][y] = EMPTY;
			}
		}

		// 곰팡이 정보 입력 처리
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			int dir = Integer.parseInt(st.nextToken());
			int size = Integer.parseInt(st.nextToken());

			// 이동 방향이 상, 하인 경우
			if (dir <= 2) {
				dist %= (2 * n - 2);
			}
			// 이동 방향이 우, 좌인 경우
			else {
				dist %= (2 * m - 2);
			}

			map[x - 1][y - 1] = new Mold(dist, dir - 1, size);
		}

		// 첫 번째 열부터 채취를 시작한다.
		for (int y = 0; y < m; y++) {
			// 곰팡이를 수집한다.
			collect(y);

			// 곰팡이가 이동을 시작한다.
			moveAll();
		}

		System.out.println(ans);
	}

	static void collect(int y) {
		for (int x = 0; x < n; x++) {
			if (map[x][y] != EMPTY) {
				ans += map[x][y].size;
				map[x][y] = EMPTY;
				break;
			}
		}
	}

	static void moveAll() {
		// nextMap 초기화
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				nextMap[x][y] = EMPTY;
			}
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				if (map[x][y] != EMPTY) {
					move(x, y);
				}
			}
		}

		// map 갱신
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				map[x][y] = nextMap[x][y];
			}
		}
	}

	static void move(int x, int y) {
		int dist = map[x][y].dist;
		int dir = map[x][y].dir;
		int size = map[x][y].size;

		int nx = x;
		int ny = y;
		while (dist-- > 0) {
			nx += dx[dir];
			ny += dy[dir];

			// 다음 위치가 벽인 경우
			if (isOutRange(nx, ny)) {
				dir = (dir % 2 == 0) ? dir + 1 : dir - 1;
				nx += dx[dir];
				ny += dy[dir];
				dist++;
			}
		}
		if (nextMap[nx][ny].isSmaller(size)) {
			nextMap[nx][ny] = new Mold(map[x][y].dist, dir, map[x][y].size);
		}
	}

	static boolean isOutRange(int x, int y) {
		return x < 0 || x >= n || y < 0 || y >= m;
	}
}