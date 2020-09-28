package global;

import includes.BCrypt;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    public static String getPassForHost(String host) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        // Read host-file from disk
        String hostsFile = readHostFile();

        // Find given host and return the password after decrypting it.
        String hostRegex = String.format("%s\\s*=\\s*.*", host);
        Pattern pattern = Pattern.compile(hostRegex);
        Matcher matcher = pattern.matcher(hostsFile);
        if (matcher.find()) {
            String encryptedPass = matcher.group().split("\\s*=\\s*", 2)[1];
            return Crypter.decryptSymetric(encryptedPass);
        } else {
            return null;
        }
    }

    private static String readHostFile() throws FileNotFoundException {
        return new Scanner(new File(SAVED_HOSTS_PATH)).useDelimiter("\\Z").next();
    }

    public static void savePassForHost(String host, String password) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // Encrypt the password so it's not in clear text
        password = Crypter.encryptSymetric(password);

        // Read host-file from disk
        String hostsFile = readHostFile();

        // Replace host password with new one
        String hostRegex = String.format("%s\\s*=\\s*.*", host);
        String hostReplace = String.format("%s = %s", host, password);

        Pattern p = Pattern.compile(hostRegex);
        if (p.matcher(hostsFile).find()) {
            hostsFile = hostsFile.replaceFirst(hostRegex, hostReplace);
        } else {
            hostsFile += hostReplace;
        }

        // Save content back to disk.
        FileWriter writer = new FileWriter(SAVED_HOSTS_PATH);
        hostsFile = hostsFile.endsWith("\n") ? hostsFile : hostsFile + '\n';
        writer.write(hostsFile);
        writer.close();
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
        savePassForHost("127.0.0.1", "bollar123");
        //SecretKey k = KeyHandler.getSymmetricKey();

/*        Authenticator auth = new Authenticator();
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
        System.out.println(d);*/

        /*System.out.println(encryptedData);
        Cipher symCipher2 = Cipher.getInstance("AES");
        symCipher2.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] dec = symCipher2.doFinal(encryptedData);
        System.out.println(new String(dec));*/




    }


}
