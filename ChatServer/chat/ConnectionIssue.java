package chat;

import java.io.IOException;

public class ConnectionIssue extends Exception {
	
	public ConnectionIssue(String errorMsg) {
		super(errorMsg);
	}
	
}
