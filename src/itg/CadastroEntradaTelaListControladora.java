package itg;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import db.DbIntegrityException;
import itg.listeners.DataChangeListener;
import itg.util.Alertas;
import itg.util.Mascaras;
import itg.util.Utilitarios;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Categorias;
import model.entidades.Pessoa;
import model.entidades.Entrada;
import model.servicos.CadastroEntradaServico;

public class CadastroEntradaTelaListControladora implements Initializable, DataChangeListener {

	private CadastroEntradaServico servicoProd;
	private ObservableList<Entrada> obsList;
	private String condicao = "tudo";
	private Entrada entidade;
	private List<CheckBox> chlist = new ArrayList<>();
	private List<Entrada> list = new ArrayList<>();

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	@FXML
	private TableView<Entrada> tbCadastroEntrada;
	@FXML
	private TableColumn<Entrada, Integer> tcIdEntrada;
	@FXML
	private TableColumn<Entrada, String> tcProduto;
	@FXML
	private TableColumn<Entrada, String> tcData;
	@FXML
	private TableColumn<Entrada, String> tcValor;
	@FXML
	private TableColumn<Entrada, String> tcQuantidade;
	@FXML
	private TableColumn<Entrada, String> tcFornecedor;
	@FXML
	private TableColumn<Entrada, String> tcFuncionario;
	@FXML
	private TableColumn<Entrada, Entrada> tcSelecionar;

	@FXML
	private TextField txtPesquisarId;
	@FXML
	private TextField txtPesquisarNome;

	@FXML
	private Button btEditar;
	@FXML
	private Button btExcluir;
	@FXML
	private Button btCancelarSelecao;
	@FXML
	private ToolBar tbEditar;

	@FXML
	private Button btNovo;
	@FXML
	private MenuButton mbPesquisar;
	@FXML
	private MenuItem miNome;
	@FXML
	private MenuItem miId;
	@FXML
	private MenuItem miTudo;

	@FXML
	public void onBtPesquisarAction() {
		updateTableView();
		initializarNodes();
		actionToolBar();
	}

	@FXML
	public void onBtMenuItemTudo() {
		mbPesquisar.setText("Pesquisar");
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setCondicao("tudo");
		onBtPesquisarAction();

	}

	@FXML
	public void onBtMenuItemNome() {
		mbPesquisar.setText("Nome");
		setarInicioTxtPesquisar(txtPesquisarId);
		txtPesquisarNome.setVisible(true);
		txtPesquisarNome.setText("");
		setCondicao("nome");
	}

	@FXML
	public void onBtMenuItemId() {
		mbPesquisar.setText("Id");
		setarInicioTxtPesquisar(txtPesquisarNome);
		txtPesquisarId.setVisible(true);
		txtPesquisarId.setText("");
		setCondicao("id");
	}

	public void actionToolBar() {
		btEditar.setVisible(false);
		btExcluir.setVisible(false);
		btCancelarSelecao.setVisible(false);
		tbEditar.setVisible(false);
	}

	public void actionBts() {
		for (CheckBox listar : chlist) {
			if (listar.isSelected() == false) {
				listar.setDisable(false);
				listar.setSelected(false);
			} else {
				listar.setDisable(false);
				listar.setSelected(false);
			}
		}
	}

