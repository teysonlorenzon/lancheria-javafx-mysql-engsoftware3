package itg.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Utilitarios {

	public static void fecharTela(Scene scene) {
		Scene loginScene = scene;
		Stage stage = (Stage) loginScene.getWindow();
		stage.close();
	}
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

}
