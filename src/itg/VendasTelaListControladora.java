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
import model.entidades.Vendas;
import model.servicos.VendasServico;

public class VendasTelaListControladora implements Initializable, DataChangeListener {

	private VendasServico servico;
	private ObservableList<Vendas> obsList;
	private String condicao = "tudo";
	private Vendas entidade;
	private List<CheckBox> chlist = new ArrayList<>();
	private List<Vendas> list = new ArrayList<>();

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	@FXML
	private TableView<Vendas> tbCadastroVendas;
	@FXML
	private TableColumn<Vendas, Integer> tcIdVendas;
	@FXML
	private TableColumn<Vendas, String> tcLanche;
	@FXML
	private TableColumn<Vendas, String> tcDataSaida;
	@FXML
	private TableColumn<Vendas, String> tcValorVenda;
	@FXML
	private TableColumn<Vendas, String> tcFuncionario;
	@FXML
	private TableColumn<Vendas, String> tcCliente;
	
	@FXML
	private TableColumn<Vendas, Vendas> tcSelecionar;

	@FXML
	private TextField txtPesquisarId;
	


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
		setCondicao("tudo");
		onBtPesquisarAction();

	}



	@FXML
	public void onBtMenuItemId() {
		mbPesquisar.setText("Id");
		txtPesquisarId.setVisible(true);
		txtPesquisarId.setText("");
		setCondicao("id");
	}

	public void actionToolBar() {
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
		criarForm("/itg/VendasFormTela.fxml", parentStage);

	}

	private void setarInicioTxtPesquisar(TextField txt) {
		txt.setVisible(false);
		txt.setText("");
	}

	public void setVendasServico(VendasServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {

		if (getCondicao().equals("tudo")) {
			int cont = 0;

			list = servico.buscarVendas();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroVendas.setItems(obsList);
			initSelecionarCheckBox();
		}

		else if (getCondicao().equals("id")) {
			list = servico.buscarListPorId((Integer.parseInt(txtPesquisarId.getText())));
			obsList = FXCollections.observableArrayList(list);
			tbCadastroVendas.setItems(obsList);
			initSelecionarCheckBox();

		}

	}

	public void initializarNodes() {

		tcIdVendas.setCellValueFactory(new PropertyValueFactory<>("idSaida"));
		tcLanche.setCellValueFactory(new PropertyValueFactory<>("nomeLancheSaida"));
		tcDataSaida.setCellValueFactory(new PropertyValueFactory<>("dataSaida"));
		tcValorVenda.setCellValueFactory(new PropertyValueFactory<>("valorSaida"));
		tcCliente.setCellValueFactory(new PropertyValueFactory<>("nomeClienteSaida"));
		tcFuncionario.setCellValueFactory(new PropertyValueFactory<>("nomeFuncionarioSaida"));
		


		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroVendas.prefHeightProperty().bind(stage.heightProperty());

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

		Mascaras.numericField(txtPesquisarId);

		tecladoEnter(txtPesquisarId);

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
	public void onBtExcluirAction() {

		if (entidade == null) {
			Alertas.showAlert("Atenção", null, "Selecione um cliente", AlertType.WARNING);
		} else {

			removeEntity(entidade);
		}
	}



	private void criarForm(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			VendasTelaFormControladora controller = loader.getController();
			controller.setVendasServico(new VendasServico());
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
		tcSelecionar.setCellFactory(param -> new TableCell<Vendas, Vendas>() {

			private final CheckBox chkSelecionar = new CheckBox("");

			@Override
			protected void updateItem(Vendas obj, boolean empty) {

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

	private void checkBoxSelecionado(Vendas obj) {
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
			btExcluir.setVisible(true);
			btCancelarSelecao.setVisible(true);
			tbEditar.setVisible(true);

		}

	}

	private void removeEntity(Vendas obj) {

		Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza em excluir o item?");

		if (result.get() == ButtonType.OK) {

			try {

				setCondicao("tudo");
				servico.excluirVendas(obj);
				updateTableView();

			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
			actionToolBar();
			actionBts();

		}

	}

}
