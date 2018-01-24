package homeScene;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainLoginController {

    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private AnchorPane displayAnchor;

    @FXML
    private ImageView displayImage;

    @FXML
    private AnchorPane signUpAnchor;

    @FXML
    private Button select_SignIn;

    @FXML
    private JFXTextField usernameField2;

    @FXML
    private ImageView userIcon2;

    @FXML
    private JFXPasswordField passwordField2;

    @FXML
    private ImageView lockIcon2;

    @FXML
    private JFXTextField emailField;

    @FXML
    private ImageView emailIcon;

    @FXML
    private JFXButton signUpButton;

    @FXML
    private AnchorPane forgotAnchor;

    @FXML
    private Button backButton;

    @FXML
    private JFXTextField retrieveField;

    @FXML
    private ImageView userIcon3;

    @FXML
    private JFXButton retrieveButton;

    @FXML
    private AnchorPane signInAnchor;

    @FXML
    private Button select_SignUp;

    @FXML
    private JFXTextField usernameField;

    @FXML
    private ImageView userIcon;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private ImageView lockIcon;

    @FXML
    private Button forgotButton;

    @FXML
    private JFXButton signInButton;

    @FXML
    private JFXButton exitButton;
    
    @FXML
    private TextArea textArea;

    private ImageView backArrow = new ImageView("/images/backArrow.png");
    
    public void initialize(){
    	
    	backButton.setGraphic(backArrow);
    	signInAnchor.toFront();
    	
    	textArea.setEditable(false);
    	textArea.setFocusTraversable(false);
    	textArea.setMouseTransparent(false);
    }
    
    /**
     * 
     */
    @FXML
    void switchPaneListener(ActionEvent event) {
    	
    	if(event.getSource() == select_SignUp)
    		signUpAnchor.toFront();
    	
    	if(event.getSource() == select_SignIn)
    		signInAnchor.toFront();
    	
    	if(event.getSource() == forgotButton)
    		forgotAnchor.toFront();
    	
    	if(event.getSource() == backButton)
    		signInAnchor.toFront();
    	
    	if(event.getSource() == exitButton)
    		System.exit(0);
    }

 }
