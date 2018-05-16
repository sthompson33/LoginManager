/**
 * @author Stephen
 *
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
	
	
	public Account() {
		
	}
	
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
	 * @return
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
				String user = code.decrypt(scan.nextLine());
				String pass = code.decrypt(scan.nextLine());
				scan.nextLine(); //read next line not used here
				
				if(user.equals(username) && pass.equals(password)) {
					found = true;
					break;
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
	 * @param user
	 * @return
	 */
	
	public boolean findUsername(String user) {
		
		boolean found = false;
		
		try {
			code.setPassword("loginmanager");
		}
		catch(AlreadyInitializedException e) {
			
		}
		
		try {
			Scanner scan = new Scanner(new File("accounts.txt"));
			while(scan.hasNext()) {
				username = code.decrypt(scan.nextLine());
				password = code.decrypt(scan.nextLine());
				email = code.decrypt(scan.nextLine());
				
				if(user.equals(username)) {
					found = true;
					break;
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
			fos2.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean verifyEmail(String emailAttempt) {
		int atIndex = emailAttempt.indexOf("@");
		if(emailAttempt.substring(atIndex + 1, emailAttempt.length() - 4).equals("gmail"))
			return true;
		else
			return false;
	}
	
	/**
	 * 
	 */
	
	public void sendEmail() {
		
		final String SENDER = "LoginManager3@gmail.com";
		final String PASSWORD = "reganaMnigoL12";
		
		Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER, PASSWORD);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
            message.setSubject("Password Recovery <Do Not Reply>");
            message.setText("Your Login Manager password is " + password);

            Transport.send(message);

            //System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
		
	
}
