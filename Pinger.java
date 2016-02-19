import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Pinger implements Runnable {

	public static Socket socket;
	
	public static String time;
	
	public static int PING_PORT = 59876;
	
	public static String ip;
	
	public static String []commands;
	
	public static String timeInString;
	
	public static PrintWriter output;
	
	public Pinger(Socket dataSocket, String clientIP) {
		ip = clientIP;
		commands = new String[]{"scamper", "-c", "ping -c 1", "-i", ip};
		socket = dataSocket;
	}
	
	@Override
	public void run() {
		try {
			Process ps = Runtime.getRuntime().exec(commands);
			
	        int exitValue = ps.waitFor();
	        if (0 != exitValue) 
	        {  
	            System.out.println("call shell failed. error code is :" + exitValue);  
	        }
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));   
	        
	        String line = ""; 
	        String t = null;
	        double time = 0.0;
	        
			if ((line = br.readLine()) != null) 
			{  	
				if(line.contains("time"))
				{
					t = line.split(" ")[6];
					t = t.substring(5);
					time = Double.valueOf(t);
				}
			}
			timeInString = Double.toString(time);
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println(timeInString);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
