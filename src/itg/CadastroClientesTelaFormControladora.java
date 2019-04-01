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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.exception.ValidationException;
import model.servicos.CadastroClientesServico;

public class CadastroClientesTelaFormControladora implements Initializable {

	private Fisica entidadeFisica = new Fisica();
	private Juridica entidadeJuridica = new Juridica();
	private Pessoa entidade;

	private CadastroClientesServico servico;
	private String tipo;

	public String setTipo(String tipo) {
		return this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdClientes;
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
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private RadioButton rbFisica;
	@FXML
	private RadioButton rbJuridica;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (entidadeJuridica == null) {
			throw new IllegalStateException("Entidade está nullo");
		}
		if (entidadeFisica == null) {
			throw new IllegalStateException("Entidade está nullo");
		}
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			if (getTipo().equals("fisica")) {
				entidadeFisica = getFormDataFisica();
				servico.iniciarUpdateOuIserirPessoaFisica(entidadeFisica);
				notifyDataChangeListeners();
				Utilitarios.currentStage(event).close();
				Alertas.showAlert("Informação", null, "Operação realizada com sucesso", AlertType.INFORMATION);

			} else {
				entidadeJuridica = getFormDataJuridica();
				servico.iniciarUpdateOuIserirPessoaJuridica(entidadeJuridica);
				notifyDataChangeListeners();
				Utilitarios.currentStage(event).close();
				Alertas.showAlert("Informação", null, "Operação realizada com sucesso", AlertType.INFORMATION);
				
			}

		} catch (DbException e) {
			Alertas.showAlert("Error Save Object", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessage(e.getErrors());
		}
	}

	@FXML
	public void onRbFisicaAction() {
		rbJuridica.setSelected(false);
		setTipo("fisica");
	}

