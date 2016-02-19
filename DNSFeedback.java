import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class DNSFeedback implements Runnable {

	private DatagramSocket server;
	
	private DNSHeader dnsHeader;
	
	private DNSQuestion dnsQuestion;
	
	private DatagramPacket sender;
	
	private static InetAddress addr;
	
	private int port;
	
	public DNSFeedback(DatagramSocket server, DNSHeader dnsHeader, DNSQuestion dnsQuestion, InetAddress address, int port) {
		this.server = server;
		this.dnsHeader = dnsHeader;
		this.dnsQuestion = dnsQuestion;
		addr = address;
		this.port = port;
	}
	
	public static byte[] buildDnsResponseByteArray(DNSHeader dnsHeader, DNSQuestion dnsQuestion) {
		
		ByteBuffer buffer = ByteBuffer.allocate(256);
		
		int id = dnsHeader.getId();
		buffer.putShort((short)id);
		
		buffer.put((byte)0x81);
		buffer.put((byte)0x80);
		buffer.put((byte)0x00);
		buffer.put((byte)0x01);
		buffer.put((byte)0x00);
		buffer.put((byte)0x01);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		
		String []parts = dnsQuestion.getQname().split("\\.");
		for (int i = 0; i < parts.length; ++i) {
			buffer.put((byte)parts[i].length());
			buffer.put(parts[i].getBytes());
		}
		buffer.put((byte)0x00);
		buffer.putShort((short)dnsQuestion.getQtype());
		buffer.putShort((short)dnsQuestion.getQclass());
		
		try {
			for (int i = 0; i < parts.length; ++ i) {
				buffer.put((byte)parts[i].length());
				buffer.put(parts[i].getBytes());
			}
			buffer.put((byte)0x00);
			buffer.putShort((short)0x0001);
			buffer.putShort((short)0x0001);
			buffer.put((byte)0x00);
			buffer.put((byte)0x00);
			buffer.put((byte)0x02);
			buffer.put((byte)0x58);
			buffer.putShort((short)0x0004);
			String fastestDomainName = "54.174.6.90";
			if (!DNSCacheManager.hasCache(addr.getHostAddress())) {
				new Thread(new ReplicaPicker(DNSServer.replicas[0], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[1], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[2], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[3], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[4], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[5], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[6], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[7], addr.getHostAddress())).start();
				new Thread(new ReplicaPicker(DNSServer.replicas[8], addr.getHostAddress())).start();
				fastestDomainName = ReplicaPicker.pick();
				DNSCacheManager.addCache(addr.getHostAddress(), fastestDomainName);
			} else {
				fastestDomainName = DNSCacheManager.fetchCache(addr.getHostAddress());
			}
			
			buffer.put(InetAddress.getByName(fastestDomainName).getAddress());
			
			return buffer.array();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		return buffer.array();
	}
	
	@Override
	public void run() {
		byte []sendBytes = buildDnsResponseByteArray(dnsHeader, dnsQuestion);
		sender = new DatagramPacket(sendBytes, sendBytes.length, addr, port);
		try {
			server.send(sender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
