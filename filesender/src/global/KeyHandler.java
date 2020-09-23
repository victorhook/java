package global;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyHandler {

    public static String KEY_DIR = ConfigHandler.CONFIG_PATH + "keys/",
                  PUB_KEY = KEY_DIR + "pub.key", PRIV_KEY = KEY_DIR + "priv.key";

    public static void main(String[] args) throws Exception {

        KeyPair pair = getKeys();
        Crypter crypter = new Crypter(pair);
        Crypter.Pair k = crypter.encrypt(pair.getPublic(), "SECRET DATA!".getBytes());
        byte[] res = crypter.decrypt(k.encryptedKey, k.encryptedData);
        System.out.println(new String(res));


/*        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey symmetricKey = generator.generateKey();

        String data = "This is some secret stuff";
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, symmetricKey);
        byte[] byteCipher = aesCipher.doFinal(data.getBytes());

        KeyPair pair = getKeys();

        Cipher cipher = Cipher.getInstance(getAlgorithm());
        cipher.init(Cipher.PUBLIC_KEY, pair.getPublic());
        byte[] encryptedKey = cipher.doFinal(symmetricKey.getEncoded());

        // SEND THE DATA + SYMETRIC ENCRYPTED KEY ->

        // CLIENT SIDE, decrypt the symmetric key
        Cipher cipherClient = Cipher.getInstance(getAlgorithm());
        cipher.init(Cipher.PRIVATE_KEY, pair.getPrivate());
        byte[] decryptedKey = cipher.doFinal(encryptedKey);

        SecretKey originalKey = new SecretKeySpec(decryptedKey , 0, decryptedKey.length, "AES");
        Cipher aesCipherClient = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, originalKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipher);
        String plainText = new String(bytePlainText);

        System.out.println(bytePlainText);*/

        /*Cipher cipher = Cipher.getInstance(getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pair.getPrivate());

        //Adding data to the cipher
        byte[] input = "Welcome to Tutorialspoint".getBytes();
        cipher.update(input);
        System.out.println(cipher.doFinal());*/



/*        DataOutputStream writer = new DataOutputStream(new FileOutputStream("test.key"));
        writer.write(cipher.doFinal());
        writer.close();*/

    }



    private PrivateKey myPrivKey;
    private PublicKey myPubKey, otherPubKey;

    public KeyHandler(PrivateKey myPrivKey, PublicKey myPubKey) {
        this.myPrivKey = myPrivKey;
        this.myPubKey = myPubKey;
    }

    public void setOtherPublicKey(PublicKey otherPubKey) {
        this.otherPubKey = otherPubKey;
    }

    private static void generateKeys() throws IOException, NoSuchAlgorithmException {
        var config = ConfigHandler.getConfig();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(getAlgorithm());
        keyPairGenerator.initialize(Integer.valueOf(config.getOrDefault("keysize", "2048")));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        saveKey(keyPair.getPublic(), PUB_KEY);
        saveKey(keyPair.getPrivate(), PRIV_KEY);
    }

    public static KeyPair getKeys() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        var config = ConfigHandler.getConfig();
        PublicKey pubKey = loadPubKey(PUB_KEY);
        PrivateKey privKey = loadPrivKey(PRIV_KEY);
        return new KeyPair(pubKey, privKey);
    }

    private static PublicKey loadPubKey(String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyData = Files.readAllBytes(Paths.get(key));
        return readPublicKey(keyData);
    }

    private static String getAlgorithm() throws FileNotFoundException {
        var config = ConfigHandler.getConfig();
        return config.getOrDefault("algorithm", "RSA");
    }

    public static Cipher getCipher() throws FileNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(getAlgorithm());
    }

    public static PublicKey readPublicKey(byte[] bytes) throws FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance(getAlgorithm());
        return kf.generatePublic(spec);
    }

    public static PrivateKey readPrivateKey(byte[] bytes) throws FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance(getAlgorithm());
        return kf.generatePrivate(spec);
    }

    private static PrivateKey loadPrivKey(String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyData = Files.readAllBytes(Paths.get(key));
        return readPrivateKey(keyData);
    }

    private static void saveKey(Key key, String output) throws IOException {
        FileOutputStream stream = new FileOutputStream(output);
        stream.write(key.getEncoded());
        stream.close();
    }



}
