/**
 * <pre> 
 * Controller Utilities class that contains methods and abstract methods
 * that are used by both controller classes for the LoginManager application.
 * Current derived classes include MainLoginController.java and MenuOptionController.java
 * </pre>
 * 
 * @author Stephen Thompson
 */

package application.controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public abstract class ControllerUtilities {

	/**
	 * <pre>
	 * Checks to see if each textfield in the list is empty or not. If the textfield is empty, the style
	 * jfx-text-field-error (can be found in LoginManager.css) is added, turning the textfield and prompt text to red.
	 * If textfield is not empty, all styles are removed (incase error style had already been added) and the original 
	 * application styles are set.
	 * </pre>
	 * 
	 * @param textfield can be a textfield or anything that derives from the
	 *            textfield class such as passwordfield.
	 * @return true if any textfields in the list contain empty input, false
	 *         otherwise
	 */
	public boolean checkForEmptyInput(ArrayList<TextField> textfield) {

		boolean emptyInput = false;

		for (TextField tf : textfield) {

			if (tf.getText().isEmpty()) {
				tf.getStyleClass().add("jfx-text-field-error");
				emptyInput = true;
			} else {
				tf.getStyleClass().clear();
				tf.getStyleClass().addAll("text-field", "text-input", "jfx-text-field", "password-field", "jfx-password-field");
			}
		}

		return emptyInput;
	}

	// constants used in setSnackbarStyle method below
	final private String CSS_LOGIN_MANAGER = this.getClass().getResource("../view/LoginManager.css").toExternalForm();
	final protected String CSS_GREEN_SNACKBAR = this.getClass().getResource("../view/GreenSnackbar.css").toExternalForm();
	final protected String CSS_RED_SNACKBAR = this.getClass().getResource("../view/RedSnackbar.css").toExternalForm();

	/**
	 * <pre>
	 * Sets the style of the snackbar toast message that pops up after certain events.
	 * When called, stylesheets are first cleared from the scene to remove any previously added styles.
	 * The main css file is then added along with the new style for the snackbar message.
	 * </pre>
	 * <p>
	 * 
	 * <pre>
	 * Current styles available are..
	 * CSS_GREEN_SNACKBAR sets the style of the message to green indicating a successful attempt.
	 * CSS_RED_SNACKBAR sets the style of the message to red indicating that an error has occured.
	 * </pre>
	 * <p>
	 * 
	 * <pre>
	 * *** At the time this class was written, there were issues with adding styles to the 
	 * snackbar using the main css file. Current solution was to create a file for each
	 * snackbar style desired and adding to scene when wanted. ***
	 * </pre>
	 * 
	 * @param style represents which color font to use for toast message:
	 *            CSS_GREEN_SNACKBAR or CSS_RED_SNACKBAR
	 * @param node - is needed to get current Scene that is being used. Once scene
	 *            has been retrieved, a style can be added to it
	 */
	public void setSnackbarStyle(String style, Node node) {
		Scene scene = node.getScene();
		scene.getStylesheets().clear();
		scene.getStylesheets().addAll(CSS_LOGIN_MANAGER, style);
	}

	// Abstract methods that are used by both controller classes
	public abstract void switchScene(ActionEvent event) throws IOException;

	public abstract void switchPaneListener(ActionEvent event);

	public abstract void clearFields();
}
