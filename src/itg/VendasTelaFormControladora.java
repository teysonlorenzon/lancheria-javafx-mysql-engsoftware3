package itg;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import itg.listeners.DataChangeListener;
import itg.util.Alertas;
import itg.util.Mascaras;
import itg.util.Restricao;
import itg.util.Utilitarios;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entidades.Lanches;
import model.entidades.Pessoa;
import model.entidades.Produtos;
import model.entidades.Vendas;
import model.exception.ValidationException;
import model.servicos.CadastroClientesServico;
import model.servicos.CadastroFornecedoresServico;
import model.servicos.CadastroLanchesServico;
import model.servicos.CadastroProdutosServico;
import model.servicos.VendasServico;

public class VendasTelaFormControladora implements Initializable {

	private Vendas entidade;
	private List<Produtos> listProd = new ArrayList<>();
	private ObservableList<Produtos> obsList;
	private List<String> arrayListView = new ArrayList<>();
	private ObservableList<String> atualizaListView;

	private List<Lanches> listLanche = new ArrayList<>();
	private List<Pessoa> listClientes = new ArrayList<>();

	private VendasServico servico = new VendasServico();
	private CadastroProdutosServico servicoProd = new CadastroProdutosServico();
	private CadastroFornecedoresServico servicoFunc = new CadastroFornecedoresServico();
	private CadastroClientesServico servicoCli = new CadastroClientesServico();
	private CadastroLanchesServico servicoLanc = new CadastroLanchesServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private ComboBox cbLanche;
	@FXML
	private ComboBox cbCliente;
	@FXML
	private ListView<String> lvDescricao;

	@FXML
	private TextField txtValor;
	@FXML
	private TextField txtFuncionario;
	@FXML
	private TextField txtDinheiroCliente;
	@FXML
	private ImageView imgLanche;
	@FXML
	private TextField txtData;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private TableView<Produtos> tbIngredientes;
	@FXML
	private TableColumn<Produtos, Produtos> tcAdicionar;
	@FXML
	private TableColumn<Produtos, Produtos> tcRetirar;
	@FXML
	private TableColumn<Produtos, Produtos> tcIngredientes;

	@FXML
	private Label lbErrorLanche;
	@FXML
	private Label lbErrorValor;
	@FXML
	private Label lbErrorDinheiroCliente;
	@FXML
	private Label lbErrorCliente;
	@FXML
	private Label lbErrorFuncionario;
	@FXML
	private Label lbErrorData;

	private Map<String, Integer> quantidadePorItem = new HashMap<String, Integer>();
	private List<String> st = new ArrayList<>();

	public void onTcAdicionar(Produtos obj) {

		Integer value = 0;
		Integer pegaIndex = 0;
		if (quantidadePorItem.containsKey(obj.getNomeProdutos())) {
			value = quantidadePorItem.get(obj.getNomeProdutos());
			pegaIndex = arrayListView.indexOf(obj.getNomeProdutos() + " | " + String.valueOf(value));
			quantidadePorItem.put(obj.getNomeProdutos(), value + 1);
			arrayListView.set(pegaIndex, obj.getNomeProdutos() + " | " + String.valueOf(value + 1));

		} else {
			quantidadePorItem.putIfAbsent(obj.getNomeProdutos(), 1); // criou
			value = quantidadePorItem.get(obj.getNomeProdutos());
			arrayListView.add(obj.getNomeProdutos() + " | " + String.valueOf(value));
		}
		Double incrementa = Double.parseDouble(txtValor.getText()) + obj.getPreco();
		txtValor.setText(String.valueOf(incrementa));
		atualizaListView = FXCollections.observableArrayList(arrayListView);
		lvDescricao.setItems(atualizaListView);

	}