	@FXML
	public void onRbJuridicaAction() {
		rbFisica.setSelected(false);
		setTipo("juridica");

	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private Juridica getFormDataJuridica() {
		Juridica obj = new Juridica();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdClientes.getText() == null || txtIdClientes.getText().trim().equals("")) {
		} else {
			obj.setIdPessoa(Integer.parseInt(txtIdClientes.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo vazio");
		}
		if (txtCidade.getText() == null || txtCidade.getText().trim().equals("")) {
			exception.addError("cidade", "campo vazio");
		}

		if (txtCep.getText() == null || txtCep.getText().trim().equals("")) {
			exception.addError("cep", "campo vazio");
		}

		if (txtUf.getText() == null || txtUf.getText().trim().equals("")) {
			exception.addError("uf", "campo vazio");
		}
		if (txtBairro.getText() == null || txtBairro.getText().trim().equals("")) {
			exception.addError("bairro", "campo vazio");
		}
		if (txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			exception.addError("endereco", "campo vazio");
		}
		if (txtNumero.getText() == null || txtNumero.getText().trim().equals("")) {
			exception.addError("numero", "campo vazio");
		}
		if (txtTelefoneFixo.getText() == null || txtTelefoneFixo.getText().trim().equals("")) {
			exception.addError("telefonefixo", "campo vazio");
		}
		if (txtTelefoneCelular.getText() == null || txtTelefoneCelular.getText().trim().equals("")) {
			exception.addError("telefonecelular", "campo vazio");
		}
		if (txtComplemento.getText() == null || txtComplemento.getText().trim().equals("")) {
			exception.addError("complemento", "campo vazio");
		}

		if (txtCnpj.getText() == null || txtCnpj.getText().trim().equals("")) {
			exception.addError("cnpj", "campo vazio");
		}
		if (txtNomeFantasia.getText() == null || txtNomeFantasia.getText().trim().equals("")) {
			exception.addError("nomefantasia", "campo vazio");
		}

		obj.setNome(txtNome.getText());
		obj.setCidade(txtCidade.getText());
		obj.setCep(txtCep.getText());
		obj.setUf(txtUf.getText());
		obj.setBairro(txtUf.getText());
		obj.setEndereco(txtUf.getText());
		obj.setNumero(Integer.parseInt(txtNumero.getText()));
		obj.setTelefoneFixo(txtUf.getText());
		obj.setTelefoneCelular(txtTelefoneCelular.getText());
		obj.setComplemento(txtComplemento.getText());
		obj.setEmail(txtEmail.getText());
		obj.setCnpj(txtCnpj.getText());
		obj.setNomeFantasia(txtNomeFantasia.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		return obj;

	}

	private Fisica getFormDataFisica() {

		Fisica obj = new Fisica();

		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdClientes.getText() == null || txtIdClientes.getText().trim().equals("")) {
		} else {
			obj.setIdPessoa(Integer.parseInt(txtIdClientes.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo vazio");
		}
		if (txtCidade.getText() == null || txtCidade.getText().trim().equals("")) {
			exception.addError("cidade", "campo vazio");
		}

		if (txtCep.getText() == null || txtCep.getText().trim().equals("")) {
			exception.addError("cep", "campo vazio");
		}

		if (txtUf.getText() == null || txtUf.getText().trim().equals("")) {
			exception.addError("uf", "campo vazio");
		}
		if (txtBairro.getText() == null || txtBairro.getText().trim().equals("")) {
			exception.addError("bairro", "campo vazio");
		}
		if (txtEndereco.getText() == null || txtEndereco.getText().trim().equals("")) {
			exception.addError("endereco", "campo vazio");
		}
		if (txtNumero.getText() == null || txtNumero.getText().trim().equals("")) {
			exception.addError("numero", "campo vazio");
		}
		if (txtTelefoneFixo.getText() == null || txtTelefoneFixo.getText().trim().equals("")) {
			exception.addError("telefonefixo", "campo vazio");
		}
		if (txtTelefoneCelular.getText() == null || txtTelefoneCelular.getText().trim().equals("")) {
			exception.addError("telefonecelular", "campo vazio");
		}
		if (txtComplemento.getText() == null || txtComplemento.getText().trim().equals("")) {
			exception.addError("complemento", "campo vazio");
		}

		if (txtRg.getText() == null || txtRg.getText().trim().equals("")) {
			exception.addError("rg", "campo vazio");
		}
		if (txtCpf.getText() == null || txtCpf.getText().trim().equals("")) {
			exception.addError("cpf", "campo vazio");
		}
		if (txtDataNascimento.getText() == null || txtDataNascimento.getText().trim().equals("")) {
			exception.addError("datanascimento", "campo vazio");
		}
		obj.setNome(txtNome.getText());
		obj.setCidade(txtCidade.getText());
		obj.setCep(txtCep.getText());
		obj.setUf(txtUf.getText());
		obj.setBairro(txtUf.getText());
		obj.setEndereco(txtUf.getText());
		obj.setNumero(Integer.parseInt(txtNumero.getText()));
		obj.setTelefoneFixo(txtUf.getText());
		obj.setTelefoneCelular(txtTelefoneCelular.getText());
		obj.setComplemento(txtComplemento.getText());
		obj.setEmail(txtEmail.getText());
		obj.setRg(Integer.parseInt(txtRg.getText()));
		obj.setCpf(txtCpf.getText());
		obj.setDataNascimento(txtDataNascimento.getText());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
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

	public void setPessoaJuridica(Juridica entidadeJuridica) {
		this.entidadeJuridica = entidadeJuridica;
	}

	public void setCadastroClientesServico(CadastroClientesServico servico) {
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
		Restricao.setTextFieldInteger(txtIdClientes);
		Restricao.setTextFieldMaxLength(txtNome, 30);
	}

	public void updateFormDataFisicaJuridica() {
		if (entidadeFisica == null) {
			throw new IllegalStateException("Entidade está nulo");
		}

		
		if (getTipo().equals("fisica")) {
			entidadeFisica = servico.buscarNomeFisica(entidade.getNome());

			txtIdClientes.setText(String.valueOf(entidade.getIdPessoa()));
			txtNome.setText(entidade.getNome());
			txtRg.setText(Integer.toString(entidadeFisica.getRg()));
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
			rbFisica.setSelected(true);
			rbJuridica.setVisible(false);
			rbFisica.setVisible(true);
			rbFisica.setDisable(true);
		} else {
			entidadeJuridica = servico.buscarNomeJuridica(entidade.getNome());
		
			txtIdClientes.setText(String.valueOf(entidade.getIdPessoa()));
			txtNome.setText(entidade.getNome());
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
			rbJuridica.setSelected(true);
			rbFisica.setVisible(false);
			rbJuridica.setVisible(true);
			rbJuridica.setDisable(true);
		}

	}

	public void updateFormDataJuridica() {
		if (entidadeFisica == null) {
			throw new IllegalStateException("Entidade está nulo");
		}
		txtIdClientes.setText(String.valueOf(entidadeJuridica.getIdPessoa()));
		txtNome.setText(entidadeJuridica.getNome());
	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			// lbErroNome.setText(errors.get("nome"));

		}
		if (fields.contains("senha")) {
			// lbErroSenha.setText(errors.get("senha"));

		}
		if (fields.contains("resenha")) {
			// lbErroReSenha.setText(errors.get("resenha"));

		}
	}

}
