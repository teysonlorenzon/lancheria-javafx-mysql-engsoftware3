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
import itg.util.Mascaras;
import itg.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.entidades.WebService;
import model.exception.ValidationException;
import model.servicos.CadastroFornecedoresServico;

public class CadastroFornecedoresTelaFormControladora implements Initializable {

	private Juridica entidadeJuridica;
	private Pessoa entidade;
	private WebService ws;

	private CadastroFornecedoresServico servico = new CadastroFornecedoresServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdFornecedores;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtCidade;
	@FXML
	private TextField txtCep;
	@FXML
	private TextField txtUf;
	@FXML
	private TextField txtBairro;
	@FXML
	private TextField txtEndereco;
	@FXML
	private TextField txtNumero;
	@FXML
	private TextField txtTelefoneFixo;
	@FXML
	private TextField txtTelefoneCelular;
	@FXML
	private TextField txtComplemento;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtCnpj;
	@FXML
	private TextField txtNomeFantasia;

	@FXML
	private Label lbCnpj;
	@FXML
	private Label lbNomeFantasia;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private Label lbErrorNome;
	@FXML
	private Label lbErrorCnpj;
	@FXML
	private Label lbErrorNomeFantasia;
	@FXML
	private Label lbErrorCidade;
	@FXML
	private Label lbErrorBairro;
	@FXML
	private Label lbErrorEndereco;
	@FXML
	private Label lbErrorNumero;
	@FXML
	private Label lbErrorCep;
	@FXML
	private Label lbErrorUf;
	@FXML
	private Label lbErrorTelefoneFixo;
	@FXML
	private Label lbErrorTelefoneCelular;
	@FXML
	private Label lbErrorEmail;
	@FXML
	private Label lbErrorComplemento;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidadeJuridica = getFormDataJuridica();
			servico.iniciarUpdateOuIserirPessoaJuridica(entidadeJuridica);
			notifyDataChangeListeners();
			Utilitarios.currentStage(event).close();
			Alertas.showAlert("Informação", null, "Operação realizada com sucesso", AlertType.INFORMATION);

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

	@FXML
	public void onTxtCepAction() {
		if (txtCep.getText().length() == 9) {
			consultarCEP();
		}
	}

	private void consultarCEP() {
		ws = new WebService();
		ws.buscarCep(Utilitarios.removeMascara(txtCep.getText()));

		txtCidade.setText(ws.getCidade());
		txtUf.setText(ws.getUf());
		txtBairro.setText(ws.getBairro());
		txtEndereco.setText(ws.getLogradouro());
	}

