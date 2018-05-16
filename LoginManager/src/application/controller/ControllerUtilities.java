/**
 * @author Stephen
 *
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
	 * @param textfield
	 * @return
	 */
	
	public boolean checkForEmptyInput(ArrayList<TextField> textfield) {
    	
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
	
	

	final protected String CSS_LOGIN_MANAGER = this.getClass().getResource("../view/LoginManager.css").toExternalForm();
	final protected String CSS_GREEN_SNACKBAR = this.getClass().getResource("../view/GreenSnackbar.css").toExternalForm();
	final protected String CSS_RED_SNACKBAR = this.getClass().getResource("../view/RedSnackbar.css").toExternalForm();
	
	/**
	 * @param style
	 * @param node
	 */
	
	public void setSnackbarStyle(String style, Node node) {
    	Scene scene = node.getScene();
    	scene.getStylesheets().clear();
    	scene.getStylesheets().addAll(CSS_LOGIN_MANAGER, style);
    }
	
	
	
	/*
	 * 
	 */
	
	public abstract void switchScene(ActionEvent event) throws IOException;
	public abstract void switchPaneListener(ActionEvent event);
	public abstract void clearFields();
}
