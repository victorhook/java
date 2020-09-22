package global;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.stream.Stream;

public class KeyHandler {

    public static String KEY_DIR = ConfigHandler.CONFIG_PATH + "keys/",
                  PUB_KEY = KEY_DIR + "pub.key", PRIV_KEY = KEY_DIR + "priv.key";

    public static void main(String[] args) throws Exception {

        /*File pubKey = new File(KEY_DIR + "pub.key");
        File privKey = new File(KEY_DIR + "priv.key");*/
        KeyPair pair = getKeys();
    }

    private static void generateKeys() throws IOException, NoSuchAlgorithmException {
        var config = ConfigHandler.getConfig();

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(config.getOrDefault("algorithm", "RSA"));
        keyPairGenerator.initialize(Integer.valueOf(config.getOrDefault("keysize", "2048")));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        saveKey(keyPair.getPublic(), PUB_KEY);
        saveKey(keyPair.getPrivate(), PRIV_KEY);
    }

    public static KeyPair getKeys() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        var config = ConfigHandler.getConfig();
        String algorithm = config.getOrDefault("algorithm", "RSA");
        PublicKey pubKey = loadPubKey(PUB_KEY, algorithm);
        PrivateKey privKey = loadPrivKey(PRIV_KEY, algorithm);
        return new KeyPair(pubKey, privKey);
    }

    private static PublicKey loadPubKey(String key, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyData = Files.readAllBytes(Paths.get(key));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyData);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePublic(spec);
    }

    private static PrivateKey loadPrivKey(String key, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyData = Files.readAllBytes(Paths.get(key));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyData);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(spec);
    }

    private static void saveKey(Key key, String output) throws IOException {
        FileOutputStream stream = new FileOutputStream(output);
        stream.write(key.getEncoded());
        stream.close();
    }

}
