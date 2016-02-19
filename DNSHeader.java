public class DNSHeader {

	public static final int HEADER_SIZE = 12;
	
	public static final short QR_MASK = (short)0x8000;
	
	public static final short OPCODE_MASK = (short)0x7800;
	
	public static final short AA_MASK = (short)0x0400;
	
	public static final short TC_MASK = (short)0x0200;
	
	public static final short RD_MASK = (short)0x0100;
	
	public static final short RA_MASK = (short)0x0080;
	
	public static final short RCODE_MASK = (short)0x000f;
	
	private int id;
	
	private boolean qr;
	
	private int opcode;
	
	private boolean aa;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isQr() {
		return qr;
	}

	public void setQr(boolean qr) {
		this.qr = qr;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public boolean isAa() {
		return aa;
	}

	public void setAa(boolean aa) {
		this.aa = aa;
	}

	public boolean isTc() {
		return tc;
	}

	public void setTc(boolean tc) {
		this.tc = tc;
	}

	public boolean isRd() {
		return rd;
	}

	public void setRd(boolean rd) {
		this.rd = rd;
	}

	public boolean isRa() {
		return ra;
	}

	public void setRa(boolean ra) {
		this.ra = ra;
	}

	public int getRcode() {
		return rcode;
	}

	public void setRcode(int rcode) {
		this.rcode = rcode;
	}

	public int getQdcount() {
		return qdcount;
	}

	public void setQdcount(int qdcount) {
		this.qdcount = qdcount;
	}

	public int getAncount() {
		return ancount;
	}

	public void setAncount(int ancount) {
		this.ancount = ancount;
	}

	public int getNscount() {
		return nscount;
	}

	public void setNscount(int nscount) {
		this.nscount = nscount;
	}

	public int getArcount() {
		return arcount;
	}

	public void setArcount(int arcount) {
		this.arcount = arcount;
	}

	private boolean tc;
	
	private boolean rd;
	
	private boolean ra;
	
	private int rcode;
	
	private int qdcount;
	
	private int ancount;
	
	private int nscount;
	
	private int arcount;
	
	public DNSHeader(byte []message, int messageLength) {
		extract(message, messageLength);
	}
	
	private void extract(byte []message, int messageLength) {
		int offset = 0;
		if (messageLength < HEADER_SIZE) {
			System.err.println("DNS error: malformed message header!");
			System.exit(1);
		}
		id = DNSUtility.getShort(message, offset);
		offset += 2;
		short flags = (short) DNSUtility.getShort(message, offset);
		qr = (flags & QR_MASK) == 0;
		opcode = (flags & OPCODE_MASK) >>> 11;
		aa = (flags & AA_MASK) != 0;
		tc = (flags & TC_MASK) != 0;
		rd = (flags & RD_MASK) != 0;
		ra = (flags & RA_MASK) != 0;
		rcode = (flags & RCODE_MASK);
		offset += 2;
		qdcount = DNSUtility.getShort(message, offset);
		offset += 2;
		ancount = DNSUtility.getShort(message, offset);
		offset += 2;
		nscount = DNSUtility.getShort(message, offset);
		offset += 2;
		arcount = DNSUtility.getShort(message, offset);
		offset += 2;
	}
	
	public void print() {
		System.out.println("DNS Header\n");
		System.out.println("id: " + Integer.toHexString(id));
		System.out.println("qr: " + qr);
		System.out.println("opcode: " + opcode);
		System.out.println("aa: " + aa);
		System.out.println("tc: " + tc);
		System.out.println("rd: " + rd);
		System.out.println("ra: " + ra);
		System.out.println("rcode: " + rcode);
		System.out.println("qdcount: " + qdcount);
		System.out.println("ancount: " + ancount);
		System.out.println("nscount: " + nscount);
		System.out.println("arcount: " + arcount + "\n");
	}
	
}
