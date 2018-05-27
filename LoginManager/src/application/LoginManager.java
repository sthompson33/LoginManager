/**
 * @author Stephen Thompson
 *
 * Initial start of LoginManager application 
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginManager extends Application {

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	
	private static Stage stage;
	private double xOffset = 0;
	private double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//load fxml file
		Parent root = FXMLLoader.load(getClass().getResource("view/MainLogin.fxml"));
		
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image("/images/blue_lock.png"));
		
		root.setOnMousePressed((event) -> {
			xOffset = primaryStage.getX() - event.getScreenX();
			yOffset = primaryStage.getY() - event.getScreenY();
		});
		
		root.setOnMouseDragged((event) -> {
			primaryStage.setX(event.getScreenX() + xOffset);
			primaryStage.setY(event.getScreenY() + yOffset);
		});
		
		//build scene graph
		Scene mainScene = new Scene(root);
		
		//display window with scene
		primaryStage.setScene(mainScene);
		primaryStage.show();
		
		stage = primaryStage;
	}
	
	public static Stage getStage() {
		return stage;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}
}