package penta.cryptography;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hash {

	public static String Hash(String string) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(string.getBytes());
		
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < byteData.length; i++)
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		
		return sb.toString();
	}
}
