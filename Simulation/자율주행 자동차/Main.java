import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n, m, area;
	static int currX, currY, currDir;
	static int[][] map;

	static int[] dx = { -1, 0, 1, 0 };
	static int[] dy = { 0, 1, 0, -1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine());
		currX = Integer.parseInt(st.nextToken());
		currY = Integer.parseInt(st.nextToken());
		currDir = Integer.parseInt(st.nextToken());

		map = new int[n][m];

		for (int x = 0; x < n; x++) {
			st = new StringTokenizer(br.readLine());
			for (int y = 0; y < m; y++) {
				map[x][y] = Integer.parseInt(st.nextToken());
			}
		}

		simulate();

		System.out.println(area);
	}

	static void simulate() {
		map[currX][currY] = -1;
		area++;

		while (true) {
			int turnCount = 0;
			for (int i = 1; i <= 4; i++) {
				int leftDir = (currDir - i + 4) % 4;
				int nextX = currX + dx[leftDir];
				int nextY = currY + dy[leftDir];
				if (map[nextX][nextY] == 0) {
					currDir = leftDir;
					currX = nextX;
					currY = nextY;
					map[currX][currY] = -1;
					area++;
					break;
				}
				turnCount++;
			}

			if (turnCount == 4) {
				int backX = currX - dx[currDir];
				int backY = currY - dy[currDir];
				if (map[backX][backY] == 1) {
					break;
				}
				currX = backX;
				currY = backY;
			}
		}
	}
}