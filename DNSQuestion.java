public class DNSQuestion {

	private String qname = "";
	
	public String getQname() {
		return qname;
	}

	public void setQname(String qname) {
		this.qname = qname;
	}

	public int getQtype() {
		return qtype;
	}

	public void setQtype(short qtype) {
		this.qtype = qtype;
	}

	public int getQclass() {
		return qclass;
	}

	public void setQclass(short qclass) {
		this.qclass = qclass;
	}

	private int qtype;
	
	private int qclass;
	
	public int offset;
	
	public DNSQuestion(byte []message, int messageLength) {
		extract(message, messageLength);
	}
	
	private void extract(byte []message, int messageLength) {
		offset = DNSHeader.HEADER_SIZE;
		
		int lengthOfFollowingString = DNSUtility.getSingleByteInt(message, offset);
		offset += 1;
		while (lengthOfFollowingString != 0) {
			qname += DNSUtility.getString(message, offset, lengthOfFollowingString);
			offset += lengthOfFollowingString;
			qname += ".";
			lengthOfFollowingString = DNSUtility.getSingleByteInt(message, offset);
			offset += 1;
		}
		qname = qname.substring(0, qname.length() - 1);
		qtype = DNSUtility.getShort(message, offset);
		offset += 2;
		qclass = DNSUtility.getShort(message, offset);
		offset += 2;
	}
	
	public void print() {
		System.out.println("DNS Question\n");
		System.out.println("qname: " + qname);
		System.out.println("qtype: " + qtype);
		System.out.println("qclass: " + qclass + "\n");
	}
	
}
