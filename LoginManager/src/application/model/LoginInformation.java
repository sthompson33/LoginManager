/**
 * @author Stephen
 *
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
	
	private static String fileName, website, username, password;
	private static BasicTextEncryptor code = new BasicTextEncryptor();

	/**
	 * @param file
	 */
	
	public static void setFileName(String file) {
		fileName = file;
	}
	
	/**
	 * @param site
	 * @return
	 */
	
	public static boolean findWebsite(String site) {
		
		boolean found = false;
		
		try {
			code.setPassword("loginmanager");
		}
		catch(AlreadyInitializedException e) {
			
		}
		
		try {
			Scanner scan = new Scanner(new File(fileName + ".txt"));
			while(scan.hasNext()) {
				website = code.decrypt(scan.nextLine());
				username = code.decrypt(scan.nextLine());
				password = code.decrypt(scan.nextLine());
				
				if(website.equals(site)) {
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
	 * @param site
	 * @param username
	 * @param password
	 */
	
	public static void addNewLogin(String site, String username, String password) {
		
		try {
			FileOutputStream fos = new FileOutputStream(fileName + ".txt", true);
			PrintWriter pw = new PrintWriter(fos);
			pw.println(code.encrypt(site));
			pw.println(code.encrypt(username));
			pw.println(code.encrypt(password));
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * @return
	 */
	
	public static String[] retrieveLogin() {
		
		String[] login = new String[2];
		login[0] = username;
		login[1] = password;
	
		return login;
	}

	/**
	 * @param username
	 * @param password
	 */
	
	public static void updateLogin(String username, String password) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			Scanner scan = new Scanner(new File(fileName + ".txt"));
			while(scan.hasNext()) {
				String w = code.decrypt(scan.nextLine());
				String u = code.decrypt(scan.nextLine());
				String p = code.decrypt(scan.nextLine());
					
				if(website.equals(w)) {
					if(!username.isEmpty())
						u = username;
					if(!password.isEmpty())
						p = password;
				}
					
				list.add(code.encrypt(w));
				list.add(code.encrypt(u));
				list.add(code.encrypt(p));
			}
			scan.close();
				
			FileOutputStream fos = new FileOutputStream(fileName + ".txt", false);
			PrintWriter pw = new PrintWriter(fos);
			
			for(String line : list)
				pw.println(line);
				
			pw.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
		
	/**
	 * 
	 */
	
	public static void deleteLogin() {
		
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			Scanner scan = new Scanner(new File(fileName + ".txt"));
			while(scan.hasNext()) {
				String w = code.decrypt(scan.nextLine());
				String u = code.decrypt(scan.nextLine());
				String p = code.decrypt(scan.nextLine());
					
				if(!website.equals(w)) {
					list.add(code.encrypt(w));
					list.add(code.encrypt(u));
					list.add(code.encrypt(p));
				}
			}
			scan.close();
				
			FileOutputStream fos = new FileOutputStream(fileName + ".txt", false);
			PrintWriter pw = new PrintWriter(fos);
			
			for(String line : list)
				pw.println(line);
				
			pw.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
