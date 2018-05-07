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

import application.LoginManager;
import application.model.Account;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainLoginController{
	
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
	private String cssLoginManager = this.getClass().getResource("LoginManager.css").toExternalForm();
	private String cssGreenSnackbar = this.getClass().getResource("GreenSnackbar.css").toExternalForm();
	private String cssRedSnackbar = this.getClass().getResource("RedSnackbar.css").toExternalForm();
	private Account userAccount;
	
	
	public void initialize(){
		
		signInPane.toFront();
		
		closeButton.setGraphic(new ImageView(new Image("/images/close.png")));
		minButton.setGraphic(new ImageView(new Image("/images/min.png")));
		
		textArea.setEditable(false);
    	textArea.setFocusTraversable(false);
    	textArea.setMouseTransparent(false);
    	
    	Image blueUser = new Image("/images/blue_user.png");
    	Image grayUser = new Image("/images/gray_user.png");
    	
    	JFXTextField[] userFields = {signInUsername, signUpUsername, forgotUsername};
    	ImageView[] userIcons = {userIcon, userIcon2, userIcon3};
    	
    	for(int i = 0; i < userFields.length; i++) {
    		//variable used in inner class must be final
    		final int j = i;
    		userFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
    			if(newValue)
    				userIcons[j].setImage(blueUser);
    			else
    				userIcons[j].setImage(grayUser);
    		});
    	}
    	
    	Image blueLock = new Image("/images/blue_lock.png");
    	Image grayLock = new Image("/images/gray_lock.png");
    	
    	JFXPasswordField[] passwordFields = {signInPassword, signUpPassword};
    	ImageView[] lockIcons = {lockIcon, lockIcon2};
    	
    	for(int i = 0; i < passwordFields.length; i++) {
    		final int j = i;
    		passwordFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
    			if(newValue)
    				lockIcons[j].setImage(blueLock);
    			else
    				lockIcons[j].setImage(grayLock);
    		});
    	}
    	
    	Image blueEmail = new Image("/images/blue_email.png");
    	Image grayEmail = new Image("/images/gray_email.png");
    	
    	signUpEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
    		if(newValue)
    			emailIcon.setImage(blueEmail);
    		else
    			emailIcon.setImage(grayEmail);
    	});
    }
    
	/**
     * 
     * 
     */
    
    @FXML
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
    
    @FXML
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
    	
    	JFXSnackbar signUpSnackbar = new JFXSnackbar(signUpPane);
    	
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
    
    private double startX = 0;
    private double startY = 0;
    
    private void switchScene(ActionEvent event) throws IOException{
    
    	Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("MenuOption.fxml"));
    	AnchorPane root = loader.load();
    
    	root.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				 startX = currentStage.getX() - event.getScreenX();
				 startY = currentStage.getY() - event.getScreenY();
			}
		});
		
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				currentStage.setX(event.getScreenX() +  startX);
				currentStage.setY(event.getScreenY() + startY);
			}
		});
		
		Scene menuScene = new Scene(root);
    	
    	//access new controller and pass account object to it
    	MenuOptionController controller = loader.getController();
    	controller.init_Username(userAccount);
    	
    	currentStage.setScene(menuScene);
		currentStage.show();
    }
    
    /**
     * @param style - set the style of the snackbar with the red or green css file
     */
    
    private void setSnackbarStyle(String style) {
    	Scene scene = rootAnchor.getScene();
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
    	forgotUsername.clear();
    }
}
