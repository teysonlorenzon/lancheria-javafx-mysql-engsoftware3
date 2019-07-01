package itg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import itg.util.Alertas;
import itg.util.Restricao;
import itg.util.Utilitarios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
	private static Scene menuScene;
	private static Integer guardaid;
	private static String guardaFunc;
	private static String nivelFunc;
	
	public static Integer getIdUsuarioLogado() {
		return guardaid;
	}
	
	public static String getGuardaFuncionario() {
		return guardaFunc;
	}

	public static String getGuardaNivel() {
		return nivelFunc;
	}
	
	public static Stage getMenuSage() {
		return menuStage;
	}
	public static Scene getMenuScene() {
		return menuScene;
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
			Utilitarios.fecharTela(Main.getLoginScene());
		} else {
			Alertas.showAlert("Erro", null, "Usuário ou senha incorretos", AlertType.ERROR);
		}
	}

	@FXML
	public void botaoConcelarAcao() {
		Utilitarios.fecharTela(Main.getLoginScene());
	}

	public synchronized void carregarTelaInicial(String nomeFXML) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeFXML));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			menuScene = new Scene(scrollPane);
			menuStage.setScene(menuScene);
			menuStage.setTitle("Menu");
			menuStage.show();


		} catch (IOException e) {
			Alertas.showAlert("Erro", null, "Erro ao carregar a tela inicial", AlertType.ERROR);
		}

	}

	
	public boolean verificaConta() {
		UsuariosLogin ul = servico.buscarUsuarioSenha(txtUsuario.getText(), Utilitarios.cripMd5(txtSenha.getText()));
		if (ul != null && ul.getUsuario() != null && ul.getSenha() != null) {
			this.guardaid = ul.getId();
			this.guardaFunc = ul.getNome();
			this.nivelFunc = ul.getNivel();
			return true;
		} else {
			return false;
		}
	}
	
	private void tecladoEnter(TextField txt) {
		txt.setOnKeyPressed(k -> {
			final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
			if (ENTER.match(k)) {
				botaoComfirmarAcao();
			}
		});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		tecladoEnter(txtUsuario);
		tecladoEnter(txtSenha);
		
		Restricao.setTextFieldMaxLength(txtUsuario, 45);
		Restricao.setTextFieldMaxLength(txtSenha, 70);
	}

}
