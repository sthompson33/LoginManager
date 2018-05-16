/**
 * @author Stephen
 *
 */

package application.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextField;

import application.LoginManager;
import application.model.Account;

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

public class MainLoginController extends ControllerUtilities{
	
	@FXML
	private AnchorPane rootAnchor;

	@FXML
	private Pane signInPane, signUpPane, forgotPane;

	@FXML
	private Button select_SignIn, select_SignUp, forgotButton, closeButton, minButton;

	@FXML
	private JFXTextField signInUsername, signUpUsername, signUpEmail, forgotUsername;

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
		
		closeButton.setGraphic(new ImageView(new Image("/images/close.png")));
		minButton.setGraphic(new ImageView(new Image("/images/min.png")));
		
		textArea.setEditable(false);
    	textArea.setFocusTraversable(false);
    	textArea.setMouseTransparent(false);
    	
    	JFXTextField[] userFields = {signInUsername, signUpUsername, forgotUsername};
    	ImageView[] userIcons = {userIcon, userIcon2, userIcon3};
    	
    	for(int i = 0; i < userFields.length; i++) {
    		//variable used in inner class must be final
    		final int j = i;
    		userFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
    			if(newValue)
    				userIcons[j].setImage(new Image("/images/blue_user.png"));
    			else
    				userIcons[j].setImage(new Image("/images/gray_user.png"));
    		});
    	}
    	
    	JFXPasswordField[] passwordFields = {signInPassword, signUpPassword};
    	ImageView[] lockIcons = {lockIcon, lockIcon2};
    	
    	for(int i = 0; i < passwordFields.length; i++) {
    		final int j = i;
    		passwordFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
    			if(newValue)
    				lockIcons[j].setImage(new Image("/images/blue_lock.png"));
    			else
    				lockIcons[j].setImage(new Image("/images/gray_lock.png"));
    		});
    	}
    	
    	signUpEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue)
    			emailIcon.setImage(new Image("/images/blue_email.png"));
    		else
    			emailIcon.setImage(new Image("/images/gray_email.png"));
    	});
    }

	/**
     * 
     * 
     */
	
	public void titleButtonListener(ActionEvent event) {
    	
    	if(event.getSource() == closeButton) {
    		System.exit(0);
    	}
    	
    	if(event.getSource() == minButton) {
    		LoginManager.getStage().setIconified(true);
    	}
    }
	
    /**
     * 
     * 
     */
    
    @FXML
    public void signInListener(ActionEvent event) throws IOException {
    	
    	JFXSnackbar signInSnackbar = new JFXSnackbar(signInPane);
    	
    	textFieldList.clear();
    	textFieldList.add(signInUsername);
    	textFieldList.add(signInPassword);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account(signInUsername.getText(), signInPassword.getText());
    		if(userAccount.findAccount()) {
    			switchScene(event);
    		}
    		else {
    			setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
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
    	
    	JFXSnackbar signUpSnackbar = new JFXSnackbar(signUpPane);
    	
    	textFieldList.clear();
    	textFieldList.add(signUpUsername);
    	textFieldList.add(signUpPassword);
    	textFieldList.add(signUpEmail);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account(signUpUsername.getText(), signUpPassword.getText(), signUpEmail.getText());
    		if(userAccount.verifyEmail(signUpEmail.getText())) {
    			if(!userAccount.findAccount()) {
    				userAccount.createAccount();
    				switchScene(event);
    			}
    			else {
    				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
    				signUpSnackbar.enqueue(new SnackbarEvent("Account Already Exists"));
    			}
    		}
    		else {
    			setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
    			signUpSnackbar.enqueue(new SnackbarEvent("Please Use a Gmail Address"));
    			signUpEmail.requestFocus();
    		}
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void forgotListener(){
    	 
    	JFXSnackbar forgotSnackbar = new JFXSnackbar(forgotPane);
    	
    	textFieldList.clear();
    	textFieldList.add(forgotUsername);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		userAccount = new Account();
    		//see if username exists, then proceed to send email
    		if(userAccount.findUsername(forgotUsername.getText())) {
    			userAccount.sendEmail();
    			setSnackbarStyle(CSS_GREEN_SNACKBAR, rootAnchor);
    			forgotSnackbar.enqueue(new SnackbarEvent("Email Sent!"));
    		}
    		else {
	    		setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
    			forgotSnackbar.enqueue(new SnackbarEvent("Username Not Found"));
    		}
    	}
    }
    
    /**
     * 
     * 
     */
    
    private double startX = 0;
    private double startY = 0;

    @Override
    public void switchScene(ActionEvent event) throws IOException{
       
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../view/MenuOption.fxml"));
    	AnchorPane root = loader.load();
    
    	root.setOnMousePressed((e) -> {
    		startX = currentStage.getX() - e.getScreenX();
    		startY = currentStage.getY() - e.getScreenY();
    	});
    	
    	root.setOnMouseDragged((e) -> {
    		currentStage.setX(e.getScreenX() + startX);
    		currentStage.setY(e.getScreenY() + startY);
    	});
		
		Scene menuScene = new Scene(root);
    	
    	//access MenuOptionController and pass current Account object 
    	MenuOptionController controller = loader.getController();
    	controller.init_Username(userAccount);
    	
    	currentStage.setScene(menuScene);
		currentStage.show();
    }
    
    @Override
	public void switchPaneListener(ActionEvent event) {
		
		clearFields();
    
    	if(event.getSource() == select_SignIn) {
    		signInPane.toFront();
    		signInUsername.requestFocus();
    	}
    	
    	if(event.getSource() == forgotButton) {
    		forgotPane.toFront();
    		forgotUsername.requestFocus();
    	}
    	
    	if(event.getSource() == select_SignUp) {
    		signUpPane.toFront();
    		signUpUsername.requestFocus();
    	}
    	
    	if(event.getSource() == backButton) {
    		signInPane.toFront();
    		signInUsername.requestFocus();
    	}
	}

	@Override
	public void clearFields() {
		signInUsername.clear();
    	signInPassword.clear();
    	signUpUsername.clear();
    	signUpPassword.clear();
    	signUpEmail.clear();
    	forgotUsername.clear();
	}
}
