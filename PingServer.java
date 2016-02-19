import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PingServer {

	public static int port = 59876;
	
	public static InetAddress addr;
	
	public static Socket dataSocket;
	
	public static ServerSocket serverSocket;
	
	public static BufferedReader input;
	
	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			serverSocket.bind(new InetSocketAddress(port));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			while (true) {
				dataSocket = serverSocket.accept();
				input = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
				String pingIP = input.readLine();
				new Thread(new Pinger(dataSocket, pingIP)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