	public void onTcRemover(Produtos obj) {
		Integer value = quantidadePorItem.get(obj.getNomeProdutos());
		Integer pegaIndex = arrayListView.indexOf(obj.getNomeProdutos() + " | " + String.valueOf(value));

		if (quantidadePorItem.get(obj.getNomeProdutos()) > 1) {
			arrayListView.set(pegaIndex, obj.getNomeProdutos() + " | " + String.valueOf(value - 1));
			quantidadePorItem.put(obj.getNomeProdutos(), value - 1);

		} else {
			arrayListView.remove(obj.getNomeProdutos() + " | " + String.valueOf(value));
			quantidadePorItem.remove(obj.getNomeProdutos());
		}
		Double tira = Double.parseDouble(txtValor.getText()) - obj.getPreco();
		txtValor.setText(String.valueOf(tira));
		atualizaListView = FXCollections.observableArrayList(arrayListView);
		lvDescricao.setItems(atualizaListView);
	}

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidade = getFormDataVendas();
			servico.iniciarUpdateOuIserirVendas(entidade);
			notifyDataChangeListeners();
			Utilitarios.currentStage(event).close();

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

	private Vendas getFormDataVendas() {
		String stNome = "";
		String stQuant = "";
		Lanches obj2 = new Lanches();
		Vendas obj = new Vendas();
		Pessoa obj3 = new Pessoa();
		Pessoa obj4 = new Pessoa();

		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtValor.getText() == null || txtValor.getText().trim().equals("")) {
			exception.addError("valor", "campo obrigatório");
		}

		if (String.valueOf(lvDescricao.getItems()).equals("[]")) {
			exception.addError("descricao", "campo obrigatório");
		}
		if (txtDinheiroCliente.getText() == null || txtDinheiroCliente.getText().trim().equals("")) {
			exception.addError("valortroco", "campo obrigatório");
		} else {
			Double troco = Double.parseDouble(txtDinheiroCliente.getText()) - Double.parseDouble(txtValor.getText());

			if (troco >= 0) {
				String msg = "Valor do Lanche: " + txtValor.getText() + "\nRecebido: " + txtDinheiroCliente.getText()
						+ "\nTroco: " + troco;
				Alertas.showAlert("Troco", null, msg, AlertType.INFORMATION);
			} else {
				exception.addError("valortroco", "falta dinheiro");
			}

		}
		if (txtFuncionario.getText() == null || txtFuncionario.getText().trim().equals("")) {
			exception.addError("funcionario", "campo obrigatório");
		}
		if (txtData.getText() == null || txtData.getText().trim().equals("")) {
			exception.addError("data", "campo obrigatório");
		}

