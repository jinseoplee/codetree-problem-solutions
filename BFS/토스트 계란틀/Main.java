import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

class Pair {
	int r, c;

	Pair(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

public class Main {
	static final int MAX_N = 50;

	static int n, L, R;
	static int[][] eggs = new int[MAX_N][MAX_N];
	static boolean[][] visited = new boolean[MAX_N][MAX_N];
	static Queue<Pair> q = new ArrayDeque<>();
	static List<Pair> eggGroup = new ArrayList<>();

	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		for (int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < n; c++) {
				eggs[r][c] = Integer.parseInt(st.nextToken());
			}
		}

		int moveCount = 0;
		while (true) {
			// 이동할 수 없을 때까지 계란을 이동시킨다.
			boolean isChanged = moveEggs();
			if (!isChanged) break;
			moveCount++;
		}
		System.out.println(moveCount);
	}

	static boolean moveEggs() {
		initVisited();

		boolean isChnaged = false;
		for (int r = 0; r < n; r++) {
			for (int c = 0; c < n; c++) {
				if (!visited[r][c]) {
					eggGroup = new ArrayList<>();
					eggGroup.add(new Pair(r, c));
					q.offer(new Pair(r, c));
					visited[r][c] = true;
					bfs();
					if (eggGroup.size() > 1) {
						isChnaged = true;
					}
					mergeEggs();
				}
			}
		}

		return isChnaged;
	}

	static void bfs() {
		while (!q.isEmpty()) {
			Pair curr = q.poll();
			for (int dir = 0; dir < 4; dir++) {
				int nextR = curr.r + dr[dir];
				int nextC = curr.c + dc[dir];
				if (canGo(curr.r, curr.c, nextR, nextC)) {
					q.offer(new Pair(nextR, nextC));
					eggGroup.add(new Pair(nextR, nextC));
					visited[nextR][nextC] = true;
				}
			}
		}
	}

	static void mergeEggs() {
		int sumOfEggs = 0;
		for (int i = 0; i < eggGroup.size(); i++) {
			sumOfEggs += eggs[eggGroup.get(i).r][eggGroup.get(i).c];
		}

		for (int i = 0; i < eggGroup.size(); i++) {
			eggs[eggGroup.get(i).r][eggGroup.get(i).c] = sumOfEggs / eggGroup.size();
		}
	}

	static boolean canGo(int currR, int currC, int nextR, int nextC) {
		if (!inRange(nextR, nextC)) {
			return false;
		}

		if (visited[nextR][nextC]) {
			return false;
		}

		int diff = Math.abs(eggs[currR][currC] - eggs[nextR][nextC]);
		return L <= diff && diff <= R;
	}

	static boolean inRange(int r, int c) {
		return 0 <= r && r < n && 0 <= c && c < n;
	}

	static void initVisited() {
		for (int r = 0; r < n; r++) {
			Arrays.fill(visited[r], false);
		}
	}
}