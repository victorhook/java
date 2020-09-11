package lpt;

import java.util.Comparator;

public class DescTimeComp implements Comparator<Job> {

	@Override
	public int compare(Job j1, Job j2) {
		int t1 = j1.getTime(), t2 = j2.getTime();
		if (t1 < t2) {
			return 1;
		} else if (t1 > t2) {
			return -1;
		}
		return 0;
	}

}
