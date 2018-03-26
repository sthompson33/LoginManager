/**
 * 
 */
package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jasypt.exceptions.AlreadyInitializedException;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author Stephen
 *
 */

public class Account {

	private String username, password, email;
	private BasicTextEncryptor code = new BasicTextEncryptor();
	
	public Account(String username, String password) {
	
		setUsername(username);
		setPassword(password);
	}
	
	public Account(String username, String password, String email) {
		
		setUsername(username);
		setPassword(password);
		setEmail(email);
	}
	
	public void setUsername(String username) {
		
		this.username = username;
	}
	
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	public void setEmail(String email) {
		
		this.email = email;
	}
	
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
     * 
     * 
     */
	
	public boolean findAccount() {
		  
		boolean found = false;
		
		try {
			code.setPassword("loginmanager");
		}
		catch(AlreadyInitializedException e) {
			
		}
		
		try {
			Scanner scan = new Scanner(new File("accounts.txt"));
			while(scan.hasNext()) {
				String user = scan.nextLine();
				String pass = scan.nextLine();
				String email = scan.nextLine();
				
				if(code.decrypt(user).equals(username) && code.decrypt(pass).equals(password)) {
					found = true;
				}
			}
			scan.close();
		}
		catch(FileNotFoundException e) {
			//e.printStackTrace();
		}
		
		return found;
	}
	
	/**
	 * 
     * 
     */
	
	public void createAccount(){
		
		try {
			code.setPassword("loginmanager");
		}
		catch(AlreadyInitializedException e) {
			
		}
		
		try {
			FileOutputStream fos = new FileOutputStream("accounts.txt", true);
			PrintWriter pw = new PrintWriter(fos);
			
			pw.println(code.encrypt(username));
			pw.println(code.encrypt(password));
			pw.println(code.encrypt(email));
			pw.close();
			
			//creates a specific file for each new account created
			FileOutputStream fos2 = new FileOutputStream(username + ".txt");
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
		
	
}
