/**
 * 
 */

package application.view;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import application.model.Account;
import application.model.LoginInformation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuOptionController {


    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private JFXToggleButton helpToggle;

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
    
    private ArrayList<TextField> textFieldList = new ArrayList<TextField>(3);
   
    public void initialize(){
    	
    	displayPane.toFront();
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
    	
    	helpToggle.setSelected(false);
    	
    	if(event.getSource() == addNewMenuButton) {
    		addNewPane.toFront();
    		addWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == retrieveMenuButton) {
    		retrievePane.toFront();
    		retrieveWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == updateMenuButton)
    		updatePane.toFront();
    	
    	if(event.getSource() == deleteMenuButton) {
    		deletePane.toFront();
    		deleteWebsiteField.requestFocus();
    	}
    	
    	if(event.getSource() == exitMenuButton)
    		System.exit(0);
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void addNewListener() {
    	
    	textFieldList.clear();
    	textFieldList.add(addWebsiteField);
    	textFieldList.add(addUsernameField);
    	textFieldList.add(addPasswordField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		LoginInformation.addNewLogin(addWebsiteField.getText(), addUsernameField.getText(), addPasswordField.getText());
    		clearFields();
    		addWebsiteField.requestFocus();
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void retrieveListener() {
    	
    	textFieldList.clear();
    	textFieldList.add(retrieveWebsiteField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		String[] login = LoginInformation.retrieveLogin(retrieveWebsiteField.getText());
    		usernameResult.setText(login[0]);
    		passwordResult.setText(login[1]);
    		clearFields();
    		retrieveWebsiteField.requestFocus();
    	}
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void updateListener() {
    	
    	textFieldList.clear();
    	textFieldList.add(updateWebsiteField);
    	
    	if(usernameCheckBox.isSelected())
    		textFieldList.add(updateUsernameField);
    	
    	if(passwordCheckBox.isSelected())
    		textFieldList.add(updatePasswordField);
    	
    	if(!usernameCheckBox.isSelected() && !passwordCheckBox.isSelected())
    		JOptionPane.showMessageDialog(null, "you must select username or password to update");
    	else {
    		if(!checkForEmptyInput(textFieldList)) {
    			LoginInformation.updateLogin(updateWebsiteField.getText(), updateUsernameField.getText(), updatePasswordField.getText());
    			clearFields();
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
    	
    	textFieldList.clear();
    	textFieldList.add(deleteWebsiteField);
    	
    	if(!checkForEmptyInput(textFieldList)) {
    		LoginInformation.deleteLogin(deleteWebsiteField.getText());
    		clearFields();
    		deleteWebsiteField.requestFocus();
    	}
    }
    
    /**
     * 
     * 
     */

    @FXML
    public void toggleListener(){
    	
    	if(helpToggle.isSelected())
    		helpPane.toFront();
    	else
    		displayPane.toFront();
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
    
    public void clearFields() {
    	addWebsiteField.clear();
    	addUsernameField.clear();
    	addPasswordField.clear();
    	retrieveWebsiteField.clear();
    	updateWebsiteField.clear();
    	updateUsernameField.clear();
    	updatePasswordField.clear();
    	deleteWebsiteField.clear();
    }
}
