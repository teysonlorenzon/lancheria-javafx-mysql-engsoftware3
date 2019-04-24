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
import javafx.scene.control.RadioButton;
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
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.servicos.CadastroClientesServico;

public class CadastroClientesTelaListControladora implements Initializable, DataChangeListener {

	private CadastroClientesServico servico;
	private ObservableList<Pessoa> obsList;
	private String condicao = "tudo";
	private Pessoa entidade;
	private List<CheckBox> chlist = new ArrayList<>();
	private List<Pessoa> list = new ArrayList<>();

	public List<Pessoa> getList() {
		return list;
	}

	public void setList(List<Pessoa> list) {
		this.list = list;
	}

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	public Pessoa getEntidade() {
		return entidade;
	}

	public void setEntidadeNula(Pessoa entidade) {
		this.entidade = entidade;
	}

	@FXML
	private TableView<Pessoa> tbCadastroClientes;

	@FXML
	private TableColumn<Pessoa, Integer> tcIdPessoa;
	@FXML
	private TableColumn<Pessoa, String> tcNome;
	@FXML
	private TableColumn<Pessoa, String> tcNomeFantasia;
	@FXML
	private TableColumn<Pessoa, String> tcCpf;
	@FXML
	private TableColumn<Pessoa, String> tcCnpj;
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
	private RadioButton rbFisica;
	@FXML
	private RadioButton rbJuridica;
	@FXML
	private RadioButton rbTudo;

	@FXML
	private Button btNovo;

	@FXML
	private MenuButton mbtPesquisar;
	@FXML
	private MenuItem miCpf;
	@FXML
	private MenuItem miCnpj;
	@FXML
	private MenuItem miNome;
	@FXML
	private MenuItem miId;

	@FXML
	private TextField txtPesquisarId;
	@FXML
	private TextField txtPesquisarNome;
	@FXML
	private TextField txtPesquisarCpf;
	@FXML
	private TextField txtPesquisarCnpj;

	@FXML
	private Button btEditar;
	@FXML
	private Button btExcluir;
	@FXML
	private Button btCancelarSelecao;
	@FXML
	private ToolBar tbEditar;

	private void desabilitarRB() {
		rbFisica.setSelected(false);
		rbJuridica.setSelected(false);
		rbTudo.setSelected(false);
	}
	
	@FXML
	public void onBtPesquisarAction() {
		updateTableView();
		initializarNodes();
		actionToolBar();
	}

