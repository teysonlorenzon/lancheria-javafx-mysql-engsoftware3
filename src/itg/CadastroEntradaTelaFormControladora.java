package itg;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Estoque;
import model.entidades.Pessoa;
import model.entidades.Produtos;
import model.exception.ValidationException;
import model.servicos.CadastroEntradaServico;
import model.servicos.CadastroFornecedoresServico;
import model.servicos.CadastroFuncionariosServico;
import model.servicos.CadastroProdutosServico;

public class CadastroEntradaTelaFormControladora implements Initializable {

	LoginTelaControladora ltc = new LoginTelaControladora(); 
	
	private Estoque entidade;
	private List<Produtos> listProd = new ArrayList<>();
	private List<Pessoa> listFornec = new ArrayList<>();
	private Integer armazenaQuantUpdate = 0;

	private CadastroEntradaServico servico = new CadastroEntradaServico();
	private CadastroProdutosServico servicoProd= new CadastroProdutosServico();
	private CadastroFuncionariosServico servicoFunc= new CadastroFuncionariosServico();
	private CadastroFornecedoresServico servicoFornec = new CadastroFornecedoresServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdEntrada;
	@FXML
	private TextField txtDataEntrada;
	@FXML
	private TextField txtQuantidade;
	@FXML
	private TextField txtValor;
	@FXML
	private TextField txtNomeFuncionario;

	@FXML
	private ComboBox cbFornecedores;
	@FXML
	private ComboBox cbProdutos;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private Label lbErrorDataEntrada;
	@FXML
	private Label lbErrorFornecedor;
	@FXML
	private Label lbErrorProduto;
	@FXML
	private Label lbErrorQuantidade;
	@FXML
	private Label lbErrorValor;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidade = getFormDataEntrada();
			servico.iniciarUpdateOuIserirEntrada(entidade);
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

	private Estoque getFormDataEntrada() {
		Estoque obj = new Estoque();
		Pessoa obj2 = new Pessoa();
		Produtos obj3 = new Produtos();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdEntrada.getText() == null || txtIdEntrada.getText().trim().equals("")) {
		} else {
			obj.setIdEntrada(Integer.parseInt(txtIdEntrada.getText()));
		}

		if (txtDataEntrada.getText() == null || txtDataEntrada.getText().trim().equals("")) {
			exception.addError("dataentrada", "campo obrigatório");
		}

		if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {
			exception.addError("quantidade", "campo obrigatório");
		}

		if (txtValor.getText() == null || txtValor.getText().trim().equals("")) {
			exception.addError("valor", "campo obrigatório");
		}

		if (cbFornecedores.valueProperty().get() == null || cbFornecedores.valueProperty().get().equals("")) {
			exception.addError("fornecedores", "campo obrigatório");
		}

		if (cbProdutos.valueProperty().get() == null || cbProdutos.valueProperty().get().equals("")) {
			exception.addError("produtos", "campo obrigatório");
		}

		obj3 = servicoProd.buscarNome((String) cbProdutos.valueProperty().get());
		obj.setIdProdutos(obj3.getIdProdutos());
		obj2 = servicoFornec.buscarNome((String) cbFornecedores.valueProperty().get());
		obj.setIdFornecedores(obj2.getIdPessoa());
		obj2 = servicoFunc.buscarNome(txtNomeFuncionario.getText());
		obj.setIdFuncionario(obj2.getIdPessoa());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		obj.setDataEntrada(txtDataEntrada.getText());
		obj.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
		obj.setValorUnitario(Double.parseDouble(txtValor.getText()));
		

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setEntrada(Estoque entidade) {
		this.entidade = entidade;
	}

	public void setCadastroEntradaServico(CadastroEntradaServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		criarListaComboBox();
		txtNomeFuncionario.setText(ltc.getGuardaFuncionario());
		DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dtAtual = new Date();
		txtDataEntrada.setText(dtFormat.format(dtAtual));

	}

	private void initializeNodes() {

		Mascaras.numericField(txtIdEntrada);
		Mascaras.dateField(txtDataEntrada);
		Mascaras.numericField(txtQuantidade);
		Mascaras.numericField(txtValor);

	}

	public void updateFormDataEntrada() {

		txtIdEntrada.setText(String.valueOf(entidade.getIdEntrada()));
		txtDataEntrada.setText(entidade.getDataEntrada());
		txtQuantidade.setText(String.valueOf(entidade.getQuantidade()));
		txtValor.setText(String.valueOf(entidade.getValorUnitario()));
		txtNomeFuncionario.setText(entidade.getNomeFuncionario());
		cbFornecedores.valueProperty().set(entidade.getNomeFornecedores());
		cbProdutos.valueProperty().set(entidade.getNomeProdutos());
		
		armazenaQuantUpdate = entidade.getQuantidade();

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("dataentrada")) {
			lbErrorDataEntrada.setText(errors.get("dataentrada"));
		}
		if (fields.contains("quantidade")) {
			lbErrorQuantidade.setText(errors.get("quantidade"));
		}
		if (fields.contains("valor")) {
			lbErrorValor.setText(errors.get("valor"));
		}
		if (fields.contains("produtos")) {
			lbErrorProduto.setText(errors.get("produtos"));
		}
	
		if (fields.contains("fornecedores")) {
			lbErrorFornecedor.setText(errors.get("fornecedores"));
		}

	}

	public void criarListaComboBox() {

		listProd = servicoProd.buscarProdutos();
		listFornec = servicoFornec.buscarFornecedores();

		for (Produtos listPro : listProd) {
			cbProdutos.getItems().add(listPro.getNomeProdutos());
		}
		for (Pessoa listFor : listFornec) {
			cbFornecedores.getItems().add(listFor.getNome());
		}

	}

}
