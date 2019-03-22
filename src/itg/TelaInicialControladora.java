package itg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import itg.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TelaInicialControladora implements Initializable {

	private static Scene telainicialScene;

	public static Scene getTelaInicialScene() {
		return telainicialScene;
	}

	@FXML
	private MenuItem miSobre;

	@FXML
	public void onMenuItemSobre() {
		carregarTela("/itg/SobreTela.fxml", x -> {});
	}

	public synchronized <T> void carregarTela(String absoluteName, Consumer<T> initializingAction) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();

			Scene mainScene = LoginTelaControladora.getMenuScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren());

			T controller = loader.getController();
			initializingAction.accept(controller);

		} catch (IOException e) {
			Alertas.showAlert("IO exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

}
