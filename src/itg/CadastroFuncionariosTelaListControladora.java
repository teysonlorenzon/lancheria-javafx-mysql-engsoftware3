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
import model.entidades.Fisica;
import model.entidades.Pessoa;
import model.servicos.CadastroFuncionariosServico;

public class CadastroFuncionariosTelaListControladora implements Initializable, DataChangeListener {

	private CadastroFuncionariosServico servico;
	private ObservableList<Pessoa> obsList;
	private Pessoa entidade;
	private List<CheckBox> chlist = new ArrayList<>();
	private List<Pessoa> list = new ArrayList<>();
	private String condicao = "tudo";

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	public List<Pessoa> getList() {
		return list;
	}

	public void setList(List<Pessoa> list) {
		this.list = list;
	}

	public Pessoa getEntidade() {
		return entidade;
	}

	public void setEntidadeNula(Pessoa entidade) {
		this.entidade = entidade;
	}

	@FXML
	private TableView<Pessoa> tbCadastroFuncionarios;

	@FXML
	private TableColumn<Pessoa, Integer> tcIdPessoa;
	@FXML
	private TableColumn<Pessoa, String> tcNome;
	@FXML
	private TableColumn<Pessoa, String> tcCpf;
	@FXML
	private TableColumn<Pessoa, String> tcRg;
	@FXML
	private TableColumn<Pessoa, String> tcDataNascimento;
	@FXML
	private TableColumn<Pessoa, String> tcCidade;
	@FXML
	private TableColumn<Pessoa, String> tcBairro;
	@FXML
	private TableColumn<Pessoa, String> tcEndereco;
	@FXML
	private TableColumn<Pessoa, String> tcComplemento;
	@FXML
	private TableColumn<Pessoa, Integer> tcNumero;
	@FXML
	private TableColumn<Pessoa, String> tcCep;
	@FXML
	private TableColumn<Pessoa, String> tcUf;
	@FXML
	private TableColumn<Pessoa, String> tcTelefoneFixo;
	@FXML
	private TableColumn<Pessoa, String> tcTelefoneCelular;
	@FXML
	private TableColumn<Pessoa, String> tcEmail;
	@FXML
	private TableColumn<Pessoa, Pessoa> tcSelecionar;

	@FXML
	private Button btNovo;

	@FXML
	private MenuItem miCpf;
	@FXML
	private MenuItem miNome;
	@FXML
	private MenuItem miId;
	@FXML
	private MenuItem miTudo;

	@FXML
	private TextField txtPesquisarId;
	@FXML
	private TextField txtPesquisarNome;
	@FXML
	private TextField txtPesquisarCpf;

	@FXML
	private Button btEditar;
	@FXML
	private Button btExcluir;
	@FXML
	private Button btCancelarSelecao;
	@FXML
	private ToolBar tbEditar;

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
		setarInicioTxtPesquisar(txtPesquisarCpf);
		setCondicao("tudo");
		onBtPesquisarAction();
	}

	@FXML
	public void onBtMenuItemCpf() {
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarNome);
		txtPesquisarCpf.setVisible(true);
		setCondicao("cpf");
	}

	@FXML
	public void onBtMenuItemNome() {
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		txtPesquisarNome.setVisible(true);
		setCondicao("nome");
	}

	@FXML
	public void onBtMenuItemId() {
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		txtPesquisarId.setVisible(true);
		setCondicao("id");
	}

	private void setarInicioTxtPesquisar(TextField txt) {
		txt.setVisible(false);
		txt.setText("");
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
		criarForm("/itg/CadastroFuncionariosFormTela.fxml", parentStage);

	}

	public void setCadastroFuncionariosServico(CadastroFuncionariosServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {

		if (getCondicao().equals("tudo")) {
			list = servico.buscarFuncionarios();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroFuncionarios.setItems(obsList);
			initSelecionarCheckBox();
		}
		if (getCondicao().equals("id")) {
			list = servico.buscarListPorId(Integer.parseInt(txtPesquisarId.getText()));
			obsList = FXCollections.observableArrayList(list);
			tbCadastroFuncionarios.setItems(obsList);
			initSelecionarCheckBox();
		}
		if (getCondicao().equals("nome")) {
			list = servico.buscarListPorNome(txtPesquisarNome.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroFuncionarios.setItems(obsList);
			initSelecionarCheckBox();
		}
		if (getCondicao().equals("cnpj")) {
			list = servico.buscarCPF(txtPesquisarCpf.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroFuncionarios.setItems(obsList);
			initSelecionarCheckBox();
		}

	}

	public void initializarNodes() {

		tcIdPessoa.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
		tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tcCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		tcRg.setCellValueFactory(new PropertyValueFactory<>("rg"));
		tcDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
		tcCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
		tcBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
		tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
		tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		tcCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
		tcUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
		tcTelefoneFixo.setCellValueFactory(new PropertyValueFactory<>("telefoneFixo"));
		tcTelefoneCelular.setCellValueFactory(new PropertyValueFactory<>("telefoneCelular"));
		tcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tcComplemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroFuncionarios.prefHeightProperty().bind(stage.heightProperty());

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
		Mascaras.cpfField(txtPesquisarCpf);
		Mascaras.numericField(txtPesquisarId);

		tecladoEnter(txtPesquisarId);
		tecladoEnter(txtPesquisarNome);
		tecladoEnter(txtPesquisarCpf);

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
			Alertas.showAlert("Atenção", null, "Selecione um funcionario", AlertType.WARNING);
		}

		criarFormPessoa(entidade, "/itg/CadastroFuncionariosFormTela.fxml", parentStage);

	}

	@FXML
	public void onBtExcluirAction() {

		if (entidade == null) {
			Alertas.showAlert("Atenção", null, "Selecione um funcionario", AlertType.WARNING);
		} else {

			removeEntity(entidade);
		}
	}

	private void criarFormPessoa(Pessoa obj, String absoluteName, Stage parentStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroFuncionariosTelaFormControladora controller = loader.getController();

			controller.subscribeDataChangeListener(this);

			controller.setPessoa(obj);
			controller.setPessoaFisica((Fisica) servico.buscarId(entidade.getIdPessoa()));
			controller.updateFormDataFisica();

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

			CadastroFuncionariosTelaFormControladora controller = loader.getController();
			controller.setCadastroFuncionariosServico(new CadastroFuncionariosServico());
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
		tcSelecionar.setCellFactory(param -> new TableCell<Pessoa, Pessoa>() {

			private final CheckBox chkSelecionar = new CheckBox("");

			@Override
			protected void updateItem(Pessoa obj, boolean empty) {

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

	private void checkBoxSelecionado(Pessoa obj) {
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

	private void removeEntity(Pessoa obj) {

		Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza em excluir o item?");

		if (result.get() == ButtonType.OK) {

			if (servico == null) {
				throw new IllegalStateException("Servico está nulo");
			}
			try {

				servico.excluirPessoa(obj);
				updateTableView();

			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
			actionToolBar();
			actionBts();

		}

	}

}
