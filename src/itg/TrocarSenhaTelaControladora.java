package itg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import itg.listeners.DataChangeListener;
import itg.util.Alertas;
import itg.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import model.entidades.UsuariosLogin;
import model.exception.ValidationException;
import model.servicos.UsuariosLoginServico;

public class TrocarSenhaTelaControladora implements Initializable {

	private UsuariosLoginServico sv = new UsuariosLoginServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	private UsuariosLogin entidade = new UsuariosLogin();

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
	private Label lbErroSenhaNova;
	@FXML
	private Label lbErroReSenha;
	@FXML
	private Label lbErroSenhaAntiga;

	@FXML
	public void onButtonConfirmar(ActionEvent event) {
		if (entidade == null) {

		}
		if (sv == null) {
			throw new IllegalStateException("Serviço está nullo");
		}
		try {
			setEntidade(getFormData());
			sv.iniciarUpdateOuIserir(entidade);
			notifyDataChangeListeners();
			Utilitarios.currentStage(event).close();

		} catch (DbException e) {
			Alertas.showAlert("Error Save Object", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessage(e.getErrors());
		}
	}

	private UsuariosLogin getFormData() {
		UsuariosLogin obj = new UsuariosLogin();
		obj = sv.buscarId(LoginTelaControladora.getIdUsuarioLogado());

		ValidationException exception = new ValidationException("Erro de Validação");

		if (pswSenhaAntiga.getText() == null || pswSenhaAntiga.getText().trim().equals("")) {
			exception.addError("antiga", "campo vazio");
		} else {
			if (Utilitarios.cripMd5(pswSenhaAntiga.getText()).trim().equals(obj.getSenha())) {

				if (pswSenhaNova.getText() == null || pswSenhaNova.getText().trim().equals("")) {
					exception.addError("senha", "campo vazio");
				} else {

					if (pswConfirmaSenha.getText() == null || pswConfirmaSenha.getText().trim().equals("")) {
						exception.addError("resenha", "campo vazio");
					} else {

						if (pswSenhaNova.getText().trim().equals(pswConfirmaSenha.getText())) {
							obj.setSenha(Utilitarios.cripMd5(pswSenhaNova.getText()));
							Alertas.showAlert("Informação", null, "Senha alterada com sucesso", AlertType.INFORMATION);
						} else {
							Alertas.showAlert("Erro", null, "Senhas não conferem", AlertType.ERROR);
						}
					}
				}
			} else {
				Alertas.showAlert("Atenção", null, "Senha Antiga não confere", AlertType.WARNING);
			}

		}
		
		if (exception.getErrors().size() > 0) {
			throw exception;
		}


		return obj;
	}

	@FXML
	public void onButtonCancelar() {
		Utilitarios.fecharTela(InicialTelaControladora.getTelaTrocarSenhaScene());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	public UsuariosLogin setEntidade(UsuariosLogin entidade) {
		return this.entidade = entidade;
	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("antiga")) {
			lbErroSenhaAntiga.setText(errors.get("antiga"));

		}
		if (fields.contains("senha")) {
			lbErroSenhaNova.setText(errors.get("senha"));

		}
		if (fields.contains("resenha")) {
			lbErroReSenha.setText(errors.get("resenha"));

		}
	}

}
