package application;



import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	private static Scene loginScene;

	public static Scene getLoginScene() {
		return loginScene;
	}

	@Override
	public void start(Stage stage) {
		try {
					
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itg/LoginTela.fxml"));
			AnchorPane anchorPane = loader.load();

			stage.initStyle(StageStyle.DECORATED);
			stage.setMinHeight(245.0);
			stage.setMinWidth(451.0);
			stage.resizableProperty().setValue(Boolean.FALSE);

			loginScene = new Scene(anchorPane);
			stage.setScene(loginScene);
			stage.setTitle("Tela de Login");
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}