package global;

import includes.BCrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Stream;

public class Authenticator {

    private static final String PASSWORD_PATH = ConfigHandler.CONFIG_PATH + "auth/credentials",
                                SAVED_HOSTS_PATH = ConfigHandler.CONFIG_PATH + "auth/saved_hosts_auth";

    private static String getPass() throws FileNotFoundException {
        String pass = new Scanner(new File(PASSWORD_PATH)).nextLine();
        pass = pass.split("\\s*=\\s*")[1];
        return pass;
    }

    /*
        Searches the know host-file and returns the password (if the host exists).
        If the host can't be found, null is returned.
    */
    public static String getPassForHost(String host) throws FileNotFoundException {
        String pass = new Scanner(new File(SAVED_HOSTS_PATH)).useDelimiter("\\Z").next();
        Map<String, String> hosts = new HashMap<>();

        // Each line is formatted 'host' = 'password'
        var res = Stream.of(pass.split("\n"))
                .map(line -> line.split("\\s*=\\s*"))
                .filter(line -> line[0].trim().equals(host))
                .findFirst();

        if (res.isPresent()) {
            // Password is on the second index of the line (seperated by '=')
            return res.get()[1];
        }
        return null;
    }

    public static void savePass(String password) throws IOException {
        String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
        FileWriter writer = new FileWriter(PASSWORD_PATH);
        writer.write(String.format("password = %s", hashedPw));
        writer.close();
    }

    public static boolean authenticate(String password) throws FileNotFoundException {
        return BCrypt.checkpw(password, getPass());
    }

    public static void main(String[] args) throws Exception {
        Authenticator auth = new Authenticator();
        System.out.println(BCrypt.hashpw("bollar123", BCrypt.gensalt()));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] res = md.digest("hey".getBytes());
        System.out.println(res);

        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256);
        SecretKey secretKey = generator.generateKey();

        Cipher symCipher = Cipher.getInstance("AES");
        symCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = symCipher.doFinal("hemligt".getBytes());

        String d = Base64.getEncoder().encodeToString(encryptedData);
        System.out.println(d);

        /*System.out.println(encryptedData);
        Cipher symCipher2 = Cipher.getInstance("AES");
        symCipher2.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] dec = symCipher2.doFinal(encryptedData);
        System.out.println(new String(dec));*/




    }


}
