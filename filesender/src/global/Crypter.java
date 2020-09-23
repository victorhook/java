package global;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.security.*;

public class Crypter {

    private PrivateKey myPrivKey;
    private PublicKey myPubKey, otherPubKey;
    private SecretKey symmetricKey;

    private static String asymetricAlgorithm;
    private static final String SYMETRIC_ALGORITHM = "AES";
    private static final int SYMMETRIC_ALGORITHM_KEY_SIZE = 128;

    public Crypter(KeyPair keyPair) throws NoSuchAlgorithmException, FileNotFoundException {
        this.myPrivKey = keyPair.getPrivate();
        this.myPubKey = keyPair.getPublic();
        this.symmetricKey = generateSymetricKey();
        this.asymetricAlgorithm = ConfigHandler.getConfig().get("algorithm");
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

    public static class Pair {
        public byte[] encryptedData, encryptedKey;

        Pair (byte[] encryptedData, byte[] encryptedKey) {
            this.encryptedData = encryptedData;
            this.encryptedKey = encryptedKey;

        }
    }

}
