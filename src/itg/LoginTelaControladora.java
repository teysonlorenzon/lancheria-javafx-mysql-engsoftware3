package itg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import itg.util.Alertas;
import itg.util.Restricao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.entidades.UsuariosLogin;
import model.servicos.UsuariosLoginServico;

public class LoginTelaControladora implements Initializable {

	private UsuariosLoginServico servico = new UsuariosLoginServico();
	private static Stage menuStage = new Stage();

	public static Stage getMenuStage() {
		return menuStage;
	}

	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtSenha;
	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	public void botaoComfirmarAcao() {

		
		if (verificaConta() == true) {
			carregarTelaInicial("/itg/InicialTela.fxml");
			fecharLogin();
		} else {
			Alertas.showAlert("Erro", "por favor insira um login correto!!", "Usuário ou senha incorretos", AlertType.ERROR);
		}
	}

	@FXML
	public void botaoConcelarAcao() {
		fecharLogin();
	}

	public synchronized void carregarTelaInicial(String nomeFXML) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeFXML));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			Scene menuScene = new Scene(scrollPane);
			menuStage.setScene(menuScene);
			menuStage.setTitle("Menu");
			menuStage.show();

			fecharLogin();

		} catch (IOException e) {
			Alertas.showAlert("Erro", null, "Erro ao carregar a tela inicial", AlertType.ERROR);
		}

	}

	public static void fecharLogin() {
		Scene loginScene = Main.getLoginScene();
		Stage stage = (Stage) loginScene.getWindow();
		stage.close();
	}
	
	public boolean verificaConta() {
		UsuariosLogin ul = servico.buscarUsuarioSenha(txtUsuario.getText(), txtSenha.getText());
		if (ul != null && ul.getUsuario() != null && ul.getSenha() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Restricao.setTextFieldMaxLength(txtUsuario, 45);
		Restricao.setTextFieldMaxLength(txtSenha, 70);
	}

}
