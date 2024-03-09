import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int n;
	static int min = Integer.MAX_VALUE;
	static int max = Integer.MIN_VALUE;
	static int[] nums;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		nums = new int[n];

		// 정수를 입력받아 nums 배열에 저장한다.
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			nums[i] = Integer.parseInt(st.nextToken());
		}

		// 사용 가능한 덧셈, 뺄셈, 곱셈을 입력받는다.
		st = new StringTokenizer(br.readLine());
		int addCount = Integer.parseInt(st.nextToken());
		int subCount = Integer.parseInt(st.nextToken());
		int mulCount = Integer.parseInt(st.nextToken());

		// 연산자를 배치하여 최댓값과 최솟값을 구한다.
		placeOperator(addCount, subCount, mulCount, nums[0], 1);

		System.out.println(min + " " + max);
	}

	static void placeOperator(int addCount, int subCount, int mulCount, int result, int idx) {
		// n개의 정수를 모두 계산한 경우 최솟값과 최댓값을 구한다.
		if (idx == n) {
			min = Math.min(min, result);
			max = Math.max(max, result);
			return;
		}

		// 덧셈 연산을 수행한다.
		if (addCount != 0) {
			placeOperator(addCount - 1, subCount, mulCount, result + nums[idx], idx + 1);
		}

		// 뺄셈 연산을 수행한다.
		if (subCount != 0) {
			placeOperator(addCount, subCount - 1, mulCount, result - nums[idx], idx + 1);
		}

		// 곱셈 연산을 수행한다.
		if (mulCount != 0) {
			placeOperator(addCount, subCount, mulCount - 1, result * nums[idx], idx + 1);
		}
	}
}
