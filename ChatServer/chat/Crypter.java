package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class Crypter {

	private static KeyPairGenerator keyPairGen;
	private static KeyPair keyPair;
	private static Cipher cipher;
	private static FileWriter fileWriter;
	private static FileOutputStream fileStream;
	private static DocumentBuilderFactory doc;
	private static Document document;
	private final String ENCRYPT_ALGORITHM = "RSA/ECB/PKCS1Padding";
	private final String BASE_DIR = ".keys";

	public Crypter(HeadServer server) {
			generateKeys(server); 
	}
	
	public void store(byte[] input) {
		input = encrypt(input);
	}
	
	public byte[] decrypt(byte[] input) {
		// Decrypts the given input and returns it decrypted (in bytes)
		
		PrivateKey key = getPrivateKey();
		try {
			cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			cipher.update(input);
			return cipher.doFinal();
		} catch(Exception e) {
			return null;
		}
	}
	
	public byte[] encrypt(byte[] input) {
		// Encrypts a given byte input and returns it encrypted (in bytes)
		
		PublicKey key = getPublicKey();
		try {
			cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			cipher.update(input);
			return cipher.doFinal();
		} catch (Exception e) {
			return null;
		}
	}

	
	public PrivateKey getPrivateKey() {
		// Gets the private key from memory, returns null if it can't find it
		try {
			Path filePath = Paths.get(BASE_DIR, "priv_key");
			FileInputStream fi = new FileInputStream(filePath.toString());
			KeyFactory kf = KeyFactory.getInstance("RSA");

			PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(fi.readAllBytes()));
			return privateKey;
		} catch (Exception e) {
			return null;
		}
	}

	public PublicKey getPublicKey() {
		// Gets the private key from memory, returns null if it can't find it
		try {
			Path filePath = Paths.get(BASE_DIR, "pub_key");
			FileInputStream fi = new FileInputStream(filePath.toString());
			KeyFactory kf = KeyFactory.getInstance("RSA");

			PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(fi.readAllBytes()));
			return publicKey;
		} catch (Exception e) {
			return null;
		}
	}

	private void generateKeys(HeadServer server) {
		try {
			File keyDir = new File(BASE_DIR);
			
			// Checks if a directory for storing the keys exists and creates one if false
			if (!keyDir.exists()) {
				keyDir.mkdir();
			}
			if (keyDir.listFiles().length == 0) {
				// If the key directory is empty, new keys are generated and saved.

				keyPairGen = KeyPairGenerator.getInstance("RSA");
				keyPairGen.initialize(2048);
				keyPair = keyPairGen.generateKeyPair();
				FileOutputStream fs = new FileOutputStream(Paths.get(keyDir.getAbsolutePath(), "priv_key").toString());
				fs.write(keyPair.getPrivate().getEncoded());
				fs.close();

				fs = new FileOutputStream(Paths.get(BASE_DIR, "pub_key").toString());
				fs.write(keyPair.getPublic().getEncoded());
				fs.close();
			}
		} catch (Exception e) {
			server.log("Exception occured when generating keys");
		}
	}

}
