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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Categorias;
import model.entidades.Pessoa;
import model.entidades.Produtos;
import model.exception.ValidationException;
import model.servicos.CadastroCategoriasServico;
import model.servicos.CadastroFornecedoresServico;
import model.servicos.CadastroProdutosServico;

public class CadastroProdutosTelaFormControladora implements Initializable {

	private Produtos entidade;
	private List<Pessoa> listFor = new ArrayList<>();
	private List<Categorias> listCat = new ArrayList<>();

	private CadastroProdutosServico servico = new CadastroProdutosServico();
	private CadastroCategoriasServico servicoCat = new CadastroCategoriasServico();
	private CadastroFornecedoresServico servicoForn = new CadastroFornecedoresServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdProdutos;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtQuantidade;
	@FXML
	private ComboBox cbFornecedor;
	@FXML
	private ComboBox cbCategoria;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private Label lbErrorNome;
	@FXML
	private Label lbErrorQuantidade;
	@FXML
	private Label lbErrorFornecedor;
	@FXML
	private Label lbErrorCategoria;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidade = getFormDataProdutos();
			servico.iniciarUpdateOuIserirProdutos(entidade);
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

	private Produtos getFormDataProdutos() {
		Produtos obj = new Produtos();
		Pessoa obj2 = new Pessoa();
		Categorias obj3 = new Categorias();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdProdutos.getText() == null || txtIdProdutos.getText().trim().equals("")) {
		} else {
			obj.setIdProdutos(Integer.parseInt(txtIdProdutos.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo obrigatório");
		}
		if (txtQuantidade.getText() == null || txtQuantidade.getText().trim().equals("")) {
			exception.addError("quantidade", "campo obrigatório");
		}

		if (cbFornecedor.valueProperty().get() == null || cbFornecedor.valueProperty().get().equals("")) {
			exception.addError("fornecedores", "campo obrigatório");
		}

		if (cbCategoria.valueProperty().get() == null || cbCategoria.valueProperty().get().equals("")) {
			exception.addError("categorias", "campo obrigatório");
		}

		obj2 = servicoForn.buscarNome((String) cbFornecedor.valueProperty().get());
		obj3 = servicoCat.buscarNome((String) cbCategoria.valueProperty().get());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		obj.setNome(txtNome.getText());
		obj.setQuantidade(Integer.parseInt(txtQuantidade.getText()));
		obj.setIdFornecedores(obj2.getIdPessoa());
		obj.setIdCategorias(obj3.getIdCategorias());

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setProdutos(Produtos entidade) {
		this.entidade = entidade;
	}

	public void setCadastroProdutosServico(CadastroProdutosServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		criarListaComboBox();

	}

	private void initializeNodes() {

		Mascaras.numericField(txtIdProdutos);
		Mascaras.maxField(txtNome, 40);
		Mascaras.numericField(txtQuantidade);

	}

	public void updateFormDataProdutos() {

		txtIdProdutos.setText(String.valueOf(entidade.getIdProdutos()));
		txtNome.setText(entidade.getNome());
		txtQuantidade.setText(Integer.toString(entidade.getQuantidade()));
		cbFornecedor.valueProperty().set(entidade.getNomeFornecedores());
		cbCategoria.valueProperty().set(entidade.getNomeCategorias());

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErrorNome.setText(errors.get("nome"));

		}

		if (fields.contains("quantidade")) {
			lbErrorQuantidade.setText(errors.get("quantidade"));

		}
		if (fields.contains("fornecedores")) {
			lbErrorFornecedor.setText(errors.get("fornecedores"));

		}
		if (fields.contains("categorias")) {
			lbErrorCategoria.setText(errors.get("categorias"));

		}

	}

	public void criarListaComboBox() {

		listFor = servicoForn.buscarFornecedores();
		listCat = servicoCat.buscarCategorias();

		for (Pessoa listPes : listFor) {
			cbFornecedor.getItems().add(listPes.getNome());
		}
		for (Categorias listCate : listCat) {
			cbCategoria.getItems().add(listCate.getNome());
		}

	}

}
