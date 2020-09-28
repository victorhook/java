package global;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.datatype.DatatypeFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class Crypter {

    private PrivateKey myPrivKey;
    private PublicKey myPubKey, otherPubKey;
    private SecretKey symmetricKey;
    private MessageDigest md5Digest;

    private static String asymetricAlgorithm;
    public static final String SYMETRIC_ALGORITHM = "AES";
    public static final int SYMMETRIC_ALGORITHM_KEY_SIZE = 128;

    public Crypter(KeyPair keyPair) throws NoSuchAlgorithmException, FileNotFoundException {
        this.myPrivKey = keyPair.getPrivate();
        this.myPubKey = keyPair.getPublic();
        this.symmetricKey = generateSymetricKey();
        this.asymetricAlgorithm = ConfigHandler.getConfig().get("algorithm");
        this.md5Digest = MessageDigest.getInstance("MD5");
    }

    private SecretKey generateSymetricKey() throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(SYMETRIC_ALGORITHM);
        generator.init(SYMMETRIC_ALGORITHM_KEY_SIZE);
        return generator.generateKey();
    }

    public Pair encrypt(PublicKey theirPubKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // Encrypt the data with a symetric key.
        Cipher symCipher = Cipher.getInstance(SYMETRIC_ALGORITHM);
        symCipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
        byte[] encryptedData = symCipher.doFinal(data);

        // Encrypt the key with the public key.
        Cipher cipher = Cipher.getInstance(asymetricAlgorithm);
        cipher.init(Cipher.ENCRYPT_MODE, theirPubKey);
        byte[] encryptedKey = cipher.doFinal(symmetricKey.getEncoded());
        return new Pair(encryptedData, encryptedKey);
    }

    public byte[] decrypt(byte[] encryptedKey, byte[] encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        // Decrypt the symmetric key
        Cipher cipher = Cipher.getInstance(asymetricAlgorithm);
        cipher.init(Cipher.DECRYPT_MODE, myPrivKey);
        byte[] decryptedKey = cipher.doFinal(encryptedKey);
        SecretKey originalKey = new SecretKeySpec(decryptedKey , 0, decryptedKey.length, SYMETRIC_ALGORITHM);

        // Decrypt the data with the now-decrypted symmetric key
        Cipher aesCipher = Cipher.getInstance(SYMETRIC_ALGORITHM);
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] decryptedData = aesCipher.doFinal(encryptedData);
        return decryptedData;
    }

    public static String encryptSymetric(String data) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey key = KeyHandler.getSymmetricKey();
        Cipher symCipher = Cipher.getInstance(Crypter.SYMETRIC_ALGORITHM);
        symCipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedPass = symCipher.doFinal(data.getBytes());
        return new String(Base64.getEncoder().encode(encryptedPass));
    }

    public static String decryptSymetric(String encryptedPass) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        SecretKey key = KeyHandler.getSymmetricKey();
        Cipher symCipher = Cipher.getInstance(Crypter.SYMETRIC_ALGORITHM);
        symCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedPassInBytes = Base64.getDecoder().decode(encryptedPass.getBytes());
        return new String(symCipher.doFinal(encryptedPassInBytes));
    }

    public void setOtherPublicKey(PublicKey otherPubKey) {
        this.otherPubKey = otherPubKey;
    }

    public byte[] sign(byte[] data) throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException,
                                            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = KeyHandler.getCipher();
        cipher.init(Cipher.ENCRYPT_MODE, myPrivKey);
        cipher.update(data);
        return cipher.doFinal();
    }

    public void decryptSymetric() {

    }

    public static class Pair {
        public byte[] encryptedData, encryptedKey;

        Pair (byte[] encryptedData, byte[] encryptedKey) {
            this.encryptedData = encryptedData;
            this.encryptedKey = encryptedKey;

        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public boolean md5SumIsOk(String file, byte[] md5Sum) throws NoSuchAlgorithmException {
        String data = new Scanner(file).useDelimiter("\\Z").next();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md5.digest(data.getBytes());
        return md5Bytes.equals(md5Sum);
    }

    /*public String md5(byte[] digest) throws NoSuchAlgorithmException {
        md5Digest
         = md.digest("hey".getBytes("UTF-8"));
        System.out.println(byteArrayToHex(digest));
    }*/

    public static void main(String[] args) throws Exception {

    }

}
