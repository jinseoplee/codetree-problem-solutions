import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10;
	static final int DIR_NUM = 8;
	static final int[] dr = { -1, 1, 0, 0, -1, -1, 1, 1 };
	static final int[] dc = { 0, 0, -1, 1, -1, 1, -1, 1 };

	static int n, m, k;

	static int[][] food = new int[MAX_N][MAX_N];
	static int[][] nextFood = new int[MAX_N][MAX_N];
	static int[][] givenFood = new int[MAX_N][MAX_N];

	static ArrayList<Integer>[][] virus = new ArrayList[MAX_N][MAX_N];
	static ArrayList<Integer>[][] nextVirus = new ArrayList[MAX_N][MAX_N];

	public static void main(String[] args) throws Exception {
		input();
		simulate();
		output();
	}

	static void simulate() {
		while (k-- > 0) {
			// 1. 양분을 섭취한다.
			eat();

			// 2. 번식을 진행한다.
			breedAll();

			// 3. 주어진 양분을 추가한다.
			addFood();
		}
	}

	static void eat() {
		// 초기화
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				nextFood[r][c] = 0;
				nextVirus[r][c] = new ArrayList<>();
			}
		}

		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				Collections.sort(virus[r][c]);
				for (int i = 0; i < virus[r][c].size(); i++) {
					int age = virus[r][c].get(i);

					// 양분이 충분한 경우
					if (food[r][c] >= age) {
						food[r][c] -= age;
						nextVirus[r][c].add(age + 1);
					}
					// 양분이 부족한 경우
					else {
						nextFood[r][c] += age / 2;
					}
				}

				food[r][c] += nextFood[r][c];
			}
		}
	}

	static void breedAll() {
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				for (int i = 0; i < nextVirus[r][c].size(); i++) {
					int age = nextVirus[r][c].get(i);
					if (age % 5 == 0) {
						breed(r, c);
					}
				}
			}
		}

		// 모든 바이러스가 번식을 끝낸 후 virus를 갱신한다.
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				virus[r][c] = new ArrayList<>(nextVirus[r][c]);
			}
		}
	}

	static void breed(int r, int c) {
		for (int dir = 0; dir < DIR_NUM; dir++) {
			int nr = r + dr[dir];
			int nc = c + dc[dir];
			if (inRange(nr, nc)) {
				nextVirus[nr][nc].add(1);
			}
		}
	}

	static boolean inRange(int r, int c) {
		return 0 <= r && r < n && 0 <= c && c < n;
	}

	static void addFood() {
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				food[r][c] += givenFood[r][c];
			}
		}
	}

	static void input() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		for (int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < n; c++) {
				givenFood[r][c] = Integer.parseInt(st.nextToken());
				food[r][c] = 5;
				virus[r][c] = new ArrayList<>();
			}
		}

		for (int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int age = Integer.parseInt(st.nextToken());
			virus[r - 1][c - 1].add(age);
		}
	}

	static void output() {
		int ans = 0;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				ans += virus[r][c].size();
			}
		}
		System.out.println(ans);
	}
}