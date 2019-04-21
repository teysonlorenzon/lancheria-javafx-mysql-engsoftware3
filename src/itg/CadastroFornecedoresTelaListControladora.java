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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.servicos.CadastroFornecedoresServico;

public class CadastroFornecedoresTelaListControladora implements Initializable, DataChangeListener {

	private CadastroFornecedoresServico servico;
	private ObservableList<Pessoa> obsList;
	private Pessoa entidade;
	private List<CheckBox> chlist = new ArrayList<>();
	private List<Pessoa> list = new ArrayList<>();

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
	private TableView<Pessoa> tbCadastroFornecedores;

	@FXML
	private TableColumn<Pessoa, Integer> tcIdPessoa;
	@FXML
	private TableColumn<Pessoa, String> tcNome;
	@FXML
	private TableColumn<Pessoa, String> tcNomeFantasia;
	@FXML
	private TableColumn<Pessoa, String> tcCnpj;
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
	private Button btPesquisar;
	@FXML
	private Button btEditar;
	@FXML
	private Button btExcluir;
	@FXML
	private Button btCancelarSelecao;
	@FXML
	private ToolBar tbEditar;

	@FXML
	public void onBtPesquisarAction(ActionEvent event) {

		Stage parentStage = Utilitarios.currentStage(event);
		criarFormPesquisar("/itg/PesquisarFormClientesTela.fxml", parentStage);

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
		criarForm("/itg/CadastroFornecedoresFormTela.fxml", parentStage);

	}

	public void setCadastroFornecedoresServico(CadastroFornecedoresServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {

		list = servico.buscarFornecedores();
		obsList = FXCollections.observableArrayList(list);
		tbCadastroFornecedores.setItems(obsList);
		initSelecionarCheckBox();
	}

	public void initializarNodes() {

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

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroFornecedores.prefHeightProperty().bind(stage.heightProperty());

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

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
			Alertas.showAlert("Atenção", null, "Selecione um fornecedor", AlertType.WARNING);
		}
		criarFormJuridica(entidade, "/itg/CadastroFornecedoresFormTela.fxml", parentStage);

	}

	@FXML
	public void onBtExcluirAction() {

		if (entidade == null) {
			Alertas.showAlert("Atenção", null, "Selecione um fornecedor", AlertType.WARNING);
		} else {

			removeEntity(entidade);
		}
	}

	private void criarFormJuridica(Pessoa obj, String absoluteName, Stage parentStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroFornecedoresTelaFormControladora controller = loader.getController();

			controller.subscribeDataChangeListener(this);
			controller.setPessoa(obj);
			controller.setPessoaJuridica((Juridica) servico.buscarId(entidade.getIdPessoa()));
			controller.updateFormDataJuridica();
			
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

			CadastroFornecedoresTelaFormControladora controller = loader.getController();
			controller.setCadastroFornecedoresServico(new CadastroFornecedoresServico());
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

	private void criarFormPesquisar(String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroClientesTelaPesquisarFormControladora controller = loader.getController();
			// controller.setCadastroClientesServico(new CadastroClientesServico());
			// controller.subscribeDataChangeListener(this);
			// controller.onRbFisicaAction();

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
