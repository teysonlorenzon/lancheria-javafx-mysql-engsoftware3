package itg.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Alertas {
	public static void showAlert(String titulo, String cabecalho, String conteudo, AlertType tipodoAlerta) {
		Alert alert = new Alert(tipodoAlerta);
		alert.setTitle(titulo);
		alert.setHeaderText(conteudo);
		alert.setContentText(cabecalho);
		alert.show();
	}

	public static Optional<ButtonType> showConfirmation(String titulo, String cabecalho) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(null);
		alert.setContentText(cabecalho);
		return alert.showAndWait();
	}
}
