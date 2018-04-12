/**
 * 
 */

package application.view;

import java.io.IOException;
import java.util.ArrayList;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbar.SnackbarEvent;
import com.jfoenix.controls.JFXTextField;

import application.model.Account;
import application.model.LoginInformation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuOptionController {


    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private Button signOutButton;

    @FXML
    private VBox menuBox;

    //JFXButtons on the menuBox 
    @FXML
    private JFXButton addNewMenuButton, retrieveMenuButton, updateMenuButton, deleteMenuButton, exitMenuButton;
    
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
    
    private JFXSnackbar addNewSnackbar, retrieveSnackbar, updateSnackbar, deleteSnackbar;
    private WebIconChangeListener webListener = new WebIconChangeListener();
    private UserIconChangeListener userListener = new UserIconChangeListener();
    private PasswordIconChangeListener passListener = new PasswordIconChangeListener();
    private ArrayList<TextField> textFieldList = new ArrayList<TextField>(3);
    private String cssLoginManager = this.getClass().getResource("LoginManager.css").toExternalForm();
	private String cssGreenSnackbar = this.getClass().getResource("GreenSnackbar.css").toExternalForm();
	private String cssRedSnackbar = this.getClass().getResource("RedSnackbar.css").toExternalForm();
    private Scene scene;
   
    public void initialize(){
    	
    	displayPane.toFront();
    	
    	addNewSnackbar = new JFXSnackbar(addNewPane);
    	retrieveSnackbar = new JFXSnackbar(retrievePane);
    	updateSnackbar = new JFXSnackbar(updatePane);
    	deleteSnackbar = new JFXSnackbar(deletePane);
    	
    	addWebsiteField.focusedProperty().addListener(webListener);
    	retrieveWebsiteField.focusedProperty().addListener(webListener);
    	updateWebsiteField.focusedProperty().addListener(webListener);
    	deleteWebsiteField.focusedProperty().addListener(webListener);
    	
    	addUsernameField.focusedProperty().addListener(userListener);
    	updateUsernameField.focusedProperty().addListener(userListener);
    	
    	addPasswordField.focusedProperty().addListener(passListener);
    	updatePasswordField.focusedProperty().addListener(passListener);
    }
    
    /**
     * 
     * 
     */
    
    public void init_Username(Account account) {
    	
    	LoginInformation.fileName = account.getUsername();
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void switchPaneListener(ActionEvent event){
    	
    	if(event.getSource() == addNewMenuButton) {
    		clearFields();
    		addNewPane.toFront();
    		addWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == retrieveMenuButton) {
    		clearFields();
    		retrievePane.toFront();
    		retrieveWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == updateMenuButton) {
    		clearFields();
    		updatePane.toFront();
    		updateWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == deleteMenuButton) {
    		clearFields();
    		deletePane.toFront();
    		deleteWebsiteField.requestFocus();
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void addNewListener() {
    	
    	getScene();
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
    	
    	getScene();
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
    	
    	getScene();
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
    	
    	getScene();
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
    
    @FXML
    public void switchSceneListener(ActionEvent event) throws IOException{
    	
    	AnchorPane root = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
		Scene mainScene = new Scene(root);
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
    }
    
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
    
    /**
     * 
     * 
     */
    
    private class WebIconChangeListener implements ChangeListener<Boolean>{

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			
			if(newValue) {
				webIconA.setImage(new Image("/images/blue_web.png"));
				webIconR.setImage(new Image("/images/blue_web.png"));
				webIconU.setImage(new Image("/images/blue_web.png"));
				webIconD.setImage(new Image("/images/blue_web.png"));
			}
			else {
				webIconA.setImage(new Image("/images/gray_web.png"));
				webIconR.setImage(new Image("/images/gray_web.png"));
				webIconU.setImage(new Image("/images/gray_web.png"));
				webIconD.setImage(new Image("/images/gray_web.png"));
			}
		}
    }
    
    private class UserIconChangeListener implements ChangeListener<Boolean>{

		@Override
		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

			if(newValue) {
				userIconA.setImage(new Image("/images/blue_user.png"));
				userIconU.setImage(new Image("/images/blue_user.png"));
			}
			else {
				userIconA.setImage(new Image("/images/gray_user.png"));
				userIconU.setImage(new Image("/images/gray_user.png"));
			}
		}	
    }
    
    private class PasswordIconChangeListener implements ChangeListener<Boolean>{

  		@Override
  		public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

  			if(newValue) {
  				lockIconA.setImage(new Image("/images/blue_lock.png"));
  				lockIconU.setImage(new Image("/images/blue_lock.png"));
  			}
  			else {
  				lockIconA.setImage(new Image("/images/gray_lock.png"));
  				lockIconU.setImage(new Image("/images/gray_lock.png"));
  			}
  		}	
    }
}
