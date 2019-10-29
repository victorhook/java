package chat;

import java.util.Set;

public class Test2 {

	public static void main(String[] args) {
		
		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
		for (Thread t: threadSet) {
			System.out.println(t);
		}
		
		
	}

}
