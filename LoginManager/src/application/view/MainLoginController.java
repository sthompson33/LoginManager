/**
 * 
 */

package application.view;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
	private JFXTextField signInUsername, signUpUsername, signUpEmail, retrieveField;

	@FXML
	private JFXPasswordField signInPassword, signUpPassword;

	@FXML
	private TextArea textArea;
	    
	@FXML
	private JFXButton signUpButton, signInButton, retrieveButton, exitButton, backButton;

	@FXML
	private ImageView displayImage, userIcon, userIcon2, userIcon3, lockIcon, lockIcon2, emailIcon;

	public void initialize(){
    	
		signInPane.toFront();
    	
    	textArea.setEditable(false);
    	textArea.setFocusTraversable(false);
    	textArea.setMouseTransparent(false);
    }
    
    /**
     * 
     */
    
    @FXML
    public void switchPaneListener(ActionEvent event) {
    	
    	if(event.getSource() == select_SignUp)
    		signUpPane.toFront();
    	
    	if(event.getSource() == select_SignIn)
    		signInPane.toFront();
    	
    	if(event.getSource() == forgotButton)
    		forgotPane.toFront();
    	
    	if(event.getSource() == backButton)
    		signInPane.toFront();
    	
    	if(event.getSource() == exitButton)
    		System.exit(0);
    }
    
    /**
     * 
     * 
     */
    
    @FXML
    public void signInListener(ActionEvent event) throws IOException {
    	
    	
    	//check correct info here, then call switchScene()
    	switchScene(event);
    }

    @FXML
    public void signUpListener(ActionEvent event) throws IOException {
    	
    	//check correct info here, then call switchScene()
    	switchScene(event);

    }
    
    private void switchScene(ActionEvent event) throws IOException{
    	
    	AnchorPane root = FXMLLoader.load(getClass().getResource("MenuOption.fxml"));
		Scene menuScene = new Scene(root);
		Stage menuStage = (Stage)((Node)event.getSource()).getScene().getWindow();
		menuStage.setScene(menuScene);
		menuStage.show();
    }

 }
