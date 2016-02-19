import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingTest {

	public static void main(String[] args) throws IOException {
		String []commands = new String[]{"scamper", "-c", "ping -c 1", "-i", "129.10.117.100"};
		Process ps = Runtime.getRuntime().exec(commands);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
		//PrintWriter pw = new PrintWriter(ps.getOutputStream());
		
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}

}
