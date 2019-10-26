package chat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyLogger {

	private String logName;
	private SimpleDateFormat timeFormat;
	
	public MyLogger(String logName) {
		this.logName = logName;
		timeFormat = new SimpleDateFormat("H:mm");
		
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(logName));
			String time = timeFormat.format(new Date());
			w.write(String.format(" -- Log started %s -- \n", time));
			w.close();
		} catch (IOException e) {}
	}
	
	public void log(String msg)  {
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter(logName, true));
			String time = timeFormat.format(new Date());
			w.write(String.format("%s:  %s\n", time, msg));
			w.close();
		} catch (IOException e) {}
	}
	
}
