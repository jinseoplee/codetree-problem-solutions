import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int N = 4;
	static final int M = 8;
	static final int CW = 1; // 시계 방향
	static final int CCW = -1; // 반시계 방향
	static char[][] chair = new char[N + 1][M + 1];
	static int[] rotateDir = new int[N + 1];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		for (int i = 1; i <= N; i++) {
			char[] input = br.readLine().toCharArray();
			for (int j = 1; j <= M; j++) {
				chair[i][j] = input[j - 1];
			}
		}

		int k = Integer.parseInt(br.readLine());
		while (k-- > 0) {
			st = new StringTokenizer(br.readLine());
			int startNum = Integer.parseInt(st.nextToken());
			int startDir = Integer.parseInt(st.nextToken());
			simulate(startNum, startDir);
		}

		int ans = 0;
		for (int i = 1; i <= N; i++) {
			if (chair[i][1] == '1') {
				ans += (int) Math.pow(2, i - 1);
			}
		}

		// 출력
		System.out.println(ans);
	}

	static void simulate(int startNum, int startDir) {
		// 의자의 회전 방향을 초기화한다.
		for (int i = 1; i <= N; i++) {
			rotateDir[i] = 0;
		}

		// 시작 위치는 반드시 회전한다.
		rotateDir[startNum] = startDir;

		// 왼쪽에 있는 의자들의 회전 방향을 정한다.
		for (int i = startNum - 1; i >= 1; i--) {
			if (chair[i][3] == chair[i + 1][7]) break;
			rotateDir[i] = flip(rotateDir[i + 1]);
		}

		// 오른쪽에 있는 의자들의 회전 방향을 정한다.
		for (int i = startNum + 1; i <= N; i++) {
			if (chair[i][7] == chair[i - 1][3]) break;
			rotateDir[i] = flip(rotateDir[i - 1]);
		}

		// 회전 방향이 결정된 의자들을 회전시킨다.
		for (int i = 1; i <= N; i++) {
			if (rotateDir[i] != 0) {
				rotate(i, rotateDir[i]);
			}
		}
	}

	// 주어진 방향으로부터 반대 방향의 값을 반환한다.
	static int flip(int dir) {
		return dir == CW ? CCW : CW;
	}

	// 의자를 해당 방향으로 회전시킨다.
	static void rotate(int num, int dir) {
		if (dir == CW) {
			char temp = chair[num][M];
			for (int i = M; i > 1; i--) {
				chair[num][i] = chair[num][i - 1];
			}
			chair[num][1] = temp;
		} else if (dir == CCW) {
			char temp = chair[num][1];
			for (int i = 1; i < M; i++) {
				chair[num][i] = chair[num][i + 1];
			}
			chair[num][M] = temp;
		}
	}
}