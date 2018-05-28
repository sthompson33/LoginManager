/**
 * <pre> 
 * Controller Class for MenuOption.fxml file locatied in application.view.
 * Methods included mostly consist of listeners that act once a specific button is pushed.
 * LoginInformation object is used to call methods that handle the data entered and 
 * carry out the desired action.
 * </pre>
 * 
 * @author Stephen Thompson
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

public class MenuOptionController extends ControllerUtilities {

	@FXML
	private AnchorPane rootAnchor;

	@FXML
	private Button closeButton, minButton;

	@FXML
	private VBox menuBox;

	// JFXButtons on the menuBox
	@FXML
	private JFXButton addNewMenuButton, retrieveMenuButton, updateMenuButton, deleteMenuButton, signOutButton;

	// JFXButtons on each corresponding pane
	@FXML
	private JFXButton addNewButton, retrieveButton, updateButton, deleteButton;

	@FXML
	private Label usernameResult, passwordResult;

	@FXML
	private Pane displayPane, helpPane, addNewPane, retrievePane, updatePane, deletePane;

	@FXML
	private ImageView userIconA, userIconU, lockIconA, lockIconU, webIconA, webIconD, webIconR, webIconU;

	@FXML
	private JFXTextField addWebsiteField, addUsernameField, retrieveWebsiteField, updateWebsiteField,
			updateUsernameField, deleteWebsiteField;

	@FXML
	private JFXPasswordField addPasswordField, updatePasswordField;

	@FXML
	private JFXCheckBox usernameCheckBox, passwordCheckBox;

	private ArrayList<TextField> textFieldList = new ArrayList<TextField>(3);
	private LoginInformation login;

	// called when class is first loaded
	public void initialize() {

		displayPane.toFront();

		closeButton.setGraphic(new ImageView(new Image("/images/close.png")));
		minButton.setGraphic(new ImageView(new Image("/images/min.png")));

		/*
		 * Following for loops add changelistener via lambda expressions to textfields.
		 * Once a textfield recieves focus the corresponding icon changes color to
		 * match.
		 */
		JFXTextField[] webFields = { addWebsiteField, retrieveWebsiteField, updateWebsiteField, deleteWebsiteField };
		ImageView[] webIcons = { webIconA, webIconR, webIconU, webIconD };

		for (int i = 0; i < webFields.length; i++) {
			final int j = i; // variable used in inner class must be final
			webFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue)
					webIcons[j].setImage(new Image("/images/blue_web.png"));
				else
					webIcons[j].setImage(new Image("/images/gray_web.png"));
			});
		}

		JFXTextField[] userFields = { addUsernameField, updateUsernameField };
		ImageView[] userIcons = { userIconA, userIconU };

		for (int i = 0; i < userFields.length; i++) {
			final int j = i;
			userFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue)
					userIcons[j].setImage(new Image("/images/blue_user.png"));
				else
					userIcons[j].setImage(new Image("/images/gray_user.png"));
			});
		}

		JFXPasswordField[] passwordFields = { addPasswordField, updatePasswordField };
		ImageView[] lockIcons = { lockIconA, lockIconU };

		for (int i = 0; i < passwordFields.length; i++) {
			final int j = i;
			passwordFields[i].focusedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue)
					lockIcons[j].setImage(new Image("/images/blue_lock.png"));
				else
					lockIcons[j].setImage(new Image("/images/gray_lock.png"));
			});
		}
	}

	/**
	 * <pre>
	 * Creates an instance of LoginInformation using the username from an Account object for its parameter. 
	 * This will set the LoginInformation fileName field, as each Account has a specific file based on username.
	 * Any methods used from the LoginInformation class will be associated with this file.
	 * Method is called from MainLoginController, passing in which account is currently in use.
	 * </pre>
	 * 
	 * @param account is an Account object, username from object needed to pass into
	 *            LoginInformation constructor.
	 */
	public void init_Username(Account account) {
		login = new LoginInformation(account.getUsername());
	}

	/**
	 * <pre>
	 * Listener for close and minimize buttons on GUI. StageStyle has been set to UNDECORATED.
	 * If the close button was pressed, program will close. If the minimize button was pressed,
	 * the program will call the static getStage method from the LoginManager class and setIconified to true.
	 * This will minimize the program to the system tray.
	 * </pre>
	 * 
	 * @param event an ActionEvent object that was raised when either close or
	 *            minimize button was pressed
	 */
	@FXML
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
	 * Listener for the addNewButton. All fields will be added to a list to be checked for empty input.
	 * If all fields contain data, findWebsite will be called to see if website exist on current username file.
	 * If no match is found, addNewLogin is called to add information to the file. If website is found,
	 * a toast message will show that the website already exists on file.
	 * </pre>
	 */
	@FXML
	public void addNewListener() {

		JFXSnackbar addNewSnackbar = new JFXSnackbar(addNewPane);

		textFieldList.clear();
		textFieldList.add(addWebsiteField);
		textFieldList.add(addUsernameField);
		textFieldList.add(addPasswordField);

		if (!checkForEmptyInput(textFieldList)) {
			if (!login.findWebsite(addWebsiteField.getText())) {
				login.addNewLogin(addWebsiteField.getText(), addUsernameField.getText(), addPasswordField.getText());
				clearFields();
				addWebsiteField.requestFocus();
				setSnackbarStyle(CSS_GREEN_SNACKBAR, rootAnchor);
				addNewSnackbar.enqueue(new SnackbarEvent("Information Added Successfully!"));
			} else {
				addWebsiteField.requestFocus();
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				addNewSnackbar.enqueue(new SnackbarEvent("Website Already Exists"));
			}
		}
	}

	/**
	 * <pre>
	 * Listener for the retrieveButton on retrievePane. All fields will be added to a list to be checked for empty input.
	 * If all fields contain data, findWebsite will be called to see if website exist on current username file.
	 * If no match is found, toast message will display that the website was not found. If website is found,
	 * usernameResult and passwordResult labels text will be set using getters from LoginInformation class.
	 * </pre>
	 */
	@FXML
	public void retrieveListener() {

		JFXSnackbar retrieveSnackbar = new JFXSnackbar(retrievePane);

		textFieldList.clear();
		textFieldList.add(retrieveWebsiteField);

		if (!checkForEmptyInput(textFieldList)) {
			if (login.findWebsite(retrieveWebsiteField.getText())) {
				usernameResult.setText(login.getUsername());
				passwordResult.setText(login.getPassword());
			} else {
				retrieveWebsiteField.requestFocus();
				usernameResult.setText("");
				passwordResult.setText("");
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				retrieveSnackbar.enqueue(new SnackbarEvent("Website Not Found"));
			}
		}
	}

	/**
	 * <pre>
	 * Listener for the updateButton on updatePane. All fields that are visible will be added to a list
	 * to check for empty input. As long as usernameCheckbox or passwordCheckbox has been selected for updating, 
	 * findWebsite will be called. If neither one has been selected, a toast message will show that the user must select one.
	 * If the website was found on current username file, updateLogin will be called to make changes and a toast message
	 * showing updating information was succesful. No website found on file will show message saying no website found.
	 * </pre>
	 * 
	 * @see checkBoxListener
	 */
	@FXML
	public void updateListener() {

		JFXSnackbar updateSnackbar = new JFXSnackbar(updatePane);

		textFieldList.clear();
		textFieldList.add(updateWebsiteField);

		if (usernameCheckBox.isSelected())
			textFieldList.add(updateUsernameField);

		if (passwordCheckBox.isSelected())
			textFieldList.add(updatePasswordField);

		if (!checkForEmptyInput(textFieldList)) {
			if (!usernameCheckBox.isSelected() && !passwordCheckBox.isSelected()) {
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				updateSnackbar.enqueue(new SnackbarEvent("Must Select a Username or Password to Update"));
			} else if (login.findWebsite(updateWebsiteField.getText())) {
				login.updateLogin(updateUsernameField.getText(), updatePasswordField.getText());
				clearFields();
				usernameCheckBox.setSelected(false);
				passwordCheckBox.setSelected(false);
				updateUsernameField.setVisible(false);
				userIconU.setVisible(false);
				updatePasswordField.setVisible(false);
				lockIconU.setVisible(false);
				updateWebsiteField.requestFocus();
				setSnackbarStyle(CSS_GREEN_SNACKBAR, rootAnchor);
				updateSnackbar.enqueue(new SnackbarEvent("Information Updated Successfully!"));
			} else {
				updateWebsiteField.requestFocus();
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				updateSnackbar.enqueue(new SnackbarEvent("Website Not Found"));
			}
		}
	}

	/**
	 * <pre>
	 * Listener for usernameCheckbox and passwordCheckbox. When one is selected the corresponding textfield 
	 * and icon will become visible and ready for input. If deselected they will become invisible again.
	 * </pre>
	 */
	@FXML
	public void checkBoxListener() {

		if (usernameCheckBox.isSelected()) {
			updateUsernameField.setVisible(true);
			userIconU.setVisible(true);
		} else {
			updateUsernameField.setVisible(false);
			userIconU.setVisible(false);
		}

		if (passwordCheckBox.isSelected()) {
			updatePasswordField.setVisible(true);
			lockIconU.setVisible(true);
		} else {
			updatePasswordField.setVisible(false);
			lockIconU.setVisible(false);
		}
	}

	/**
	 * <pre>
	 * Listener for the deleteButton on deletePane. All fields will be added to a list to be checked for empty input.
	 * If all fields contain data, findWebsite will be called to see if website exist on current username file.
	 * If no match is found, toast message will display that the website was not found. If website is found,
	 * deleteLogin will be called and a toast message display successful deletion.
	 * </pre>
	 */
	@FXML
	public void deletListener() {

		JFXSnackbar deleteSnackbar = new JFXSnackbar(deletePane);

		textFieldList.clear();
		textFieldList.add(deleteWebsiteField);

		if (!checkForEmptyInput(textFieldList)) {
			if (login.findWebsite(deleteWebsiteField.getText())) {
				login.deleteLogin();
				clearFields();
				deleteWebsiteField.requestFocus();
				setSnackbarStyle(CSS_GREEN_SNACKBAR, rootAnchor);
				deleteSnackbar.enqueue(new SnackbarEvent("Information Deleted Successfully!"));
			} else {
				deleteWebsiteField.requestFocus();
				setSnackbarStyle(CSS_RED_SNACKBAR, rootAnchor);
				deleteSnackbar.enqueue(new SnackbarEvent("Website Not Found"));
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
	 * Called after signOutButton is pressed.
	 * Loads the MainLogin fxml file into a root Node. Within in this Node a new 
	 * Scene is built and used to replace current stage, changing the GUI.
	 * </pre>
	 * 
	 * @see application.controller.ControllerUtilities#switchScene(javafx.event.ActionEvent)
	 * @param event ActionEvent object raised from pressing signOutButton
	 * @throws IOException thrown if there is an issue with loading new fxml file
	 */
	@Override
	public void switchScene(ActionEvent event) throws IOException {

		// event is used to identify current stage
		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		AnchorPane root = FXMLLoader.load(getClass().getResource("../view/MainLogin.fxml"));

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

		Scene mainScene = new Scene(root);

		currentStage.setScene(mainScene);
		currentStage.show();
	}

	/**
	 * <pre>
	 * Listener for the addNewMenuButton, retrieveMenuButton, updateMenuButton and deleteMenuButton. 
	 * When one of these buttons is pressed it brings the corresponding pane to the front.
	 * </pre>
	 * 
	 * @see application.controller.ControllerUtilities#switchPaneListener(javafx.event.ActionEvent)
	 * @param event ActionEvent object used to identify the source of which button
	 *            was pressed.
	 */
	@Override
	public void switchPaneListener(ActionEvent event) {

		clearFields();

		if (event.getSource() == addNewMenuButton) {
			addNewPane.toFront();
			addWebsiteField.requestFocus();
		}

		if (event.getSource() == retrieveMenuButton) {
			retrievePane.toFront();
			retrieveWebsiteField.requestFocus();
		}

		if (event.getSource() == updateMenuButton) {
			updatePane.toFront();
			updateWebsiteField.requestFocus();
		}

		if (event.getSource() == deleteMenuButton) {
			deletePane.toFront();
			deleteWebsiteField.requestFocus();
		}
	}

	/**
	 * clears all fields on any pane when called
	 * 
	 * @see application.controller.ControllerUtilities#clearFields()
	 */
	@Override
	public void clearFields() {
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