	private Juridica getFormDataJuridica() {
		Juridica obj = new Juridica();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdFornecedores.getText() == null || txtIdFornecedores.getText().trim().equals("")) {
		} else {
			obj.setIdPessoa(Integer.parseInt(txtIdFornecedores.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo obrigatório");
		}
		if (txtCidade.getText() == null || txtCidade.getText().trim().equals("")) {
			exception.addError("cidade", "campo obrigatório");
		}

		if (txtCep.getText() == null || txtCep.getText().trim().equals("")) {
			exception.addError("cep", "campo obrigatório");
		}

		if (txtUf.getText() == null || txtUf.getText().trim().equals("")) {
			exception.addError("uf", "campo obrigatório");
		}
		if (txtBairro.getText() == null || txtBairro.getText().trim().equals("")) {
			exception.addError("bairro", "campo obrigatório");
		}
		if (txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			exception.addError("endereco", "campo obrigatório");
		}
		if (txtNumero.getText() == null || txtNumero.getText().trim().equals("")) {
			exception.addError("numero", "campo obrigatório");
		}

		if (txtTelefoneCelular.getText() == null || txtTelefoneCelular.getText().trim().equals("")) {
			exception.addError("telefonecelular", "campo obrigatório");
		}

		if (txtCnpj.getText() == null || txtCnpj.getText().trim().equals("")) {
			exception.addError("cnpj", "campo obrigatório");
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		obj.setNome(txtNome.getText());
		obj.setCidade(txtCidade.getText());
		obj.setCep(txtCep.getText());
		obj.setUf(txtUf.getText());
		obj.setBairro(txtBairro.getText());
		obj.setEndereco(txtEndereco.getText());
		obj.setNumero(Integer.parseInt(txtNumero.getText()));
		obj.setTelefoneFixo(txtTelefoneFixo.getText());
		obj.setTelefoneCelular(txtTelefoneCelular.getText());
		obj.setComplemento(txtComplemento.getText());
		if (txtEmail.getText() == null || txtEmail.getText().equals("")) {
			obj.setEmail(txtEmail.getText());
		} else {
			if (Utilitarios.validarEmail(txtEmail.getText()) == true) {
				obj.setEmail(txtEmail.getText());
			} else {
				Alertas.showAlert("Email Invalido", null, "Por favor informe um email valido", AlertType.INFORMATION);
				throw exception;
			}
		}
		if (Utilitarios.ValidaCNPJ(Utilitarios.removeMascara(txtCnpj.getText())) == true) {
			obj.setCnpj(txtCnpj.getText());
		} else {
			Alertas.showAlert("Cnpj Invalido", null, "Por favor informe um cnpj valido", AlertType.INFORMATION);
			throw exception;
		}
		obj.setNomeFantasia(txtNomeFantasia.getText());

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setPessoa(Pessoa entidade) {
		this.entidade = entidade;
	}

	public void setPessoaJuridica(Juridica entidadeJuridica) {
		this.entidadeJuridica = entidadeJuridica;
	}

	public void setCadastroFornecedoresServico(CadastroFornecedoresServico servico) {
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

		Mascaras.numericField(txtIdFornecedores);
		Mascaras.maxField(txtNome, 40);
		Mascaras.maxField(txtNomeFantasia, 50);
		Mascaras.cnpjField(txtCnpj);
		Mascaras.foneFixoField(txtTelefoneFixo);
		Mascaras.foneField(txtTelefoneCelular);
		Mascaras.maxField(txtEmail, 50);
		Mascaras.cepField(txtCep);
		Mascaras.maxField(txtCidade, 40);
		Mascaras.maxField(txtUf, 2);
		Mascaras.maxField(txtEndereco, 50);
		Mascaras.numericField(txtNumero);
		Mascaras.maxField(txtBairro, 30);
		Mascaras.maxField(txtComplemento, 50);

	}

	public void updateFormDataJuridica() {

		txtIdFornecedores.setText(String.valueOf(entidade.getIdPessoa()));
		txtNome.setText(entidadeJuridica.getNome());
		txtCnpj.setText(entidadeJuridica.getCnpj());
		txtNomeFantasia.setText(entidadeJuridica.getNomeFantasia());
		txtTelefoneFixo.setText(entidadeJuridica.getTelefoneFixo());
		txtTelefoneCelular.setText(entidadeJuridica.getTelefoneCelular());
		txtEmail.setText(entidadeJuridica.getEmail());
		txtCep.setText(entidadeJuridica.getCep());
		txtCidade.setText(entidadeJuridica.getCidade());
		txtUf.setText(entidadeJuridica.getUf());
		txtEndereco.setText(entidadeJuridica.getEndereco());
		txtBairro.setText(entidadeJuridica.getBairro());
		txtNumero.setText(Integer.toString(entidadeJuridica.getNumero()));
		txtComplemento.setText(entidadeJuridica.getComplemento());
		txtCnpj.setText(entidadeJuridica.getCnpj());
		txtNomeFantasia.setText(entidadeJuridica.getNomeFantasia());

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErrorNome.setText(errors.get("nome"));

		}
		if (fields.contains("cidade")) {
			lbErrorCidade.setText(errors.get("cidade"));

		}
		if (fields.contains("cep")) {
			lbErrorCep.setText(errors.get("cep"));

		}
		if (fields.contains("uf")) {
			lbErrorUf.setText(errors.get("uf"));

		}
		if (fields.contains("bairro")) {
			lbErrorBairro.setText(errors.get("bairro"));

		}
		if (fields.contains("endereco")) {
			lbErrorEndereco.setText(errors.get("endereco"));

		}
		if (fields.contains("numero")) {
			lbErrorNumero.setText(errors.get("numero"));

		}
		if (fields.contains("telefonecelular")) {
			lbErrorTelefoneCelular.setText(errors.get("telefonecelular"));

		}
		if (fields.contains("cnpj")) {
			lbErrorCnpj.setText(errors.get("cnpj"));

		}
		if (fields.contains("nomefantasia")) {
			lbErrorNomeFantasia.setText(errors.get("nomefantaia"));

		}
	}

}
