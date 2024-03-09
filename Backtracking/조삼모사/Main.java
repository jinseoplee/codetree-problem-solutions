import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n, ans;
	static int[][] intensity;
	static int[] morning, evening;
	static boolean[] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		ans = Integer.MAX_VALUE;
		intensity = new int[n][n];
		morning = new int[n / 2];
		evening = new int[n / 2];
		visited = new boolean[n];

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < n; j++) {
				intensity[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		divideJob(0, 0);
		System.out.println(ans);
	}

	static void divideJob(int start, int idx) {
		if (idx == n / 2) {
			eveningJob();
			calculate();
			return;
		}

		for (int i = start; i < n; i++) {
			visited[i] = true;
			morning[idx] = i;
			divideJob(i + 1, idx + 1);
			visited[i] = false;
		}
	}

	static void eveningJob() {
		int idx = 0;
		for (int i = 0; i < n; i++) {
			if (!visited[i]) {
				evening[idx++] = i;
			}
		}
	}

	static void calculate() {
		int morningIntensity = 0;
		int eveningIntensity = 0;
		for (int i = 0; i < n / 2; i++) {
			for (int j = i + 1; j < n / 2; j++) {
				morningIntensity += intensity[morning[i]][morning[j]] + intensity[morning[j]][morning[i]];
				eveningIntensity += intensity[evening[i]][evening[j]] + intensity[evening[j]][evening[i]];
			}
		}
		ans = Math.min(ans, Math.abs(morningIntensity - eveningIntensity));
	}
}