	@FXML
	public void onBtCancelarSelecao() {
		actionBts();
		actionToolBar();
	}

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		actionBts();
		actionToolBar();
		Stage parentStage = Utilitarios.currentStage(event);
		criarForm("/itg/CadastroEntradaFormTela.fxml", parentStage);

	}

	private void setarInicioTxtPesquisar(TextField txt) {
		txt.setVisible(false);
		txt.setText("");
	}

	public void setCadastroEntradaServico(CadastroEntradaServico servicoProd) {
		this.servicoProd = servicoProd;
	}

	public void updateTableView() {

		if (getCondicao().equals("tudo")) {
			int cont = 0;

			list = servicoProd.buscarEntrada();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroEntrada.setItems(obsList);
			initSelecionarCheckBox();
		}

		else if (getCondicao().equals("id")) {
			list = servicoProd.buscarListPorId((Integer.parseInt(txtPesquisarId.getText())));
			obsList = FXCollections.observableArrayList(list);
			tbCadastroEntrada.setItems(obsList);
			initSelecionarCheckBox();

		} else if (getCondicao().equals("nome")) {
			list = servicoProd.buscarListPorNome(txtPesquisarNome.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroEntrada.setItems(obsList);
			initSelecionarCheckBox();
		}

	}

	public void initializarNodes() {

		tcIdEntrada.setCellValueFactory(new PropertyValueFactory<>("idEntrada"));
		tcProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProdutos"));
		tcData.setCellValueFactory(new PropertyValueFactory<>("dataEntrada"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tcValor.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
		tcFornecedor.setCellValueFactory(new PropertyValueFactory<>("nomeFornecedores"));
		tcFuncionario.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionario"));

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroEntrada.prefHeightProperty().bind(stage.heightProperty());

	}

	private void tecladoEnter(TextField txt) {
		txt.setVisible(false);
		txt.setOnKeyPressed(k -> {
			final KeyCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
			if (ENTER.match(k)) {
				onBtPesquisarAction();
			}
		});
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		Mascaras.maxField(txtPesquisarNome, 60);
		Mascaras.numericField(txtPesquisarId);

		tecladoEnter(txtPesquisarId);
		tecladoEnter(txtPesquisarNome);

		initializarNodes();
		actionToolBar();
		actionBts();
	}

	@Override
	public void onDataChanged() {
		updateTableView();
		actionToolBar();
		actionBts();
	}

	@FXML
	public void onBtEditarAction(ActionEvent event) {
		Stage parentStage = Utilitarios.currentStage(event);
		if (entidade == null || entidade.equals("")) {
			Alertas.showAlert("Atenção", null, "Selecione um cliente", AlertType.WARNING);
		}

		criarFormEntrada(entidade, "/itg/CadastroEntradaFormTela.fxml", parentStage);

	}

	@FXML
	public void onBtExcluirAction() {

		if (entidade == null) {
			Alertas.showAlert("Atenção", null, "Selecione um cliente", AlertType.WARNING);
		} else {

			removeEntity(entidade);
		}
	}

	private void criarFormEntrada(Entrada obj, String absoluteName, Stage parentStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroEntradaTelaFormControladora controller = loader.getController();

			controller.subscribeDataChangeListener(this);

			controller.setEntrada(obj);
			controller.updateFormDataEntrada();

			setCondicao("tudo");
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com as informações");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alertas.showAlert("IO Exception", "Error loding view", e.getMessage(), AlertType.ERROR);
		}
	}

	private void criarForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroEntradaTelaFormControladora controller = loader.getController();
			controller.setCadastroEntradaServico(new CadastroEntradaServico());
			controller.subscribeDataChangeListener(this);

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Entre com as informações");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parentStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alertas.showAlert("IO Exception", "Error loding view", e.getMessage(), AlertType.ERROR);
		}
	}

	private void initSelecionarCheckBox() {

		tcSelecionar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcSelecionar.setCellFactory(param -> new TableCell<Entrada, Entrada>() {

			private final CheckBox chkSelecionar = new CheckBox("");

			@Override
			protected void updateItem(Entrada obj, boolean empty) {

				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				if (obj != null) {
					chlist.add(chkSelecionar);
				}
				setGraphic(chkSelecionar);

				chkSelecionar.setOnAction(event -> checkBoxSelecionado(obj));

			}

		});

	}

	private void checkBoxSelecionado(Entrada obj) {
		entidade = obj;

		for (CheckBox listar : chlist) {
			if (listar.isSelected() == false) {
				listar.setDisable(true);
			}
		}

		for (CheckBox listar : chlist) {
			if (listar.isSelected() == true) {
				listar.setDisable(true);
			} else {
				listar.setDisable(true);
				listar.setSelected(false);
			}
			btEditar.setVisible(true);
			btExcluir.setVisible(true);
			btCancelarSelecao.setVisible(true);
			tbEditar.setVisible(true);

		}

	}

	private void removeEntity(Entrada obj) {

		Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza em excluir o item?");

		if (result.get() == ButtonType.OK) {

			try {

				setCondicao("tudo");
				servicoProd.excluirEntrada(obj);
				updateTableView();

			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
			actionToolBar();
			actionBts();

		}

	}

}
