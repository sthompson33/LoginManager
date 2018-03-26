/**
 * 
 */

package application.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainLoginController {
	
	@FXML
	private AnchorPane rootAnchor;

	@FXML
	private Pane signInPane, signUpPane, forgotPane;

	@FXML
	private Button select_SignIn, select_SignUp, forgotButton;

	@FXML
	private JFXTextField signInUsername, signUpUsername, signUpEmail, forgotField;

	@FXML
	private JFXPasswordField signInPassword, signUpPassword;

	@FXML
	private TextArea textArea;
	    
	@FXML
	private JFXButton signUpButton, signInButton, retrieveButton, exitButton, backButton;

	@FXML
	private ImageView displayImage, userIcon, userIcon2, userIcon3, lockIcon, lockIcon2, emailIcon;
	
	private ArrayList<TextField> textFieldList = new ArrayList<TextField>(3);
	private Account userAccount;
	
	public void initialize(){
		
		signInPane.toFront();
		
		textArea.setEditable(false);
    	textArea.setFocusTraversable(false);
    	textArea.setMouseTransparent(false);
    }
    
	/**
     * 
     * 
     */
    
    @FXML
    public void switchPaneListener(ActionEvent event) {
    	
    	
    	if(event.getSource() == select_SignIn) {
    		signInPane.toFront();
    		signInUsername.requestFocus();
    	}
    	
    	if(event.getSource() == forgotButton) {
    		forgotPane.toFront();
    		forgotField.requestFocus();
    	}
    	
    	if(event.getSource() == select_SignUp) {
    		signUpPane.toFront();
    		signUpUsername.requestFocus();
    	}
    	
    	if(event.getSource() == backButton) {
    		signInPane.toFront();
    		signInUsername.requestFocus();
    	}
    	
    	if(event.getSource() == exitButton)
    		System.exit(0);
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void signInListener(ActionEvent event) throws IOException {
    	
    	textFieldList.clear();
    	textFieldList.add(signInUsername);
    	textFieldList.add(signInPassword);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account(signInUsername.getText(), signInPassword.getText());
    		if(userAccount.findAccount()) {
    			switchScene(event);
    		}
    		else
    			JOptionPane.showMessageDialog(null, "username or password incorrect");
    	}
    }
    
    /**
     * 
     * 
     */

    @FXML
    public void signUpListener(ActionEvent event) throws IOException {
    	
    	textFieldList.clear();
    	textFieldList.add(signUpUsername);
    	textFieldList.add(signUpPassword);
    	textFieldList.add(signUpEmail);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account(signUpUsername.getText(), signUpPassword.getText(), signUpEmail.getText());
    		if(!userAccount.findAccount()) {
    			userAccount.createAccount();
    			switchScene(event);
    		}
    		else
    			JOptionPane.showMessageDialog(null, "account already exist");
    	}
    }
    
    /**
     * 
     * 
     */
    
    public void retrieveListener(){
    	
    	textFieldList.clear();
    	textFieldList.add(forgotField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		JOptionPane.showMessageDialog(null, "Email Sent!");
    	}
    }
    
    /**
     * 
     * 
     */
    
    private void switchScene(ActionEvent event) throws IOException{
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("MenuOption.fxml"));
    	AnchorPane root = loader.load();
    	Scene menuScene = new Scene(root);
    	
    	//access new controller and pass account object to it
    	MenuOptionController controller = loader.getController();
    	controller.init_Username(userAccount);
    	
		Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		currentStage.setScene(menuScene);
		currentStage.show();
    }
    
    /**
     * 
     * 
     */
    
    private boolean checkForEmptyInput(ArrayList<TextField> textfield) {
    	
    	boolean emptyInput = false;
    	
    	for(TextField tf : textfield) {
    		
    		if(tf.getText().isEmpty()) {
    			tf.getStyleClass().add("jfx-text-field-error");
    			emptyInput = true;
    		}
    		else {
    			tf.getStyleClass().clear();
    			tf.getStyleClass().addAll("text-field", "text-input", "jfx-text-field");
    		}
    	}
    	
    	return emptyInput;
    }
}
