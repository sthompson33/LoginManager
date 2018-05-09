/**
 * 
 */

package application.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextField;

import application.LoginManager;
import application.model.Account;
import application.model.LoginInformation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuOptionController {


    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private Button closeButton, minButton;

    @FXML
    private VBox menuBox;

    //JFXButtons on the menuBox 
    @FXML
    private JFXButton addNewMenuButton, retrieveMenuButton, updateMenuButton, deleteMenuButton, signOutButton
    ;
    
    //JFXButtons on each corresponding pane
    @FXML 
    private JFXButton addNewButton, retrieveButton, updateButton, deleteButton;

    @FXML
    private Label usernameResult, passwordResult;

    @FXML
    private Pane displayPane, helpPane, addNewPane, retrievePane, updatePane, deletePane;

    @FXML
    private ImageView userIconA, userIconU, lockIconA, lockIconU, webIconA, webIconD, webIconR, webIconU;
   
    @FXML
    private JFXTextField addWebsiteField, addUsernameField, retrieveWebsiteField, updateWebsiteField, updateUsernameField, deleteWebsiteField;
    
    @FXML
    private JFXPasswordField addPasswordField, updatePasswordField;
    
    @FXML
    private JFXCheckBox usernameCheckBox, passwordCheckBox;
    
    private ArrayList<TextField> textFieldList = new ArrayList<TextField>(3);
    private String cssLoginManager = this.getClass().getResource("../view/LoginManager.css").toExternalForm();
	private String cssGreenSnackbar = this.getClass().getResource("../view/GreenSnackbar.css").toExternalForm();
	private String cssRedSnackbar = this.getClass().getResource("../view/RedSnackbar.css").toExternalForm();
    
    public void initialize(){
    	
    	displayPane.toFront();
    	
    	closeButton.setGraphic(new ImageView(new Image("/images/close.png")));
		minButton.setGraphic(new ImageView(new Image("/images/min.png")));
		
		JFXTextField[] webFields = {addWebsiteField, retrieveWebsiteField, updateWebsiteField, deleteWebsiteField};
    	ImageView[] webIcons = {webIconA, webIconR, webIconU, webIconD};
    	
    	for(int i = 0; i < webFields.length; i++) {
    		final int j = i;
    		webFields[i].focusedProperty().addListener((observable, oldValue, newValue) ->{
    			if(newValue)
    				webIcons[j].setImage(new Image("/images/blue_web.png"));
    			else
    				webIcons[j].setImage(new Image("/images/gray_web.png"));
    		});
    	}
    	
    	JFXTextField[] userFields = {addUsernameField, updateUsernameField};
    	ImageView[] userIcons = {userIconA, userIconU};
    	
    	for(int i = 0; i < userFields.length; i++) {
    		final int j = i;
    		userFields[i].focusedProperty().addListener((observable, oldValue, newValue) ->{
    			if(newValue)
    				userIcons[j].setImage(new Image("/images/blue_user.png"));
    			else
    				userIcons[j].setImage(new Image("/images/gray_user.png"));
    		});
    	}
    	
    	JFXPasswordField[] passwordFields = {addPasswordField, updatePasswordField};
    	ImageView[] lockIcons = {lockIconA, lockIconU};
    	
    	for(int i = 0; i < passwordFields.length; i++) {
    		final int j = i;
    		passwordFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
    			if(newValue)
    				lockIcons[j].setImage(new Image("/images/blue_lock.png"));
    			else
    				lockIcons[j].setImage(new Image("/images/gray_lock.png"));
    		});
    	}
    }
    
    /**
     * 
     * 
     */
    
    public void init_Username(Account account) {
    	
    	LoginInformation.setFileName(account.getUsername());
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void switchPaneListener(ActionEvent event){
    	
    	clearFields();
    	
    	if(event.getSource() == addNewMenuButton) {
    		addNewPane.toFront();
    		addWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == retrieveMenuButton) {
    		retrievePane.toFront();
    		retrieveWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == updateMenuButton) {
    		updatePane.toFront();
    		updateWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == deleteMenuButton) {
    		deletePane.toFront();
    		deleteWebsiteField.requestFocus();
    	}
    }
    
    /**
     * 
     * 
     */
    
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
    public void addNewListener() {
    	
    	JFXSnackbar addNewSnackbar = new JFXSnackbar(addNewPane);
    	
    	textFieldList.clear();
    	textFieldList.add(addWebsiteField);
    	textFieldList.add(addUsernameField);
    	textFieldList.add(addPasswordField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		if(LoginInformation.findWebsite(addWebsiteField.getText())) {
    			clearFields();
        		addWebsiteField.requestFocus();
    			setSnackbarStyle(cssRedSnackbar);
    			addNewSnackbar.enqueue(new SnackbarEvent("Website Already Exists"));
    		}else {
    			LoginInformation.addNewLogin(addWebsiteField.getText(), addUsernameField.getText(), addPasswordField.getText());
        		clearFields();
        		addWebsiteField.requestFocus();
        		setSnackbarStyle(cssGreenSnackbar);
        		addNewSnackbar.enqueue(new SnackbarEvent("Information Added Successfully!"));
    		}
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void retrieveListener() {
    	
    	JFXSnackbar retrieveSnackbar = new JFXSnackbar(retrievePane);
    	
    	textFieldList.clear();
    	textFieldList.add(retrieveWebsiteField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		if(LoginInformation.findWebsite(retrieveWebsiteField.getText())){
    			String[] login = LoginInformation.retrieveLogin();
        		usernameResult.setText(login[0]);
        		passwordResult.setText(login[1]);
    		}
    		else {
    			clearFields();
    			retrieveWebsiteField.requestFocus();
    			usernameResult.setText("");
    			passwordResult.setText("");
    			setSnackbarStyle(cssRedSnackbar);
    			retrieveSnackbar.enqueue(new SnackbarEvent("Website Not Found"));
    		}
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void updateListener() {
    	
    	JFXSnackbar updateSnackbar = new JFXSnackbar(updatePane);
    	
    	textFieldList.clear();
    	textFieldList.add(updateWebsiteField);
    	
    	if(usernameCheckBox.isSelected())
    		textFieldList.add(updateUsernameField);
    	
    	if(passwordCheckBox.isSelected())
    		textFieldList.add(updatePasswordField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		if(!usernameCheckBox.isSelected() && !passwordCheckBox.isSelected()) {
        		setSnackbarStyle(cssRedSnackbar);
        		updateSnackbar.enqueue(new SnackbarEvent("Must Select a Username or Password to Update"));
        	}
    		else if(LoginInformation.findWebsite(updateWebsiteField.getText())) {
    			LoginInformation.updateLogin(updateUsernameField.getText(), updatePasswordField.getText());
    			clearFields();
    			usernameCheckBox.setSelected(false);
    			passwordCheckBox.setSelected(false);
    			updateUsernameField.setVisible(false);
    			userIconU.setVisible(false);
    			updatePasswordField.setVisible(false);
    			lockIconU.setVisible(false);
    			updateWebsiteField.requestFocus();
        		setSnackbarStyle(cssGreenSnackbar);
        		updateSnackbar.enqueue(new SnackbarEvent("Information Updated Successfully!"));
    		}
    		else {
    			clearFields();
    			updateWebsiteField.requestFocus();
    			setSnackbarStyle(cssRedSnackbar);
        		updateSnackbar.enqueue(new SnackbarEvent("Website Not Found"));
    		}
    	}
    }
    
    @FXML
    public void checkBoxListener(){
    	
    	if(usernameCheckBox.isSelected()){
    		updateUsernameField.setVisible(true);
    		userIconU.setVisible(true);
    	}
    	else{
    		updateUsernameField.setVisible(false);
    		userIconU.setVisible(false);
    	}
    	
    	if(passwordCheckBox.isSelected()){
    		updatePasswordField.setVisible(true);
    		lockIconU.setVisible(true);
    	}
    	else{
    		updatePasswordField.setVisible(false);
    		lockIconU.setVisible(false);
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void deletListener() {
    	
    	JFXSnackbar deleteSnackbar = new JFXSnackbar(deletePane);
    	
    	textFieldList.clear();
    	textFieldList.add(deleteWebsiteField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		if(LoginInformation.findWebsite(deleteWebsiteField.getText())) {
    			LoginInformation.deleteLogin();
        		clearFields();
        		deleteWebsiteField.requestFocus();
        		setSnackbarStyle(cssGreenSnackbar);
        		deleteSnackbar.enqueue(new SnackbarEvent("Information Deleted Successfully!"));
    		}
    		else {
    			clearFields();
        		deleteWebsiteField.requestFocus();
    			setSnackbarStyle(cssRedSnackbar);
    			deleteSnackbar.enqueue(new SnackbarEvent("Website Not Found"));
    		}
    		
    	}
    }
    
    /**
     * 
     * 
     */
    
    private double startX = 0;
    private double startY = 0;
    
    @FXML
    public void switchSceneListener(ActionEvent event) throws IOException{
    	
    	Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
    	AnchorPane root = FXMLLoader.load(getClass().getResource("../view/MainLogin.fxml"));
    	
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
				currentStage.setX(event.getScreenX() + startX);
				currentStage.setY(event.getScreenY() + startY);
			}
		});
    	
		Scene mainScene = new Scene(root);
		
		currentStage.setScene(mainScene);
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
    			tf.getStyleClass().addAll("text-field", "text-input", "jfx-text-field", "password-field", "jfx-password-field");    		}
    	}
    	
    	return emptyInput;
    }
    
    private void clearFields() {
    	addWebsiteField.clear();
    	addUsernameField.clear();
    	addPasswordField.clear();
    	retrieveWebsiteField.clear();
    	usernameResult.setText("");
    	passwordResult.setText("");
    	updateWebsiteField.clear();
    	updateUsernameField.clear();
    	updatePasswordField.clear();
    	deleteWebsiteField.clear();
    }
 }
