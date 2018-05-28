/**
 * @author Stephen Thompson
 * 
 * <pre> Controller Class for MainLogin.fxml file located in application.view.
 * Methods included mostly consist of listeners that act once a specific button is pushed.
 * Account object is used to call methods that handle the data entered and carry out the desired action.</pre>
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

public class MainLoginController extends ControllerUtilities {

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

	// called when class is first loaded
	public void initialize() {

		signInPane.toFront();
		userAccount = new Account();

		closeButton.setGraphic(new ImageView(new Image("/images/close.png")));
		minButton.setGraphic(new ImageView(new Image("/images/min.png")));

		textArea.setEditable(false);
		textArea.setFocusTraversable(false);
		textArea.setMouseTransparent(false);

		/*
		 * Following for loops add changelistener via lambda expressions to textfields.
		 * Once a textfield recieves focus the corresponding icon changes color to
		 * match.
		 */
		JFXTextField[] userFields = { signInUsername, signUpUsername, forgotUsername };
		ImageView[] userIcons = { userIcon, userIcon2, userIcon3 };

		for (int i = 0; i < userFields.length; i++) {
			final int j = i; // variable used in inner class must be final
			userFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue)
					userIcons[j].setImage(new Image("/images/blue_user.png"));
				else
					userIcons[j].setImage(new Image("/images/gray_user.png"));
			});
		}

		JFXPasswordField[] passwordFields = { signInPassword, signUpPassword };
		ImageView[] lockIcons = { lockIcon, lockIcon2 };

		for (int i = 0; i < passwordFields.length; i++) {
			final int j = i;
			passwordFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue)
					lockIcons[j].setImage(new Image("/images/blue_lock.png"));
				else
					lockIcons[j].setImage(new Image("/images/gray_lock.png"));
			});
		}

		signUpEmail.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue)
				emailIcon.setImage(new Image("/images/blue_email.png"));
			else
				emailIcon.setImage(new Image("/images/gray_email.png"));
		});
	}

	/**
	 * <pre>
	 * Listener for close and minimize buttons on GUI. StageStyle has been set to UNDECORATED.
	 * If the close button was pressed, program will close. If the minimize button was pressed,
	 * the program will call the static getStage method from the LoginManager class and setIconified to true.
	 * This will minimize the program to the system tray.
	 * </pre>
	 * 
	 * @param event
	 *            - an ActionEvent object that was raised when either close or
	 *            minimize button was pressed
	 */
	public void titleButtonListener(ActionEvent event) {

		if (event.getSource() == closeButton) {
			System.exit(0);
		}

		if (event.getSource() == minButton) {
			LoginManager.getStage().setIconified(true);
		}
	}

	/**
	 * <pre>
	 * Listener for signInButton on signInPane. All fields will be added to a list to be checked for empty input. 
	 * If all fields contain data, findAccout from Accout class is called. This will search the accounts file for a match 
	 * and if found, switch scenes. If not found a toast message will show that the sign in attempt failed.
	 * </pre>
	 * 
	 * @param event
	 *            - an ActionEvent object that was raised by signInButton, this
	 *            event is passed to switchScene method
	 * @throws IOException
	 *             - possible exception thrown from switchScene call
	 */
	@FXML
	public void signInListener(ActionEvent event) throws IOException {

		JFXSnackbar signInSnackbar = new JFXSnackbar(signInPane);

		textFieldList.clear();
		textFieldList.add(signInUsername);
		textFieldList.add(signInPassword);

		if (!checkForEmptyInput(textFieldList)) {
			if (userAccount.findAccount(signInUsername.getText(), signInPassword.getText())) {
				switchScene(event);
			} else {
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				signInSnackbar.enqueue(new SnackbarEvent("Incorrect Username or Password"));
			}
		}
	}

	/**
	 * <pre>
	 * Listener for signUpButton on signUpPane. All fields will be added to a list to be checked for empty input.
	 * If all fields contain data, verifyEmail will be called to check that email entered is supported. If email domain not 
	 * supported, toast message will show that a Gmail address is needed. If Gmail address was entered then findAccount will be
	 * called to be sure there is no matching Account already on file. If a match is found, a toast message will show that
	 * the current Account information entered already exists. If no match found, createAccount will be called so that the
	 * information entered can be added to the Accounts file and a specific file for the username will be created.
	 * Once this has finished, switchScene will be called.
	 * </pre>
	 * 
	 * @param event
	 *            - an ActionEvent object that was raised by signUpButton, this
	 *            event is passed to switchScene method
	 * @throws IOException
	 *             - possible exception thrown from switchScene call
	 */
	@FXML
	public void signUpListener(ActionEvent event) throws IOException {

		String username = signUpUsername.getText();
		String password = signUpPassword.getText();
		String email = signUpEmail.getText();

		JFXSnackbar signUpSnackbar = new JFXSnackbar(signUpPane);

		textFieldList.clear();
		textFieldList.add(signUpUsername);
		textFieldList.add(signUpPassword);
		textFieldList.add(signUpEmail);

		if (!checkForEmptyInput(textFieldList)) {
			if (userAccount.verifyEmail(email)) {
				if (!userAccount.findAccount(username, password)) {
					userAccount.createAccount(username, password, email);
					switchScene(event);
				} else {
					setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
					signUpSnackbar.enqueue(new SnackbarEvent("Account Already Exists"));
				}
			} else {
				signUpEmail.requestFocus();
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				signUpSnackbar.enqueue(new SnackbarEvent("Please Use a Gmail Address"));
			}
		}
	}

	/**
	 * <pre>
	 * Listener for retrieveButton on forgotPane. forgotUsername field will be checked for empty input first. If it 
	 * contains data, findUsername called to see if username exist on file. A toast message will show if the username was not found. 
	 * If username does exist, the username, password and email will be set in the Account class and an email will be sent to the
	 * registered email address containing the forgotten password.
	 * </pre>
	 */
	@FXML
	public void forgotListener() {

		JFXSnackbar forgotSnackbar = new JFXSnackbar(forgotPane);

		textFieldList.clear();
		textFieldList.add(forgotUsername);

		if (!checkForEmptyInput(textFieldList)) {
			if (userAccount.findUsername(forgotUsername.getText())) {
				userAccount.sendEmail();
				setSnackbarStyle(CSS_GREEN_SNACKBAR, rootAnchor);
				forgotSnackbar.enqueue(new SnackbarEvent("Email Sent!"));
			} else {
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				forgotSnackbar.enqueue(new SnackbarEvent("Username Not Found"));
			}
		}
	}

	// startX, startY will represent in switchScene what the x and y values are when
	// the mouse was first pressed.
	// these are stored as class variables because of the scope needed as they are
	// used in inner classes
	private double startX = 0;
	private double startY = 0;

	/**
	 * <pre>
	 * Called after a successful sign in or sign up.
	 * Loads the MenuOption fxml file into a root Node. Within in this Node a new 
	 * Scene is built and used to replace current stage, changing the GUI.
	 * </pre>
	 * 
	 * @see application.controller.ControllerUtilities#switchScene(javafx.event.ActionEvent)
	 * @param event
	 *            - ActionEvent object that was passed from either signIn or signUp
	 *            Listener
	 * @throws IOException
	 *             - thrown if there is an issue with loading new fxml file
	 */
	@Override
	public void switchScene(ActionEvent event) throws IOException {

		// uses the event that was passed in from a listener to identify current stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/MenuOption.fxml"));
		AnchorPane root = loader.load();

		// sets the values of where the mouse was first pressed
		root.setOnMousePressed((e) -> {
			startX = currentStage.getX() - e.getScreenX();
			startY = currentStage.getY() - e.getScreenY();
		});

		// changes the x, y values of the stage position, allowing the user to drag
		// around screen
		root.setOnMouseDragged((e) -> {
			currentStage.setX(e.getScreenX() + startX);
			currentStage.setY(e.getScreenY() + startY);
		});

		Scene menuScene = new Scene(root);

		// access MenuOptionController and pass current Account object
		MenuOptionController controller = loader.getController();
		controller.init_Username(userAccount);

		currentStage.setScene(menuScene);
		currentStage.show();
	}

	/**
	 * <pre>
	 * Listener for the select_signIn, select_signUp, forgotButton and backButton. 
	 * When one of these buttons is pressed it brings the corresponding pane to the front.
	 * </pre>
	 * 
	 * @see application.controller.ControllerUtilities#switchPaneListener(javafx.event.ActionEvent)
	 * @param event
	 *            - ActionEvent object used to identify the source of which button
	 *            was pressed.
	 */
	@Override
	public void switchPaneListener(ActionEvent event) {

		clearFields();

		if (event.getSource() == select_SignIn) {
			signInPane.toFront();
			signInUsername.requestFocus();
		}

		if (event.getSource() == forgotButton) {
			forgotPane.toFront();
			forgotUsername.requestFocus();
		}

		if (event.getSource() == select_SignUp) {
			signUpPane.toFront();
			signUpUsername.requestFocus();
		}

		if (event.getSource() == backButton) {
			signInPane.toFront();
			signInUsername.requestFocus();
		}
	}

	/**
	 * clears all fields on any pane when called
	 * 
	 * @see application.controller.ControllerUtilities#clearFields()
	 */
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
