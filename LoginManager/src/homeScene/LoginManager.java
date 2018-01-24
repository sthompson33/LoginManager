package homeScene;

/**
 * @author Stephen
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginManager extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		
		//load fxml file
		Parent mainParent = FXMLLoader.load(getClass().getResource("MainLogin.fxml"));
		
		//build scene graph
		Scene mainScene = new Scene(mainParent);
		
		//display window with scene
		stage.setTitle("Login Manager");
		stage.setScene(mainScene);
		stage.show();
	}

	/**
	 * @param args
	 */
	
	public static void main(String[] args){
		launch(args);
	}
}