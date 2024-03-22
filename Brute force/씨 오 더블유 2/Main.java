// https://www.codetree.ai/cote/14/problems/c-o-w-2/description

import java.io.*;

public class Main {
	static int n;
	static char[] str;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		n = Integer.parseInt(br.readLine());
		str = br.readLine().toCharArray();

		// 'C', 'O', 'W'가 순서대로 몇 번 나오는지 센다.
		int count = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				for (int k = j + 1; k < n; k++) {
					if (str[i] == 'C' && str[j] == 'O' && str[k] == 'W')
						count++;
				}
			}
		}
		System.out.println(count);
	}
}