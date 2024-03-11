import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Pair {
	int r, c;

	Pair(int r, int c) {
		this.r = r;
		this.c = c;
	}
}

public class Main {
	static final int PERSON = 1;
	static final int HOSPITAL = 2;
	static final int MAX_HOSPITAL = 13;

	static int ans = Integer.MAX_VALUE;
	static int n, m;
	static List<Pair> people = new ArrayList<>();
	static List<Pair> hospitals = new ArrayList<>();
	static Pair[] selected = new Pair[MAX_HOSPITAL];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());

		for (int r = 0; r < n; r++) {
			st = new StringTokenizer(br.readLine());
			for (int c = 0; c < n; c++) {
				int input = Integer.parseInt(st.nextToken());
				if (input == PERSON) {
					people.add(new Pair(r, c));
				} else if (input == HOSPITAL) {
					hospitals.add(new Pair(r, c));
				}
			}
		}

		selectHospital(0, 0);
		System.out.println(ans);
	}

	static void selectHospital(int start, int idx) {
		// m개의 병원을 선택한 경우 병원 거리의 합을 최소화한다.
		if (idx == m) {
			ans = Math.min(ans, getTotalDistance());
			return;
		}

		// 선택할 수 있는 병원의 후보들을 탐색한다.
		for (int i = start; i < hospitals.size(); i++) {
			selected[idx] = hospitals.get(i);
			selectHospital(i + 1, idx + 1);
		}
	}

	// 병원 거리의 합을 구한다.
	static int getTotalDistance() {
		int totalDistance = 0;

		// 각 사람에 대하여 가장 가까운 병원 거리를 구한다.
		for (int i = 0; i < people.size(); i++) {
			int eachMinDistance = Integer.MAX_VALUE;
			for (int j = 0; j < m; j++) {
				eachMinDistance = Math.min(eachMinDistance, getDistance(people.get(i), selected[j]));
			}
			totalDistance += eachMinDistance;
		}

		return totalDistance;
	}

	// 사람과 병원 사이의 거리를 구한다.
	static int getDistance(Pair person, Pair hospital) {
		return Math.abs(person.r - hospital.r) + Math.abs(person.c - hospital.c);
	}
}