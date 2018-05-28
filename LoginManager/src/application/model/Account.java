/**
 * @author Stephen Thompson
 * 
 * <pre> The Account class contains methods that allow information for application such as
 * username, password and email to be stored on file in encrypted format.
 * Such methods include creating a new account, searching for an existing account or username.
 * Also a method for sending an email incase of forgotten password.
 *
 * The jasypt library has been imported for encryption purposes. Any information to be written to a file 
 * will be encrypted first with the BasicTextEncryptor class. Same class will be used for decrypting when 
 * reading from a file.</pre>
 */

package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.jasypt.exceptions.AlreadyInitializedException;
import org.jasypt.util.text.BasicTextEncryptor;

public class Account {

	private String username, password, email;
	private BasicTextEncryptor code = new BasicTextEncryptor();

	// setters
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// getters
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * <pre>
	 * Adds a new username, password and email to the end of the accounts.txt file, creating
	 * a new Account. Should use findAccount method first to be sure no duplicate information is being added
	 * </pre>
	 * 
	 * @param username
	 *            - new username to be added
	 * @param password
	 *            - new password to be added
	 * @param email
	 *            - new email to be added
	 * @throws NullPointerException
	 *             - thrown if any of the parameters are null
	 */
	public void createAccount(String username, String password, String email) throws NullPointerException {

		if (username == null || password == null || email == null)
			throw new NullPointerException("one or more class fields is null");

		try {
			code.setPassword("loginmanager");
		} catch (AlreadyInitializedException e) {

		}

		try {
			FileOutputStream fos = new FileOutputStream("accounts.txt", true);
			PrintWriter pw = new PrintWriter(fos);

			pw.println(code.encrypt(username));
			pw.println(code.encrypt(password));
			pw.println(code.encrypt(email));
			pw.close();

			// creates a specific file for each new account created
			FileOutputStream fos2 = new FileOutputStream(username + ".txt");
			fos2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Looks to see if username exist on file. For a match to be found, username has to be equal to the 
	 * username being read from file. Temporary Strings are used to hold the values when reading through the file. 
	 * The class fields will be set to these values if a match is found. This prevents the last username, password and email 
	 * on file to be stored in class fields if a match was not found.
	 * </pre>
	 * 
	 * @return true if username found on file, false if not
	 */
	public boolean findUsername(String username) {

		boolean found = false;

		try {
			code.setPassword("loginmanager");
		} catch (AlreadyInitializedException e) {

		}

		try {
			Scanner scan = new Scanner(new File("accounts.txt"));
			while (scan.hasNext()) {
				String currentUsername = code.decrypt(scan.nextLine());
				String currentPassword = code.decrypt(scan.nextLine());
				String currentEmail = code.decrypt(scan.nextLine());

				if (currentUsername.equals(username)) {
					this.username = username;
					password = currentPassword;
					email = currentEmail;
					found = true;
					break;
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}

		return found;
	}

	/**
	 * <pre>
	 * Looks to see if an Account exist on file. For a match to be found, username and password both
	 * have to be equal to the username and password being read from file. Temporary Strings are used to hold the values 
	 * when reading through file. The class fields will be set to these values if a match is found. 
	 * This prevents the last username, password and email on file to be stored in class fields if a match was not found.
	 * </pre>
	 * 
	 * @oaram username - username to look for
	 * @param password
	 *            - password to look for
	 * @return true if a match to username and password was found, false if not
	 */
	public boolean findAccount(String username, String password) {

		boolean found = false;

		try {
			code.setPassword("loginmanager");
		} catch (AlreadyInitializedException e) {

		}

		try {
			Scanner scan = new Scanner(new File("accounts.txt"));
			while (scan.hasNext()) {
				String currentUsername = code.decrypt(scan.nextLine());
				String currentPassword = code.decrypt(scan.nextLine());
				String currentEmail = code.decrypt(scan.nextLine());

				if (currentUsername.equals(username) && currentPassword.equals(password)) {
					this.username = username;
					this.password = password;
					email = currentEmail;
					found = true;
					break;
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}

		return found;
	}

	/**
	 * <pre>
	 * Verifies that the email matches the currently supported email domain.
	 * Currently only supporting gmail addresses.
	 * </pre>
	 * 
	 * @return true if email supported, false if not
	 */
	public boolean verifyEmail(String email) {
		int atIndex = email.indexOf("@");
		if (email.substring(atIndex + 1, email.length() - 4).equals("gmail"))
			return true;
		else
			return false;
	}

	/**
	 * <pre>
	 * Sends an email to current this.email with forgotten password. Should call
	 * findAccount or findUsername first to set class fields.
	 * </pre>
	 * 
	 * @throws NullPointerException
	 *             - thrown if email or password contains null
	 */
	public void sendEmail() throws NullPointerException {

		if (email == null || password == null)
			throw new NullPointerException("email or password contains null");

		final String SENDER = "LoginManager3@gmail.com";
		final String PASSWORD = "reganaMnigoL12";

		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SENDER, PASSWORD);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Password Recovery <Do Not Reply>");
			message.setText("Your Login Manager password is " + password);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