		if (cbLanche.valueProperty().get() == null || cbLanche.valueProperty().get().equals("")) {
			exception.addError("lanche", "campo obrigatório");
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		String stJunta = "";

		for (String key : quantidadePorItem.keySet()) {
			if (stNome.equals("") && stQuant.equals("")) {
				stNome = key;
				stQuant = String.valueOf(quantidadePorItem.get(key));
				stJunta = String.valueOf(quantidadePorItem.get(key)) + " " + key;
			} else {
				stNome += "," + key;
				stQuant += "," + String.valueOf(quantidadePorItem.get(key));
				stJunta += "," + String.valueOf(quantidadePorItem.get(key)) + " " + key;
			}

		}

		obj2 = servicoLanc.buscarNome(String.valueOf(cbLanche.valueProperty().get()));

		try {
			obj4 = servicoCli.buscarNome(String.valueOf(cbCliente.valueProperty().get()));
			obj.setidClienteSaida(obj4.getIdPessoa());
		} catch (NullPointerException e) {

		}
		
		
		obj.setDescriSaida(stJunta);

		obj.setDescricao(stNome);
		obj.setQuantidade(stQuant);
		obj.setIdLancheSiada(obj2.getIdLanches());

		obj.setValorSaida(Double.parseDouble(txtValor.getText()));

		obj3 = servicoFunc.buscarNome(txtFuncionario.getText());

		obj.setIdFuncionarioSaida(obj3.getIdPessoa());
		obj.setDataSaida(txtData.getText());

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setVendas(Vendas entidade) {
		this.entidade = entidade;
	}

	public void setVendasServico(VendasServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		updateTableView();
		criarListaComboBox();
		initializeNodes();

	}

	private void initializeNodes() {

		Restricao.setTextFieldDouble(txtValor);
		Restricao.setTextFieldDouble(txtDinheiroCliente);
		Mascaras.dateField(txtData);

	}

	private void converteStringParaList(String descri) {
		arrayListView.clear();
		quantidadePorItem.clear();
		String stNumero = descri.replaceAll("\\D", "");
		String stDesc = descri;
		for (int i = 0; i < stNumero.length(); i++) {

			char numero = stNumero.charAt(i);
			String converte = String.valueOf(numero);
			String re = converte + " ";
			String cortou = stDesc.replace(re, "");
			String array[] = cortou.split(",");
			quantidadePorItem.putIfAbsent(array[i], Integer.parseInt(converte));
			Integer value = quantidadePorItem.get(array[i]);
			arrayListView.add(array[i] + " | " + String.valueOf(value));
		}
		atualizaListView = FXCollections.observableArrayList(arrayListView);
		lvDescricao.setItems(atualizaListView);

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("cliente")) {
			lbErrorCliente.setText(errors.get("cliente"));
		}
		if (fields.contains("data")) {
			lbErrorData.setText(errors.get("data"));
		}
		if (fields.contains("valortroco")) {
			lbErrorDinheiroCliente.setText(errors.get("valortroco"));
		}
		if (fields.contains("valor")) {
			lbErrorValor.setText(errors.get("valor"));
		}
		if (fields.contains("funcionario")) {
			lbErrorFuncionario.setText(errors.get("funcionario"));
		}
		if (fields.contains("lanche")) {
			lbErrorLanche.setText(errors.get("lanche"));
		}

	}

	public void updateTableView() {

		listProd = servicoProd.buscarProdutos();
		obsList = FXCollections.observableArrayList(listProd);
		tbIngredientes.setItems(obsList);
		txtFuncionario.setText(LoginTelaControladora.getGuardaFuncionario());
		initializarNodes();
		initEditButtons();
		initRemoveButtons();

	}

	public void initializarNodes() {

		DateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date dtAtual = new Date();
		tcIngredientes.setCellValueFactory(new PropertyValueFactory<>("nomeProdutos"));
		txtData.setText(dtFormat.format(dtAtual));
	}

	private void initEditButtons() {
		tcAdicionar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcAdicionar.setCellFactory(param -> new TableCell<Produtos, Produtos>() {
			private final Button button = new Button("+");

			@Override
			protected void updateItem(Produtos obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> onTcAdicionar(obj));
			}
		});
	}

	private void initRemoveButtons() {
		tcRetirar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcRetirar.setCellFactory(param -> new TableCell<Produtos, Produtos>() {
			private final Button button = new Button("-");

			@Override
			protected void updateItem(Produtos obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> onTcRemover(obj));
			}
		});
	}

	public void criarListaComboBox() {

		listLanche = servicoLanc.buscarLanches();
		listClientes = servicoCli.buscarClientes('T');

		for (Lanches listlanc : listLanche) {
			cbLanche.getItems().add(listlanc.getNomeLanches());
		}
		for (Pessoa listCli : listClientes) {
			cbCliente.getItems().add(listCli.getNome());
		}

	}

	@FXML
	public void clickComboBoxLanche() {
		Lanches obj = new Lanches();
		cbLanche.valueProperty().get();
		obj = servicoLanc.buscarNome(String.valueOf(cbLanche.valueProperty().get()));
		converteStringParaList(obj.getDescricao());
		txtValor.setText(String.valueOf(obj.getValorLanche()));
		imgLanche.setImage(new Image(obj.getLinkImgLanche()));
	}

}
