import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Atom {
	int x, y, m, s, d;

	Atom(int x, int y, int m, int s, int d) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.s = s;
		this.d = d;
	}
}

public class Main {
	static int n, m, k;
	static ArrayList<Atom>[][] grid, nextGrid;

	static int[] dx = { -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		grid = new ArrayList[n][n];
		nextGrid = new ArrayList[n][n];

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				grid[x][y] = new ArrayList<>();
				nextGrid[x][y] = new ArrayList<>();
			}
		}

		for (int i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			grid[x - 1][y - 1].add(new Atom(x, y, m, s, d));
		}

		while (k-- > 0) {
			simulate();
		}

		int ans = 0;
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				for (int i = 0; i < grid[x][y].size(); i++) {
					ans += grid[x][y].get(i).m;
				}
			}
		}
		System.out.println(ans);
	}

	static void simulate() {
		// 1. 원자들을 이동시킨다.
		moveAll();

		// 2. 원자의 합성이 일어난다.
		synthesize();
	}

	static void moveAll() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				nextGrid[x][y] = new ArrayList<>();
			}
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				for (int i = 0; i < grid[x][y].size(); i++) {
					Atom atom = grid[x][y].get(i);
					int nextX = (x + atom.s * dx[atom.d] + n * atom.s) % n;
					int nextY = (y + atom.s * dy[atom.d] + n * atom.s) % n;
					nextGrid[nextX][nextY].add(new Atom(x, y, atom.m, atom.s, atom.d));
				}
			}
		}
	}

	static void synthesize() {
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				grid[x][y] = new ArrayList<>();
			}
		}

		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				int atomCount = nextGrid[x][y].size();
				if (atomCount == 1) {
					grid[x][y].add(nextGrid[x][y].get(0));
				} else if (atomCount >= 2) {
					split(x, y);
				}
			}
		}
	}

	static void split(int x, int y) {
		int sumOfMass = 0;
		int sumOfSpeed = 0;
		int atomCount = nextGrid[x][y].size();
		int startDir = 1;
		int[] dirType = new int[2];

		for (int i = 0; i < atomCount; i++) {
			Atom atom = nextGrid[x][y].get(i);
			sumOfMass += atom.m;
			sumOfSpeed += atom.s;
			dirType[atom.d % 2]++;
		}

		if (dirType[0] == 0 || dirType[1] == 0) {
			startDir = 0;
		}

		if (sumOfMass / 5 != 0) {
			for (int dir = startDir; dir < 8; dir += 2) {
				grid[x][y].add(new Atom(x, y, sumOfMass / 5, sumOfSpeed / atomCount, dir));
			}
		}
	}
}