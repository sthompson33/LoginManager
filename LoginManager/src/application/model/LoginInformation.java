/**
 * 
 */
package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jasypt.exceptions.AlreadyInitializedException;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author Stephen
 *
 */
public class LoginInformation {
	
	private static String website, username, password;
	public static String fileName;
	private static BasicTextEncryptor code = new BasicTextEncryptor();

	/**
     * 
     * 
     */
	
	private static boolean findWebsite(String site) {
		
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
     * 
     * 
     */
	
	public static void addNewLogin(String site, String username, String password) {
		
		if(!findWebsite(site)) {
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
		else
			JOptionPane.showMessageDialog(null, "login for website already exist");
	}
	
	/**
     * 
     * 
     */
	
	public static String[] retrieveLogin(String site) {
		
		String[] login = new String[2];
		
		if(findWebsite(site)) {
			login[0] = username;
			login[1] = password;
		}
		else
			JOptionPane.showMessageDialog(null, "login for website does not exist");
		
		return login;
	}
	
	/**
     * 
     * 
     */
	
	public static void updateLogin(String site, String username, String password) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		if(findWebsite(site)) {
			try {
				Scanner scan = new Scanner(new File(fileName + ".txt"));
				while(scan.hasNext()) {
					String w = code.decrypt(scan.nextLine());
					String u = code.decrypt(scan.nextLine());
					String p = code.decrypt(scan.nextLine());
					
					if(site.equals(w)) {
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
		else
			JOptionPane.showMessageDialog(null, "login for website does not exist");
	}
	
	/**
     * 
     * 
     */
	
	public static void deleteLogin(String site) {
		
		ArrayList<String> list = new ArrayList<String>();
		
		if(findWebsite(site)) {
			try {
				Scanner scan = new Scanner(new File(fileName + ".txt"));
				while(scan.hasNext()) {
					String w = code.decrypt(scan.nextLine());
					String u = code.decrypt(scan.nextLine());
					String p = code.decrypt(scan.nextLine());
					
					if(!site.equals(w)) {
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
		else
			JOptionPane.showMessageDialog(null, "login for website does not exist");
	}
}
