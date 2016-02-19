import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DNSCacheManager {

	private static String CACHE_FILE = "dnscache.txt";
	
	//private static HashMap<InetAddress, InetAddress> cachemap = new HashMap<InetAddress, InetAddress>();
	
	public static boolean hasCache(String addr) {
		boolean result = false;
		try {
			FileReader rstream = new FileReader(CACHE_FILE);
			BufferedReader in = new BufferedReader(rstream);
			String line = null;
			while (((line = in.readLine()) != null)) {
				String []parts = line.split(" ");
				if (addr.equals(parts[0])) {
					result = true;
					break;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static void addCache(String addr, String value) {
		if (hasCache(addr)) {
			return;
		}
		FileWriter wstream;
		try {
			wstream = new FileWriter(CACHE_FILE, true);
			BufferedWriter out = new BufferedWriter(wstream);
			out.write(addr + " " + value + "\n");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String fetchCache(String qname) {
		String result = null;
		try {
			FileReader rstream = new FileReader(CACHE_FILE);
			BufferedReader in = new BufferedReader(rstream);
			String line = null;
			while (((line = in.readLine()) != null)) {
				String []parts = line.split(" ");
				if (qname.equals(parts[0])) {
					result = parts[1];
					break;
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
}
