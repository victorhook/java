package chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Database {

	private Crypter crypter;
	private FileOutputStream fileStream;
	private FileInputStream reader;
	private FileWriter writer;
	private final String BASE_DIR = "db/", USERS = BASE_DIR + "users.xml", CONF = BASE_DIR + ".config";
	private DocumentBuilderFactory doc;
	private Document document;
	private int totalUsers;
	
	private HeadServer server;

	public Database(HeadServer server) {
		this.server = server;
		crypter = new Crypter(server);
		try {
			initDb();
			server.log("Database initalized correctly");
		} catch (Exception e) {
			server.log("An error occured when initializing the Database");
		}
	}

	
	PublicKey getPubKey() {
		return crypter.getPublicKey();
	}
	
	boolean passwordOk(byte[] userName, byte[] pass) {
	
		try {
			// Opens the xml file to check what id the given username has
			doc = DocumentBuilderFactory.newInstance();
			document = doc.newDocumentBuilder().parse(USERS);
			NodeList userInfo = document.getElementsByTagName("users").item(0).getChildNodes();

			// Iterates over the user IDs to find the right match with the userName
			for (int usr = 0; usr < userInfo.getLength(); usr++) {
				String id = userInfo.item(usr).getNodeName();

				if (!id.equals("#text")) {
					Node name = document.getElementsByTagName(id).item(0).getChildNodes().item(1);
					byte[] decryptedUserName = openUser(name.getTextContent(), 'N');
					decryptedUserName = crypter.decrypt(decryptedUserName);
					
					// If a match is found on the username, it checks wether the passwords match
					// Returns true if they do, false if not
					if (Arrays.equals(decryptedUserName, crypter.decrypt(userName))) {
						Node passw = document.getElementsByTagName(id).item(0).getChildNodes().item(3);
						byte[] decryptedPassw = openUser(passw.getTextContent(), 'P');
						decryptedPassw = crypter.decrypt(decryptedPassw);
						if (Arrays.equals(decryptedPassw, crypter.decrypt(pass))) {
							return true;
						}
					}
				}
			}

		} catch (Exception e) {
		}

		return false;
	}

	boolean userExist(byte[] name) {

		// Returns true if the given name is already a user and false if not
		try {
			doc = DocumentBuilderFactory.newInstance();
			document = doc.newDocumentBuilder().parse(USERS);
			NodeList userInfo = document.getElementsByTagName("users").item(0).getChildNodes();

			for (int usr = 0; usr < userInfo.getLength(); usr++) {
				String id = userInfo.item(usr).getNodeName();

				if (!id.equals("#text")) {

					Node userName = document.getElementsByTagName(id).item(0).getChildNodes().item(1);
					byte[] decryptedUserName = openUser(userName.getTextContent(), 'N');
					decryptedUserName = crypter.decrypt(decryptedUserName);

					if (Arrays.equals(decryptedUserName, crypter.decrypt(name))) {
						return true;
					}
				}
			}
		} catch (Exception e) {
		}

		return false;
	}

	private byte[] openUser(String userName, char NorP) {
		// Opens a info file of a given user and returns the bytes of that file

		try {
			reader = new FileInputStream(BASE_DIR + NorP + "/" + userName);
			return reader.readAllBytes();
		} catch (Exception e) {
			return null;
		}
	}

	void addNewUser(byte[] name, byte[] pass) {
		try {
			// Adds the new user information to the user.xml file
			reader = new FileInputStream(USERS);
			String users = new String(reader.readAllBytes(), "UTF8");
			reader.close();

			Matcher regMatch = Pattern.compile("(?<=<users>).*(?=<\\/users>)", Pattern.DOTALL)
					.matcher((CharSequence) users);
			regMatch.find();

			users = users.substring(0, regMatch.end()) + format(++totalUsers)
					+ users.substring(regMatch.end(), users.length());
			writer = new FileWriter(USERS);
			writer.write(users);
			writer.close();

			// Update the total users in the config file
			reader = new FileInputStream(CONF);
			String config = new String(reader.readAllBytes(), "UTF8");
			reader.close();

			regMatch = Pattern.compile("(?<=<users>)\\d+(?=<\\/users>)").matcher((CharSequence) config);
			regMatch.find();
			config = config.substring(0, regMatch.start()) + totalUsers
					+ config.substring(regMatch.end(), config.length());
			writer = new FileWriter(CONF);
			writer.write(config);
			writer.close();

			// Saved the user encrypted credentials in seperate locations
			fileStream = new FileOutputStream(BASE_DIR + "N/N" + totalUsers);
			fileStream.write(crypter.encrypt(name));
			fileStream.close();

			fileStream = new FileOutputStream(BASE_DIR + "P/P" + totalUsers);
			fileStream.write(crypter.encrypt(pass));
			fileStream.close();

			fileStream = null;
		} catch (Exception e) {
		}
	}

	private void initDb() throws Exception {
		// Checks if the database template exists, otherwise creates a new one

		File dbDir = new File("db");
		if (!dbDir.exists()) {
			dbDir.mkdir();
		}

		File usersFile = new File(USERS);
		if (!usersFile.exists()) {
			try {
				writer = new FileWriter(usersFile);
				writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				writer.write("<users>\n</users>");
				writer.close();
			} catch (Exception e) {
			}
		}

		File userNames = new File(BASE_DIR + "N");
		if (!userNames.exists()) {
			userNames.mkdir();
		}

		File passwords = new File(BASE_DIR + "P");
		if (!passwords.exists()) {
			passwords.mkdir();
		}

		// The config file contains the number of total users.
		File conf = new File(BASE_DIR + ".config");
		if (!conf.exists()) {
			try {
				writer = new FileWriter(conf);
				writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				writer.write("<users>0</users>\n");
				writer.close();
			} catch (Exception e) {
			}
		} else {
			doc = DocumentBuilderFactory.newInstance();
			try {
				document = doc.newDocumentBuilder().parse(conf);
				Node users = document.getElementsByTagName("users").item(0);
				this.totalUsers = Integer.parseInt(users.getTextContent());
			} catch (Exception e) {
				throw new Exception();
			}
		}

	}

	private String format(int userID) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<user_" + userID + ">\n\t");
		sb.append("<name>N" + userID + "</name>\n\t");
		sb.append("<pass>P" + userID + "</pass>\n");
		sb.append("</user_" + userID + ">\n");
		return sb.toString();
	}

}
