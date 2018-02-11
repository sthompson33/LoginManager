/**
 * 
 */

package application.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    public void initialize(){
    
    	displayPane.toFront();
    }
    
    /**
     * 
     */

    public void toggleListener(){
    	
    	if(helpToggle.isSelected())
    		helpPane.toFront();
    	else
    		displayPane.toFront();
    }
    
    /**
     * 
     */
    
    public void menuOptionListener(ActionEvent event){
    	
    	helpToggle.setSelected(false);
    	
    	if(event.getSource() == addNewMenuButton)
    		addNewPane.toFront();
    	
    	if(event.getSource() == retrieveMenuButton)
    		retrievePane.toFront();
    	
    	if(event.getSource() == updateMenuButton)
    		updatePane.toFront();
    	
    	if(event.getSource() == deleteMenuButton)
    		deletePane.toFront();
    	
    	if(event.getSource() == exitMenuButton)
    		System.exit(0);
    }
    
    /**
     * 
     */
    
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
     */
    
    public void switchSceneListener(ActionEvent event) throws IOException{
    	
    	AnchorPane root = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
		Scene mainScene = new Scene(root);
		Stage mainStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		mainStage.setScene(mainScene);
		mainStage.show();
    }
}
