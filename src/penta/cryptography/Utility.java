package penta.cryptography;
import java.util.UUID;

public class Utility {
	
	public static String GenerateUniqueKey()
	{
		return UUID.randomUUID().toString();
	}
}
