import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class DNSServer {

	private static int DEFAULT_PORT = 40000;
	
	private static String name = "";
	
	private static int port;
	
	private static DatagramSocket server;
	
	private static DatagramPacket receiver;
	
	private static DatagramPacket sender;
	
	public static String []replicas = {
		"54.174.6.90",
		"54.149.9.25",
		"54.67.86.61",
		"54.72.167.104",
		"54.93.182.67",
		"54.169.146.226",
		"54.65.104.220",
		"54.66.212.131",
		"54.94.156.232"
	};
	
	public static ArrayList<byte[]> cache = new ArrayList<byte[]>();
	
	public static void main(String []args) {
		if (!args[0].equals("-p") || !args[2].equals("-n")) {
			System.out.println("Usage: ./dnsserver -p <port> -n <name>");
			System.exit(1);
		}

		port = Integer.parseInt(args[1]);
		if (port < 40000 || port > 65535) {
			System.out.println("The port number should be in range from 40000 to 65535! It's set to default port, which is 40000.");
			port = DEFAULT_PORT;
		}
		name = args[3];
		try {
			server = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Thread(new DNSListener(server, port)).start();
	}
	
}
