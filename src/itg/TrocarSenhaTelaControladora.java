package itg;

import java.net.URL;
import java.util.ResourceBundle;

import itg.util.Alertas;
import itg.util.Utilitarios;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import model.entidades.UsuariosLogin;
import model.servicos.UsuariosLoginServico;

public class TrocarSenhaTelaControladora implements Initializable{

	private UsuariosLoginServico sv = new UsuariosLoginServico();

	@FXML
	private PasswordField pswSenhaAntiga;
	@FXML
	private PasswordField pswSenhaNova;
	@FXML
	private PasswordField pswConfirmaSenha;
	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	public void onButtonConfirmar() {

		trocarSenha();
		

	}

	public void trocarSenha() {

		
		UsuariosLogin ul = sv.buscarId(LoginTelaControladora.getIdUsuarioLogado());
		String senha = ul.getSenha();
		String senhaAntiga = pswSenhaAntiga.getText();
		String resenha;
		
		if (senha.equals(senhaAntiga)) {
			senha = pswSenhaNova.getText();
			resenha = pswConfirmaSenha.getText();

			if (senha.equals(resenha)) {
				ul.setSenha(senha);
				sv.iniciarUpdateOuIserir(ul);
				Alertas.showAlert("Senha Alterada", "Senha alterada com sucesso", null, AlertType.INFORMATION);
				Utilitarios.fecharTela(InicialTelaControladora.getTelaTrocarSenhaScene());
			}else {
				Alertas.showAlert("Erro", "Confirmação de senha nao confere com senha nova", null, AlertType.ERROR);
			}
		}
		else {
			Alertas.showAlert("Erro", "Senha antiga incorreta", null, AlertType.ERROR);
		}
	}

	@FXML
	public void onButtonCancelar() {
	Utilitarios.fecharTela(InicialTelaControladora.getTelaTrocarSenhaScene());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}

	
}
