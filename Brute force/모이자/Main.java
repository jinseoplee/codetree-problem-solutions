// https://www.codetree.ai/cote/14/problems/gather/description

import java.io.*;
import java.util.*;

public class Main {
    static int n;
    static int[] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 각 i번째 집으로 모였을 때 합을 구한다.
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int sumOfDistance = 0;

            for (int j = 0; j < n; j++) {
                sumOfDistance += Math.abs(i - j) * arr[j];
            }

            // 가능한 거리의 합 중 최솟값을 구한다.
            min = Math.min(min, sumOfDistance);
        }
        System.out.println(min);
    }
}