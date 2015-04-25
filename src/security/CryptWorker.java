package security;

import java.io.IOException;
import java.security.*;

import javax.crypto.*;

/*
 * Not entirely certain that these functions shouldn't just be made static; we would need minor modifications to the code.
 */
public class CryptWorker {
	
	private Cipher cipher;
	private KeyGenerator keyGenerator;
	private SecureRandom secureRandom;
	private Key key;
	
	public CryptWorker() 
	throws NoSuchAlgorithmException, NoSuchPaddingException
	{
		cipher = Cipher.getInstance(SecurityData.ALGORITHM);
		secureRandom = SecureRandom.getInstance(SecurityData.SECURE_RANDOM_ALGORITHM);
		
		keyGenerator = KeyGenerator.getInstance(SecurityData.ALGORITHM);
		keyGenerator.init(secureRandom);
		key = keyGenerator.generateKey();
	}
	
	public Key getCurrentKey() { return key; }
	
	public byte[] encryptData(byte[] data) 
	throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		cipher.init(Cipher.ENCRYPT_MODE, key, secureRandom);
		return cipher.doFinal(data);
	}

	public byte[] decryptData(byte[] data, Key oldKey)
	throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, ClassNotFoundException
	{
		cipher.init(Cipher.DECRYPT_MODE, oldKey, secureRandom);
		return cipher.doFinal(data);
	}
	
}
