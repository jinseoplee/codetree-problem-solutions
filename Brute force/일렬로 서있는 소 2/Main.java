// https://www.codetree.ai/cote/14/problems/cattle-in-a-rowing-up-2/description

import java.io.*;
import java.util.*;

public class Main {
	static int n;
	static int[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		n = Integer.parseInt(br.readLine());
		arr = new int[n];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		// 서로 다른 쌍의 수를 구한다.
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (arr[i] > arr[j]) continue;
				for (int k = j + 1; k < n; k++) {
					if (arr[j] > arr[k]) continue;
					count++;
				}
			}
		}
		System.out.println(count);
	}
}