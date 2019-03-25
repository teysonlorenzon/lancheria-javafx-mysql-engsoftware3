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
import itg.util.Restricao;
import itg.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.entidades.UsuariosLogin;
import model.exception.ValidationException;
import model.servicos.UsuariosLoginServico;

public class NovoUsuarioTelaFormControladora implements Initializable {

	private UsuariosLogin entidade;

	private UsuariosLoginServico servico;

	private String nivel = "";

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private PasswordField pswSenha;
	@FXML
	private PasswordField pswReSenha;

	@FXML
	private Label lbErroNome;
	@FXML
	private Label lbErroSenha;
	@FXML
	private Label lbErroReSenha;

	@FXML
	private Button btConfirmar;

	@FXML
	private Button btCancelar;

	@FXML
	private RadioButton rbAdmin;

	@FXML
	private RadioButton rbUsuario;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (entidade == null) {
			throw new IllegalStateException("Entidade está nullo");
		}
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {
			entidade = getFormData();
			if (compara() == false) {
				servico.iniciarUpdateOuIserir(entidade);
				notifyDataChangeListeners();
				Utilitarios.currentStage(event).close();
				Alertas.showAlert("Informação", null, "Operação realizada com sucesso", AlertType.INFORMATION);

			} else {
				Alertas.showAlert("Alerta", null, "Usuário existente", AlertType.WARNING);
			}

		} catch (DbException e) {
			Alertas.showAlert("Error Save Object", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessage(e.getErrors());
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private UsuariosLogin getFormData() {
		UsuariosLogin obj = new UsuariosLogin();

		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtId.getText() == null || txtId.getText().trim().equals("")) {
		} else {
			obj.setId(Integer.parseInt(txtId.getText()));

		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo vazio");
		}

		if (pswSenha.getText() == null || pswSenha.getText().trim().equals("")) {
			exception.addError("senha", "campo vazio");
		}

		if (pswReSenha.getText() == null || pswReSenha.getText().trim().equals("")) {
			exception.addError("resenha", "campo vazio");
		} else {
			if (pswSenha.getText().trim().equals(pswReSenha.getText())) {
				obj.setSenha(Utilitarios.cripMd5(pswSenha.getText()));
			} else {
				Alertas.showAlert("Erro", null, "Senhas não conferem", AlertType.ERROR);
			}

		}

		obj.setUsuario(txtNome.getText());

		if (nivel == null || nivel.equals("")) {
			Alertas.showAlert("Informação", null, "Selecione o nível de usuário", AlertType.INFORMATION);
			throw exception;
		} else {
			if (nivel.equals("admin")) {
				obj.setNivel("Admin");
			} else {
				obj.setNivel("Usuario");
			}
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return obj;
	}

	public boolean compara() {
		List<UsuariosLogin> lista = servico.buscarTudo();
		for (UsuariosLogin ul : lista) {
			if (txtId.getText() == null || txtId.getText().equals("")) {
				if (txtNome.getText().trim().equals(ul.getUsuario())) {
					return true;
				}
			}
		}
		return false;
	}

	@FXML
	public void onRbAdminAction() {
		nivel = "admin";
		rbUsuario.setSelected(false);
	}

	@FXML
	public void onRbUsuarioAction() {
		nivel = "usuario";
		rbAdmin.setSelected(false);
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();
	}

	public void setUsuariosLogin(UsuariosLogin entidade) {
		this.entidade = entidade;
	}

	public void setUsuariosLoginServico(UsuariosLoginServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		Restricao.setTextFieldInteger(txtId);
		Restricao.setTextFieldMaxLength(txtNome, 30);
	}

	public void updateFormData() {
		if (entidade == null) {
			throw new IllegalStateException("Entidade está nulo");
		}
		txtId.setText(String.valueOf(entidade.getId()));
		txtNome.setText(entidade.getUsuario());
	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErroNome.setText(errors.get("nome"));

		}
		if (fields.contains("senha")) {
			lbErroSenha.setText(errors.get("senha"));

		}
		if (fields.contains("resenha")) {
			lbErroReSenha.setText(errors.get("resenha"));

		}
	}

}
