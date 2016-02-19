import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

public class DNSServerTest {
	
	private static String name;
	
	public static void main(String[] args) {
		if (!args[0].equals("-p") || !args[2].equals("-n")) {
			System.out.println("Usage: ./dnsserver -p <port> -n <name>");
			System.exit(1);
		}
		try {
			int portNumber = Integer.parseInt(args[1]);
			if (portNumber < 40000 || portNumber > 65535) {
				System.out.println("The port number should be in range from 40000 to 65535!");
				System.exit(1);
			}
			name = args[3];
			byte []sendBytes = buildDnsQuestionByteArray();
			byte []receiveBytes = new byte[512];
			DatagramSocket client = new DatagramSocket();
			InetAddress addr = InetAddress.getByName(name);
			DatagramPacket sender = new DatagramPacket(sendBytes, sendBytes.length, addr, portNumber);
			client.send(sender);
			DatagramPacket receiver = new DatagramPacket(receiveBytes, receiveBytes.length);
			client.receive(receiver);
			String target = new String(receiver.getData());
			System.out.println("Targer replica: " + target);
			client.close();
			
			//System.out.println("Port Number: " + portNumber);
			//System.out.println("Name: " + name);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] buildDnsQuestionByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(512);
		byte []id = new byte[2];
		Random rand = new Random();
		rand.nextBytes(id);
		buffer.put(id);
		buffer.put((byte)0x01);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x01);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		buffer.put((byte)0x00);
		
		String []domainParts = name.split(".");
		for (String part: domainParts) {
			buffer.put((byte)part.length());
			buffer.put(part.getBytes(Charset.forName("UTF-8")));
		}
		buffer.put((byte)0x00);
		buffer.putShort((short)0x0001);
		buffer.putShort((short)0x0001);
		
		return buffer.array();
	}

}
