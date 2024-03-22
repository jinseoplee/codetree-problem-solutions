// https://www.codetree.ai/cote/14/problems/pair-parentheses-2/description

import java.io.*;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		char[] a = br.readLine().toCharArray();
		
		int count = 0;
		for (int i = 1; i < a.length - 2; i++) {
			if (a[i] == ')' || a[i - 1] == ')') continue;
			for (int j = i + 1; j < a.length - 1; j++) {
				if (a[j] == '(' || a[j + 1] == '(') continue;
				count++;
			}
		}
		System.out.println(count);
	}
}