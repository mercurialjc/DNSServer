import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class ReplicaPicker implements Runnable {

	private Socket socket;
	
	private String localIP;
	
	private InetAddress destination;
	
	private PrintWriter output;
	
	private BufferedReader input;
	
	private int default_ping_port = 59876;
	
	public static double minimumTime;
	
	public static LinkedHashMap<InetAddress, String> hashmap = new LinkedHashMap<InetAddress, String>();
	
	public ReplicaPicker(String replica, String localIP) {
		try {
			destination = InetAddress.getByName(replica);
			this.localIP = localIP;
			run();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {
			//if (destination.isReachable(10000)) {
				socket = new Socket(destination, default_ping_port);
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(),true);
				output.println(localIP);
				String info = input.readLine();
				hashmap.put(destination, info);
				input.close();
				output.close();
				socket.close();
			//} else {
				//hashmap.put(destination, "Not reachable!");
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static String pick() {
		try {
			minimumTime = Double.MAX_VALUE;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = "54.174.6.90";
		
		Iterator<Entry<InetAddress, String>> iterator = hashmap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<InetAddress, String> entry = iterator.next();
			String temp = entry.getValue();
			if (temp.equals("MAX")) continue;
			double value = Double.valueOf(temp);
			String replica = entry.getKey().getHostAddress();
			if (value < minimumTime) {
				minimumTime = value;
				result = replica;
			}
			//System.out.println("Replica: " + entry.getKey().getHostAddress() + "Speed: " + entry.getValue());
		}
		
		return result;
	}
	
}
