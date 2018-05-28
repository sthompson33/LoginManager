/**
 * @author Stephen Thompson
 * 
 * <pre> The LoginInformation class contains methods that allow information such as a
 * website, username and password to be stored on file in encrypted format.
 * Such methods allow adding, updating, or deleting information. To retrieve forgotten information,
 * getters for username and password are included.
 * 
 * The jasypt library has been imported for encryption purposes. Any information to be written to a file 
 * will be encrypted first with the BasicTextEncryptor class. Same class will be used for decrypting when 
 * reading from a file.</pre>
 */

package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jasypt.exceptions.AlreadyInitializedException;
import org.jasypt.util.text.BasicTextEncryptor;

public class LoginInformation {

	private String fileName, website, username, password;
	private BasicTextEncryptor code = new BasicTextEncryptor();

	/**
	 * <pre>
	 * Initiliazed the fileName field. Once fileName has been set, all methods will
	 * look for this specific file to use.
	 * </pre>
	 * 
	 * @param file
	 *            - String value that represents which file to use
	 */
	public LoginInformation(String file) {
		fileName = file;
	}

	/**
	 * <pre>
	 * Returns current username from the last time
	 * the findWebsite method was invoked.
	 * </pre>
	 * 
	 * @return String representing a username or null if findWebsite has not
	 *         previously been called.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <pre>
	 * Returns current password from the last time the findWebsite method was invoked.
	 * </pre>
	 * 
	 * @return String representing a password or null if findWebsite has not
	 *         previously been called.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <pre>
	 * Looks to see if the website exist on file. If the website has
	 * been found, Scanner will stop and close. The corresponding username and password 
	 * will be stored in their respective class fields. Temporary Strings are used to hold the values
	 * when reading through the file.
	 * </pre>
	 * 
	 * @param site
	 *            - website to look for
	 * @return true if website found, false if not
	 */
	public boolean findWebsite(String website) {

		boolean found = false;

		try {
			code.setPassword("loginmanager");
		} catch (AlreadyInitializedException e) {

		}

		try {
			Scanner scan = new Scanner(new File(fileName + ".txt"));
			while (scan.hasNext()) {
				String currentWebsite = code.decrypt(scan.nextLine());
				String tempUsername = code.decrypt(scan.nextLine());
				String tempPassword = code.decrypt(scan.nextLine());

				if (currentWebsite.equals(website)) {
					this.website = currentWebsite;
					username = tempUsername;
					password = tempPassword;
					found = true;
					break;
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {

		}

		return found;
	}

	/**
	 * <pre>
	 * Adds a new website, username and password to the end of file.
	 * Should call the findWebsite method first to be sure no duplicate information is being added.
	 * </pre>
	 * 
	 * @param site
	 *            - new website to be added
	 * @param username
	 *            - new username to be added
	 * @param password
	 *            - new password to be added
	 */
	public void addNewLogin(String website, String username, String password) {

		try {
			FileOutputStream fos = new FileOutputStream(fileName + ".txt", true);
			PrintWriter pw = new PrintWriter(fos);
			pw.println(code.encrypt(website));
			pw.println(code.encrypt(username));
			pw.println(code.encrypt(password));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Updates the username and/or password for a website that's on file.
	 * Should call the findWebsite method first to be sure the website exist. If the website is found, 
	 * the website class field will have been initialized and then can be compared to websites on file.
	 * </pre>
	 * 
	 * @param username
	 *            - new username to be changed to if not empty
	 * @param password
	 *            - new password to be changed to if not empty
	 * @throws NullPointerException
	 *             - thrown if website has not been initialized
	 */
	public void updateLogin(String username, String password) throws NullPointerException {

		if (website == null)
			throw new NullPointerException("Website field is null");

		ArrayList<String> list = new ArrayList<String>();

		try {
			Scanner scan = new Scanner(new File(fileName + ".txt"));
			while (scan.hasNext()) {
				String currentWebsite = code.decrypt(scan.nextLine());
				String currentUsername = code.decrypt(scan.nextLine());
				String currentPassword = code.decrypt(scan.nextLine());

				if (website.equals(currentWebsite)) {
					if (!username.isEmpty())
						currentUsername = username;
					if (!password.isEmpty())
						currentPassword = password;
				}

				list.add(code.encrypt(currentWebsite));
				list.add(code.encrypt(currentUsername));
				list.add(code.encrypt(currentPassword));
			}
			scan.close();

			FileOutputStream fos = new FileOutputStream(fileName + ".txt", false);
			PrintWriter pw = new PrintWriter(fos);

			for (String line : list)
				pw.println(line);

			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <pre>
	 * Deletes a website, username and password from file.
	 * Should call the findWebsite first to be sure website exist. If the website was found,
	 * the website class field will have been initialized and then can be compared to websites on file.
	 * </pre>
	 * 
	 * @throws NullPointerException
	 *             - thrown if website has not been initialized
	 */
	public void deleteLogin() throws NullPointerException {

		if (website == null)
			throw new NullPointerException("Website field is null");

		ArrayList<String> list = new ArrayList<String>();

		try {
			Scanner scan = new Scanner(new File(fileName + ".txt"));
			while (scan.hasNext()) {
				String currentWebsite = code.decrypt(scan.nextLine());
				String currentUsername = code.decrypt(scan.nextLine());
				String currentPassword = code.decrypt(scan.nextLine());

				if (!website.equals(currentWebsite)) {
					list.add(code.encrypt(currentWebsite));
					list.add(code.encrypt(currentUsername));
					list.add(code.encrypt(currentPassword));
				}
			}
			scan.close();

			FileOutputStream fos = new FileOutputStream(fileName + ".txt", false);
			PrintWriter pw = new PrintWriter(fos);

			for (String line : list)
				pw.println(line);

			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