	@FXML
	public void onBtMenuItemCpf() {
		desabilitarRB();
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCnpj);
		txtPesquisarCpf.setVisible(true);
		txtPesquisarCpf.setText("");
		setCondicao("cpf");
	}

	@FXML
	public void onBtMenuItemCnpj() {
		desabilitarRB();
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		txtPesquisarCnpj.setVisible(true);
		txtPesquisarCnpj.setText("");
		setCondicao("cnpj");
	}

	@FXML
	public void onBtMenuItemNome() {
		desabilitarRB();
		setarInicioTxtPesquisar(txtPesquisarId);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		setarInicioTxtPesquisar(txtPesquisarCnpj);
		txtPesquisarNome.setVisible(true);
		txtPesquisarNome.setText("");
		setCondicao("nome");
	}

	@FXML
	public void onBtMenuItemId() {
		desabilitarRB();
		txtPesquisarId.setEditable(true);
		txtPesquisarId.setDisable(false);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		setarInicioTxtPesquisar(txtPesquisarCnpj);
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
		rbTudo.setSelected(true);
		rbFisica.setSelected(false);
		rbJuridica.setSelected(false);
		onRbTudoAction();
		Stage parentStage = Utilitarios.currentStage(event);
		criarForm("/itg/CadastroClientesFormTela.fxml", parentStage);

	}

	@FXML
	public void onRbFisicaAction() {
		txtPesquisarId.setEditable(false);
		txtPesquisarId.setDisable(true);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		setarInicioTxtPesquisar(txtPesquisarCnpj);
		rbJuridica.setSelected(false);
		rbTudo.setSelected(false);
		setCondicao("fisica");
		updateTableView();
		initializarNodes();
		actionToolBar();
	}

	@FXML
	public void onRbJuridicaAction() {
		txtPesquisarId.setEditable(false);
		txtPesquisarId.setDisable(true);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		setarInicioTxtPesquisar(txtPesquisarCnpj);
		rbFisica.setSelected(false);
		rbTudo.setSelected(false);
		setCondicao("juridica");
		updateTableView();
		initializarNodes();
		actionToolBar();

	}

	@FXML
	public void onRbTudoAction() {
		txtPesquisarId.setEditable(false);
		txtPesquisarId.setDisable(true);
		setarInicioTxtPesquisar(txtPesquisarNome);
		setarInicioTxtPesquisar(txtPesquisarCpf);
		setarInicioTxtPesquisar(txtPesquisarCnpj);
		rbFisica.setSelected(false);
		rbJuridica.setSelected(false);
		setCondicao("tudo");
		updateTableView();
		initializarNodes();
		actionToolBar();
	}
	
	private void setarInicioTxtPesquisar(TextField txt) {
		txt.setVisible(false);
		txt.setText("");
	}

	public void setCadastroClientesServico(CadastroClientesServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {

		if (getCondicao().equals("fisica")) {

			list = servico.buscarClientes('F');
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();

		} else if (getCondicao().equals("juridica")) {
			list = servico.buscarClientes('J');
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();

		} else if (getCondicao().equals("tudo")) {
			list = servico.buscarClientes('T');
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();
		} else if (getCondicao().equals("cpf")) {

			list = servico.buscarCPF(txtPesquisarCpf.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();

		} else if (getCondicao().equals("cnpj")) {
			list = servico.buscarCNPJ(txtPesquisarCnpj.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();
		} else if (getCondicao().equals("nome")) {
			list = servico.buscarListPorNome(txtPesquisarNome.getText());
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();
		} else if (getCondicao().equals("id")) {
			list = servico.buscarListPorId(Integer.parseInt(txtPesquisarId.getText()));
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();
		}

	}

	public void initializarNodes() {

		if (getCondicao().equals("fisica") || getCondicao().equals("cpf")) {
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

			tcNomeFantasia.setVisible(false);
			tcCnpj.setVisible(false);
			tcCpf.setVisible(true);
			tcRg.setVisible(true);
			tcDataNascimento.setVisible(true);

		} else if (getCondicao().equals("juridica") || getCondicao().equals("cnpj")) {

			tcIdPessoa.setCellValueFactory(new PropertyValueFactory<>("idPessoa"));
			tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			tcNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
			tcCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
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

			tcCpf.setVisible(false);
			tcRg.setVisible(false);
			tcDataNascimento.setVisible(false);
			tcNomeFantasia.setVisible(true);
			tcCnpj.setVisible(true);

		} else if (getCondicao().equals("tudo") || getCondicao().equals("nome") || getCondicao().equals("id")) {

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

			tcNomeFantasia.setCellValueFactory(new PropertyValueFactory<>("nomeFantasia"));
			tcCnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));

			tcCpf.setVisible(true);
			tcRg.setVisible(true);
			tcDataNascimento.setVisible(true);
			tcNomeFantasia.setVisible(true);
			tcCnpj.setVisible(true);
			tcNomeFantasia.setVisible(true);
			tcCnpj.setVisible(true);
			tcCpf.setVisible(true);
			tcRg.setVisible(true);
			tcDataNascimento.setVisible(true);

		}

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroClientes.prefHeightProperty().bind(stage.heightProperty());

	}

	private void tecladoEnter(TextField txt) {
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
		Mascaras.cnpjField(txtPesquisarCnpj);
		Mascaras.numericField(txtPesquisarId);

		txtPesquisarId.setEditable(false);
		txtPesquisarId.setDisable(true);
		txtPesquisarNome.setVisible(false);
		txtPesquisarCpf.setVisible(false);
		txtPesquisarCnpj.setVisible(false);
		tecladoEnter(txtPesquisarId);
		tecladoEnter(txtPesquisarNome);
		tecladoEnter(txtPesquisarCpf);
		tecladoEnter(txtPesquisarCnpj);

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
		Fisica obj = new Fisica();

		if (obj.getClass().equals(entidade.getClass())) {
			setCondicao("fisica");
			criarFormFisicaJuridica(entidade, "/itg/CadastroClientesFormTela.fxml", parentStage, getCondicao());
		} else {
			setCondicao("juridica");
			criarFormFisicaJuridica(entidade, "/itg/CadastroClientesFormTela.fxml", parentStage, getCondicao());
		}

	}

	@FXML
	public void onBtExcluirAction() {

		if (entidade == null) {
			Alertas.showAlert("Atenção", null, "Selecione um cliente", AlertType.WARNING);
		} else {

			removeEntity(entidade);
		}
	}

	private void criarFormFisicaJuridica(Pessoa obj, String absoluteName, Stage parentStage, String tipo) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroClientesTelaFormControladora controller = loader.getController();

			controller.subscribeDataChangeListener(this);

			controller.setPessoa(obj);
			controller.setTipo(tipo);

			if (getCondicao().equals("fisica")) {
				controller.setPessoaFisica((Fisica) servico.buscarId(entidade.getIdPessoa()));
				controller.onRbFisicaAction();
			} else {
				controller.onRbJuridicaAction();
				controller.setPessoaJuridica((Juridica) servico.buscarId(entidade.getIdPessoa()));
			}
			controller.updateFormDataFisicaJuridica();

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

			CadastroClientesTelaFormControladora controller = loader.getController();
			controller.setCadastroClientesServico(new CadastroClientesServico());
			controller.subscribeDataChangeListener(this);
			controller.onRbFisicaAction();

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

				Fisica obj2 = new Fisica();

				if (obj2.getClass().equals(entidade.getClass())) {

					setCondicao("juridica");
				} else {
					setCondicao("fisica");
				}

				if (getCondicao().equals("fisica")) {
					setCondicao("tudo");
					servico.excluirPessoa(obj);
					updateTableView();
				} else {
					setCondicao("tudo");
					servico.excluirPessoa(obj);
					updateTableView();
				}

			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
			actionToolBar();
			actionBts();

		}

	}

}
