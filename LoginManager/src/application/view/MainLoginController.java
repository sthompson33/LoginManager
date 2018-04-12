/**
 * 
 */

package application.view;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextField;

import application.model.Account;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainLoginController{
	
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
	
	private JFXSnackbar signInSnackbar, signUpSnackbar, forgotSnackbar;
	private PasswordIconChangeListener passwordListener = new PasswordIconChangeListener();
	private UserIconChangeListener userListener = new UserIconChangeListener();
	private EmailIconChangeListener emailListener = new EmailIconChangeListener();
	private ArrayList<TextField> textFieldList = new ArrayList<TextField>(3);
	private Account userAccount;
	private String cssLoginManager = this.getClass().getResource("LoginManager.css").toExternalForm();
	private String cssGreenSnackbar = this.getClass().getResource("GreenSnackbar.css").toExternalForm();
	private String cssRedSnackbar = this.getClass().getResource("RedSnackbar.css").toExternalForm();
	private Scene scene;

	public void initialize(){
		
		signInPane.toFront();
		
		signInSnackbar = new JFXSnackbar(signInPane);
		signUpSnackbar = new JFXSnackbar(signUpPane);
		forgotSnackbar = new JFXSnackbar(forgotPane);
	
		textArea.setEditable(false);
    	textArea.setFocusTraversable(false);
    	textArea.setMouseTransparent(false);
    	
    	signInUsername.focusedProperty().addListener(userListener);
    	signUpUsername.focusedProperty().addListener(userListener);
    	forgotField.focusedProperty().addListener(userListener);
    	
    	signInPassword.focusedProperty().addListener(passwordListener);
    	signUpPassword.focusedProperty().addListener(passwordListener);
    	
    	signUpEmail.focusedProperty().addListener(emailListener);
    }
    
	/**
     * 
     * 
     */
    
    @FXML
    public void switchPaneListener(ActionEvent event) {
    	
    	if(event.getSource() == select_SignIn) {
    		clearFields();
    		signInPane.toFront();
    		signInUsername.requestFocus();
    	}
    	
    	if(event.getSource() == forgotButton) {
    		clearFields();
    		forgotPane.toFront();
    		forgotField.requestFocus();
    	}
    	
    	if(event.getSource() == select_SignUp) {
    		clearFields();
    		signUpPane.toFront();
    		signUpUsername.requestFocus();
    	}
    	
    	if(event.getSource() == backButton) {
    		clearFields();
    		signInPane.toFront();
    		signInUsername.requestFocus();
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void signInListener(ActionEvent event) throws IOException {
    	
    	getScene();
    	textFieldList.clear();
    	textFieldList.add(signInUsername);
    	textFieldList.add(signInPassword);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account(signInUsername.getText(), signInPassword.getText());
    		if(userAccount.findAccount()) {
    			switchScene(event);
    		}
    		else {
    			setSnackbarStyle(cssRedSnackbar);
    			signInSnackbar.enqueue(new SnackbarEvent("Incorrect Username or Password"));
    		}
    	}
    }
    
    /**
     * 
     * 
     */

    @FXML
    public void signUpListener(ActionEvent event) throws IOException {
    	
    	getScene();
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
    		else {
    			setSnackbarStyle(cssRedSnackbar);
    			signUpSnackbar.enqueue(new SnackbarEvent("Account Already Exists"));
    		}
    	}
    }
    
    /**
     * 
     * 
     */
    
    public void retrieveListener(){
    	 
    	getScene();
    	textFieldList.clear();
    	textFieldList.add(forgotField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account();
    		if(userAccount.findUsername(forgotField.getText())) {
    			setSnackbarStyle(cssGreenSnackbar);
    			forgotSnackbar.enqueue(new SnackbarEvent("Email Sent!"));
    		}
    		else {
	    		setSnackbarStyle(cssRedSnackbar);
    			forgotSnackbar.enqueue(new SnackbarEvent("Username Not Found"));
    		}
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
     *  getScene() - finds current scene root node is on and stores it to global scene variable
     *  
     *  setSnackbarStyle() - first clear any stylesheets that have been previously added to scene
     *  				   - add default css along with red or green snackbar css @param style
     */
    
    private void getScene() {
    	scene = rootAnchor.getScene();
    }
    
    private void setSnackbarStyle(String style) {
    	scene.getStylesheets().clear();
    	scene.getStylesheets().addAll(cssLoginManager, style);
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
    			tf.getStyleClass().addAll("text-field", "text-input", "jfx-text-field", "password-field", "jfx-password-field");
    		}
    	}
    	
    	return emptyInput;
    }
    
    private void clearFields() {
    	signInUsername.clear();
    	signInPassword.clear();
    	signUpUsername.clear();
    	signUpPassword.clear();
    	signUpEmail.clear();
    	forgotField.clear();
    }
    
    /**
     * 
     * 
     */
    
    private class UserIconChangeListener implements ChangeListener<Boolean>{
    	
		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			//System.out.println("This was fired " + observable);
			if(newValue) {
				userIcon.setImage(new Image("/images/blue_user.png"));
				userIcon2.setImage(new Image("/images/blue_user.png"));
				userIcon3.setImage(new Image("/images/blue_user.png"));
			}
			else {
				userIcon.setImage(new Image("/images/gray_user.png"));
				userIcon2.setImage(new Image("/images/gray_user.png"));
				userIcon3.setImage(new Image("/images/gray_user.png"));
			}
		}	
    }
    
    private class PasswordIconChangeListener implements ChangeListener<Boolean>{

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

			if(newValue) {
				lockIcon.setImage(new Image("/images/blue_lock.png"));
				lockIcon2.setImage(new Image("/images/blue_lock.png"));
			}
			else {
				lockIcon.setImage(new Image("/images/gray_lock.png"));
				lockIcon2.setImage(new Image("/images/gray_lock.png"));
			}
		}	
    }
    
    private class EmailIconChangeListener implements ChangeListener<Boolean>{

 		@Override
 		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

 			if(newValue)
 				emailIcon.setImage(new Image("/images/blue_email.png"));
 			else
 				emailIcon.setImage(new Image("/images/gray_email.png"));
 		}
    }

	
}
