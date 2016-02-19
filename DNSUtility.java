public class DNSUtility {

	public static int getSingleByteInt(byte []message, int offset) {
		return message[offset] & 0xff;
	}
	
	public static int getShort(byte []message, int offset) {
		return ((message[offset] & 0xff) << 8 | (message[offset + 1] & 0xff));
	}
	
	public static int getInt(byte []message, int offset) {
		return ((message[offset] & 0xff) << 24 | (message[offset + 1] & 0xff) << 16 
				| (message[offset + 2] & 0xff) << 8 | (message[offset + 3] & 0xff));
	}
	
	public static String getString(byte []message, int offset, int length) {
		byte []subArray = new byte[length];
		System.arraycopy(message, offset, subArray, 0, length);
		return new String(subArray);
	}
	
}
