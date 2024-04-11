import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos {
	int x, y;

	Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

public class Main {
	static final Pos NOT_EXIST = new Pos(-1, -1);
	static final int MAX_N = 20;
	static final int DIR_NUM = 4;
	static final int[] dx = { -1, 1, 0, 0 };
	static final int[] dy = { 0, 0, -1, 1 };

	// 로봇의 정보
	static Pos robot = new Pos(-1, -1);
	static int robotLevel = 2;
	static int removeCnt;

	static int n;
	static int elapsedTime;
	static int[][] map = new int[MAX_N][MAX_N];
	static int[][] step = new int[MAX_N][MAX_N];
	static boolean[][] visited = new boolean[MAX_N][MAX_N];
	static Queue<Pos> bfsQ = new ArrayDeque<>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		n = Integer.parseInt(br.readLine());

		for (int x = 0; x < n; x++) {
			st = new StringTokenizer(br.readLine());
			for (int y = 0; y < n; y++) {
				map[x][y] = Integer.parseInt(st.nextToken());

				// 로봇인 경우
				if (map[x][y] == 9) {
					robot = new Pos(x, y);
					map[x][y] = 0;
				}
			}
		}

		while (true) {
			boolean isMoved = moveRobot();
			if (!isMoved) break;
		}

		System.out.println(elapsedTime);
	}

	static boolean moveRobot() {
		// 방문 배열 초기화
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				visited[x][y] = false;
			}
		}

		// step 갱신
		bfsQ.offer(robot);
		visited[robot.x][robot.y] = true;
		step[robot.x][robot.y] = 0;
		bfs();

		// 규칙에 따라 없앨 수 있는 몬스터를 찾는다.
		Pos bestPos = NOT_EXIST;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (!visited[x][y] || map[x][y] == 0 || map[x][y] == robotLevel)
					continue;

				Pos newPos = new Pos(x, y);
				if (needUpdate(bestPos, newPos)) {
					bestPos = newPos;
				}
			}
		}

		// 이동할 수 있는 경우
		if (bestPos != NOT_EXIST) {
			robot.x = bestPos.x;
			robot.y = bestPos.y;
			elapsedTime += step[robot.x][robot.y];
			map[robot.x][robot.y] = 0;
			removeCnt++;

			// 몬스터 제거 횟수가 레벨이랑 같아질 경우
			if (removeCnt == robotLevel) {
				robotLevel++;
				removeCnt = 0;
			}

			return true;
		}
		return false;
	}

	static boolean needUpdate(Pos bestPos, Pos newPos) {
		if (bestPos == NOT_EXIST) {
			return true;
		}

		// 거리, 행, 열 비교
		if (step[bestPos.x][bestPos.y] != step[newPos.x][newPos.y]) {
			return step[bestPos.x][bestPos.y] > step[newPos.x][newPos.y];
		}
		if (bestPos.x != newPos.x) {
			return bestPos.x > newPos.x;
		}
		return bestPos.y > newPos.y;
	}

	static void bfs() {
		while (!bfsQ.isEmpty()) {
			Pos curr = bfsQ.poll();
			for (int dir = 0; dir < DIR_NUM; dir++) {
				int nx = curr.x + dx[dir];
				int ny = curr.y + dy[dir];
				if (canGo(nx, ny)) {
					bfsQ.offer(new Pos(nx, ny));
					visited[nx][ny] = true;
					step[nx][ny] = step[curr.x][curr.y] + 1;
				}
			}
		}
	}

	static boolean canGo(int x, int y) {
		return (0 <= x && x < n && 0 <= y && y < n) && !visited[x][y] && map[x][y] <= robotLevel;
	}
}