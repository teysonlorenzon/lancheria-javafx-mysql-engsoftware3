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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.entidades.WebService;
import model.exception.ValidationException;
import model.servicos.CadastroClientesServico;
import model.servicos.CadastroFuncionariosServico;

public class CadastroFuncionariosTelaFormControladora implements Initializable {

	private Fisica entidadeFisica;
	private Pessoa entidade;
	private WebService ws;
	private CadastroFuncionariosServico servico = new CadastroFuncionariosServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
	
	public CadastroFuncionariosServico getServico() {
		return servico;
	}

	public void setCadastroFuncionariosServico(CadastroFuncionariosServico servico) {
		this.servico = servico;
	}

	public Fisica getEntidadeFisica() {
		return entidadeFisica;
	}

	public void setEntidadeFisica(Fisica entidadeFisica) {
		this.entidadeFisica = entidadeFisica;
	}

	public Pessoa getEntidade() {
		return entidade;
	}

	public void setEntidade(Pessoa entidade) {
		this.entidade = entidade;
	}


	@FXML
	private TextField txtIdFuncionarios;
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
	private TextField txtRg;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtDataNascimento;
	@FXML
	private TextField txtCnpj;
	@FXML
	private TextField txtNomeFantasia;

	@FXML
	private Label lbCpf;
	@FXML
	private Label lbCnpj;
	@FXML
	private Label lbDataNascimento;
	@FXML
	private Label lbRg;
	@FXML
	private Label lbNomeFantasia;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private RadioButton rbFisica;
	@FXML
	private RadioButton rbJuridica;

	@FXML
	private Label lbErrorNome;
	@FXML
	private Label lbErrorCpf;
	@FXML
	private Label lbErrorRg;
	@FXML
	private Label lbErrorDatanascimento;
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

			entidadeFisica = getFormDataFisica();
			servico.iniciarUpdateOuIserirPessoaFisica(entidadeFisica);
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

	private Fisica getFormDataFisica() {

		Fisica obj = new Fisica();

		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdFuncionarios.getText() == null || txtIdFuncionarios.getText().trim().equals("")) {
		} else {
			obj.setIdPessoa(Integer.parseInt(txtIdFuncionarios.getText()));
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

		if (txtRg.getText() == null || txtRg.getText().trim().equals("")) {
			exception.addError("rg", "campo obrigatório");
		}
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "campo obrigatório");
		}
		if (txtDataNascimento.getText() == null || txtDataNascimento.getText().trim().equals("")) {
			exception.addError("datanascimento", "campo obrigatório");
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
		obj.setRg(txtRg.getText());
		if (Utilitarios.ValidaCPF(Utilitarios.removeMascara(txtCpf.getText())) == true) {
			obj.setCpf(txtCpf.getText());
		} else {

			Alertas.showAlert("Cpf Invalido", null, "Por favor informe um cpf valido", AlertType.INFORMATION);
			throw exception;
		}
		obj.setDataNascimento(txtDataNascimento.getText());

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setPessoaFisica(Fisica entidadeFisica) {
		this.entidadeFisica = entidadeFisica;
	}

	public void setPessoa(Pessoa entidade) {
		this.entidade = entidade;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {

		Mascaras.numericField(txtIdFuncionarios);
		Mascaras.maxField(txtNome, 40);
		Mascaras.maxField(txtNomeFantasia, 50);
		Mascaras.numericField(txtRg);
		Mascaras.cpfField(txtCpf);
		Mascaras.cnpjField(txtCnpj);
		Mascaras.dateField(txtDataNascimento);
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

	public void updateFormDataFisica() {

		txtIdFuncionarios.setText(String.valueOf(entidade.getIdPessoa()));
		txtNome.setText(entidadeFisica.getNome());
		txtRg.setText(entidadeFisica.getRg());
		txtCpf.setText(entidadeFisica.getCpf());
		txtDataNascimento.setText(entidadeFisica.getDataNascimento());
		txtTelefoneFixo.setText(entidadeFisica.getTelefoneFixo());
		txtTelefoneCelular.setText(entidadeFisica.getTelefoneCelular());
		txtEmail.setText(entidadeFisica.getEmail());
		txtCep.setText(entidadeFisica.getCep());
		txtCidade.setText(entidadeFisica.getCidade());
		txtUf.setText(entidadeFisica.getUf());
		txtEndereco.setText(entidadeFisica.getEndereco());
		txtBairro.setText(entidadeFisica.getBairro());
		txtNumero.setText(Integer.toString(entidadeFisica.getNumero()));
		txtComplemento.setText(entidadeFisica.getComplemento());

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
		if (fields.contains("rg")) {
			lbErrorRg.setText(errors.get("rg"));

		}
		if (fields.contains("cpf")) {
			lbErrorCpf.setText(errors.get("cpf"));

		}
		if (fields.contains("datanascimento")) {
			lbErrorDatanascimento.setText(errors.get("datanascimento"));

		}
	}

}
