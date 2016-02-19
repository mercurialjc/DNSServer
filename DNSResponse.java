import java.net.InetAddress;
import java.net.UnknownHostException;

public class DNSResponse {

	private String name = "";
	
	private short type;
	
	private short _class;
	
	private int ttl;
	
	private short rdlength;
	
	private InetAddress rdata;
	
	public int offset;
	
	public DNSResponse(byte []message, int messageLength, int offset) {
		this.offset = offset;
		extract(message, messageLength);
	}
	
	private void extract(byte []message, int messageLength) {
		int lengthOfFollowingString = DNSUtility.getSingleByteInt(message, offset);
		offset += 1;
		while (lengthOfFollowingString != 0) {
			name += DNSUtility.getString(message, offset, lengthOfFollowingString);
			offset += lengthOfFollowingString;
			name += ".";
			lengthOfFollowingString = DNSUtility.getSingleByteInt(message, offset);
			offset += 1;
		}
		type = (short)DNSUtility.getShort(message, offset);
		offset += 2;
		_class = (short)DNSUtility.getShort(message, offset);
		offset += 2;
		ttl = DNSUtility.getInt(message, offset);
		offset += 4;
		rdlength = (short)DNSUtility.getShort(message, offset);
		offset += 2;
		byte []rdataBytes = new byte[rdlength];
		System.arraycopy(message, offset, rdataBytes, 0, rdlength);
		try {
			rdata = InetAddress.getByAddress(rdataBytes);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void print() {
		System.out.println("DNS Response\n");
		System.out.println("name: " + name);
		System.out.println("type: " + type);
		System.out.println("class: " + _class);
		System.out.println("ttl: " + ttl);
		System.out.println("rdlength: " + rdlength);
		System.out.println("rdata: " + rdata.getHostAddress() + "\n");
	}
	
}
