
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Test {
	
	public static void main(String[] args) {
		
		Socket socket;
		DataOutputStream out;
		DataInputStream in;
		
		String IP = "https://govagriculture.web.ctfcompetition.com";
		int PORT = 80;
		
		try {
			socket = new Socket(IP, PORT);
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			
			out.writeUTF("GET /about.html HTTP/1.1");
			out.writeUTF("Host: https://govagriculture.web.ctfcompetition.com\n");
			out.flush();
			
			String msg = "";
			while (!msg.equals("over")) {
				msg = in.readUTF();
				System.out.println(msg);
			}
			
			socket.close();
			out.close();
			in.close();
			
		} catch (IOException e) {}
		
	}
	
}
