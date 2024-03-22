// https://www.codetree.ai/cote/14/problems/pair-parentheses-3/description

import java.io.*;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		char[] str = br.readLine().toCharArray();
		int count = 0;
		
		// 모든 쌍을 찾는다.
		for (int i = 0; i < str.length; i++) {
			if (str[i] == ')') continue;
			for (int j = i + 1; j < str.length; j++) {
				if (str[j] == ')') count++;
			}
		}
		System.out.println(count);
	}
}