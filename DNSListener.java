import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DNSListener implements Runnable {

	private DatagramSocket server;
	
	private int port;
	
	private DatagramPacket receiver;
	
	public DNSListener(DatagramSocket server, int port) {
		this.server = server;
		this.port = port;
	}
	
	@Override
	public void run() {
		while (server.isBound()) {
			byte []receiveBytes = new byte[256];
			receiver = new DatagramPacket(receiveBytes, receiveBytes.length);
			try {
				server.receive(receiver);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte []receivedData = receiver.getData();
			
			DNSHeader dnsHeader = new DNSHeader(receivedData, receivedData.length);
			DNSQuestion dnsQuestion = new DNSQuestion(receivedData, receivedData.length);
			InetAddress addr = receiver.getAddress();
			int port = receiver.getPort();
			
			new Thread (new DNSFeedback(server, dnsHeader, dnsQuestion, addr, port)).start();
		}
	}

}
