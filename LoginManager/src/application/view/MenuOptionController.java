/**
 * 
 */

package application.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
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
    private JFXButton addNewButton;

    @FXML
    private Label addNewLabel, retrieveLabel, updateLabel, deleteLabel;

    @FXML
    private Pane displayPane, helpPane, addNewPane;

    @FXML
    private ImageView displayLock, arrow1, arrow2, arrow3, arrow4;
   
    @FXML
    private JFXTextField websiteFieldA, usernameFieldA;
    
    @FXML
    private JFXPasswordField passwordFieldA;
    
    public void initialize(){
    	
    	displayPane.toFront();
    }

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
    	
    	if(event.getSource() == exitMenuButton)
    		System.exit(0);
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
