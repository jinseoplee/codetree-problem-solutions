import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n, k, step;
	static int[] stability;
	static boolean[] occupied;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		stability = new int[2 * n + 1];
		occupied = new boolean[n + 1];

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= 2 * n; i++) {
			stability[i] = Integer.parseInt(st.nextToken());
		}

		simulate();
		System.out.println(step);
	}

	static void simulate() {
		while (k > 0) {
			step++;

			// 1. 무빙워크를 한 칸 회전시킨다.
			rotate();

			// 2. 사람들을 한 칸씩 앞으로 이동시킨다.
			move();

			// 3. 1번 칸에 사람을 올립니다.
			add();
		}
	}

	static void rotate() {
		int temp = stability[2 * n];
		for (int i = 2 * n; i > 1; i--) {
			stability[i] = stability[i - 1];
		}
		stability[1] = temp;

		for (int i = n; i > 1; i--) {
			occupied[i] = occupied[i - 1];
		}
		occupied[1] = false;
		occupied[n] = false;
	}

	static void move() {
		for (int i = n - 1; i >= 1; i--) {
			if (occupied[i] && canGo(i + 1)) {
				occupied[i] = false;
				occupied[i + 1] = true;
				if (--stability[i + 1] == 0) {
					k--;
				}
			}
		}
		occupied[n] = false;
	}

	static void add() {
		if (canGo(1)) {
			occupied[1] = true;
			if (--stability[1] == 0) {
				k--;
			}
		}
	}

	static boolean canGo(int i) {
		return !occupied[i] && stability[i] != 0;
	}
}