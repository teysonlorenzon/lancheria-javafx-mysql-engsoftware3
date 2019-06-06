package itg;

import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entidades.Categorias;
import model.entidades.Lanches;
import model.entidades.Produtos;
import model.exception.ValidationException;
import model.servicos.CadastroCategoriasServico;
import model.servicos.CadastroLanchesServico;
import model.servicos.CadastroProdutosServico;

public class CadastroLanchesTelaFormControladora implements Initializable {

	private Lanches entidade;
	private List<Produtos> listProd = new ArrayList<>();
	private ObservableList<Produtos> obsList;
	private List<String> arrayListView = new ArrayList<>();
	private ObservableList<String> atualizaListView;

	private Categorias listCat;
	private URL url;

	private CadastroLanchesServico servico = new CadastroLanchesServico();
	private CadastroProdutosServico servicoProd = new CadastroProdutosServico();
	private CadastroCategoriasServico servicoCat = new CadastroCategoriasServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdLanches;
	@FXML
	private TextField txtNome;
	@FXML
	private ListView<String> lvDescricao;
	@FXML
	private TextField txtValor;
	@FXML
	private ImageView imgLanche;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;
	@FXML
	private Button btAdcionarImagem;

	@FXML
	private TableView<Produtos> tbIngredientes;
	@FXML
	private TableColumn<Produtos, Produtos> tcAdicionar;
	@FXML
	private TableColumn<Produtos, Produtos> tcRetirar;
	@FXML
	private TableColumn<Produtos, Produtos> tcIngredientes;

	@FXML
	private Label lbErrorNome;
	@FXML
	private Label lbErrorDescricao;
	@FXML
	private Label lbErrorValor;

	private Map<String, Integer> quantidadePorItem = new HashMap<String, Integer>();
	private List<String> st = new ArrayList<>();

	@FXML
	public void onBtAdcionarImagem() {

		url = Utilitarios.buscarImagem(imgLanche);
	}

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
		atualizaListView = FXCollections.observableArrayList(arrayListView);
		lvDescricao.setItems(atualizaListView);
	}

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidade = getFormDataLanches();
			servico.iniciarUpdateOuIserirLanches(entidade);
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

	private Lanches getFormDataLanches() {
		String stNome = "";
		String stQuant = "";
		Lanches obj = new Lanches();
		Categorias obj3 = new Categorias();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdLanches.getText() == null || txtIdLanches.getText().trim().equals("")) {
		} else {
			obj.setIdLanches(Integer.parseInt(txtIdLanches.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo obrigatório");
		}

		if (String.valueOf(lvDescricao.getItems()).equals("[]")) {
			exception.addError("descricao", "campo obrigatório");
		}
		if (txtValor.getText() == null || txtValor.getText().trim().equals("")) {
			exception.addError("valor", "campo obrigatório");
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		if (url == null || url.equals("")) {
			obj.setLinkImgLanche(imgLanche.getImage().getUrl());
		} else {
			obj.setLinkImgLanche(String.valueOf(url));
		}

		for (String key : quantidadePorItem.keySet()) {
			if (stNome.equals("") && stQuant.equals("")) {
				stNome = key;
				stQuant = String.valueOf(quantidadePorItem.get(key));
			} else {
				stNome += "," + key;
				stQuant += "," + String.valueOf(quantidadePorItem.get(key));
			}

		}

		obj.setQuantidade(stQuant);
		obj.setNomeLanches(txtNome.getText());

		obj.setDescricao(stNome);
		obj.setValorLanche(Double.parseDouble(txtValor.getText()));

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setLanches(Lanches entidade) {
		this.entidade = entidade;
	}

	public void setCadastroLanchesServico(CadastroLanchesServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		updateTableView();
		initializeNodes();

	}

	private void initializeNodes() {

		Mascaras.numericField(txtIdLanches);
		Mascaras.maxField(txtNome, 40);
		Restricao.setTextFieldDouble(txtValor);

	}

	private void converteStringParaList(String descri) {
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

	public void updateFormDataLanches() {

		txtIdLanches.setText(String.valueOf(entidade.getIdLanches()));
		txtNome.setText(entidade.getNomeLanches());
		txtValor.setText(String.valueOf(entidade.getValorLanche()));
		converteStringParaList(entidade.getDescricao());
		imgLanche.setImage(new Image(entidade.getLinkImgLanche()));

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErrorNome.setText(errors.get("nome"));

		}

		if (fields.contains("descricao")) {
			lbErrorDescricao.setText(errors.get("descricao"));

		}
		if (fields.contains("valor")) {
			lbErrorValor.setText(errors.get("valor"));

		}

	}

	public void updateTableView() {

		listCat = servicoCat.buscarNome("Ingredientes");
		listProd = servicoProd.buscarListProdutosPorCategorias(listCat.getIdCategorias());
		obsList = FXCollections.observableArrayList(listProd);
		tbIngredientes.setItems(obsList);
		initializarNodes();
		initEditButtons();
		initRemoveButtons();

	}

	public void initializarNodes() {

		tcIngredientes.setCellValueFactory(new PropertyValueFactory<>("nomeProdutos"));
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

}
