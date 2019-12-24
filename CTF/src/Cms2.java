import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Cms2 {

	public static void main(String[] args) throws Exception {
		
		URL url = new URL("http://35.190.155.168/f9a9ed72e4/login");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setDoOutput(true);
		con.connect();
		
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.write("");
		
		
		InputStream in = con.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder response = new StringBuilder();
		String line;
		
		while ((line = reader.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		
		System.out.println(response.toString());
		
		reader.close();
		con.disconnect();
		
	}

}
