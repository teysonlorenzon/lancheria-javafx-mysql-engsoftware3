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
import model.servicos.CadastroCategoriasServico;

public class CadastroCategoriasTelaListControladora implements Initializable, DataChangeListener {

	private CadastroCategoriasServico servico;
	private ObservableList<Categorias> obsList;
	private String condicao = "tudo";
	private Categorias entidade;
	private List<CheckBox> chlist = new ArrayList<>();
	private List<Categorias> list = new ArrayList<>();

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	@FXML
	private TableView<Categorias> tbCadastroCategorias;
	@FXML
	private TableColumn<Categorias, Integer> tcIdCategoria;
	@FXML
	private TableColumn<Categorias, String> tcNome;
	@FXML
	private TableColumn<Categorias, Categorias> tcSelecionar;

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
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setCondicao("tudo");
		onBtPesquisarAction();
	}

	@FXML
	public void onBtMenuItemNome() {
		setarInicioTxtPesquisar(txtPesquisarId);
		txtPesquisarNome.setVisible(true);
		txtPesquisarNome.setText("");
		setCondicao("nome");
	}

	@FXML
	public void onBtMenuItemId() {

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
		criarForm("/itg/CadastroCategoriasFormTela.fxml", parentStage);

	}

	private void setarInicioTxtPesquisar(TextField txt) {
		txt.setVisible(false);
		txt.setText("");
	}

	public void setCadastroCategoriasServico(CadastroCategoriasServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {

		if (getCondicao().equals("tudo")) {
			list = servico.buscarCategorias();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroCategorias.setItems(obsList);
			initSelecionarCheckBox();

		} else if (getCondicao().equals("id")) {
			list = servico.buscarListPorId(Integer.parseInt(txtPesquisarId.getText()));
			obsList = FXCollections.observableArrayList(list);
			tbCadastroCategorias.setItems(obsList);
			initSelecionarCheckBox();

		} else if (getCondicao().equals("nome")) {
			list = servico.buscarListPorNome(txtPesquisarNome.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroCategorias.setItems(obsList);
			initSelecionarCheckBox();
		}
	}

	public void initializarNodes() {

		tcIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategorias"));
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroCategorias.prefHeightProperty().bind(stage.heightProperty());

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

		criarFormCategorias(entidade, "/itg/CadastroCategoriasFormTela.fxml", parentStage, getCondicao());

	}

	@FXML
	public void onBtExcluirAction() {

		if (entidade == null) {
			Alertas.showAlert("Atenção", null, "Selecione um cliente", AlertType.WARNING);
		} else {

			removeEntity(entidade);
		}
	}

	private void criarFormCategorias(Categorias obj, String absoluteName, Stage parentStage, String tipo) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroCategoriasTelaFormControladora controller = loader.getController();

			controller.subscribeDataChangeListener(this);

			controller.setCategorias(obj);
			controller.setTipo(tipo);
			//controller.setCategoriasP(servico.buscarId(entidade.getIdPessoa()));

			controller.updateFormDataCategorias();

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

			CadastroCategoriasTelaFormControladora controller = loader.getController();
			controller.setCadastroCategoriasServico(new CadastroCategoriasServico());
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
		tcSelecionar.setCellFactory(param -> new TableCell<Categorias, Categorias>() {

			private final CheckBox chkSelecionar = new CheckBox("");

			@Override
			protected void updateItem(Categorias obj, boolean empty) {

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

	private void checkBoxSelecionado(Categorias obj) {
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

	private void removeEntity(Categorias obj) {

		Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza em excluir o item?");

		if (result.get() == ButtonType.OK) {

			if (servico == null) {
				throw new IllegalStateException("Servico está nulo");
			}
			try {

				setCondicao("tudo");
				servico.excluirCategorias(obj);
				updateTableView();

			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
			actionToolBar();
			actionBts();

		}

	}

}
