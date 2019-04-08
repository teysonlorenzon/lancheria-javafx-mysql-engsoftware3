package itg;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.Pessoa;
import model.servicos.CadastroClientesServico;

public class CadastroClientesTelaListControladora implements Initializable, DataChangeListener {

	private CadastroClientesServico servico;
	private ObservableList<Pessoa> obsList;
	private String condicao = "fisica";
	private Pessoa entidade;

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
	private TableColumn<Pessoa, Boolean> tcSelecionar2;

	@FXML
	private RadioButton rbFisica;
	@FXML
	private RadioButton rbJuridica;

	@FXML
	private Button btNovo;
	@FXML
	private Button btEditar;
	@FXML
	private Button btExcluir;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parentStage = Utilitarios.currentStage(event);
		criarForm("/itg/CadastroClientesFormTela.fxml", parentStage);

	}

	@FXML
	public void onRbFisicaAction() {
		rbJuridica.setSelected(false);
		setCondicao("fisica");
		updateTableView();
		initializarNodes();
	}

	@FXML
	public void onRbJuridicaAction() {
		rbFisica.setSelected(false);
		setCondicao("juridica");
		updateTableView();
		initializarNodes();

	}

	public void setCadastroClientesServico(CadastroClientesServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("Servico está nulo");
		}
		if (getCondicao().equals("fisica")) {
			List<Pessoa> list = servico.buscaClientesFisico();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();

		} else {
			List<Pessoa> list = servico.buscaClientesJuridico();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
			initSelecionarCheckBox();

		}
	}

	private void initializarNodes() {

		if (getCondicao().equals("fisica")) {
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

		} else {

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
		}

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbCadastroClientes.prefHeightProperty().bind(stage.heightProperty());

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializarNodes();
		Pessoa pega = tbCadastroClientes.getSelectionModel().getSelectedItem();
		entidade = pega;

	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	@FXML
	public void onBtEditarAction(ActionEvent event) {
		Stage parentStage = Utilitarios.currentStage(event);
		if (entidade == null || entidade.equals("")) {
			Alertas.showAlert("Atenção", null, "Selecione um cliente", AlertType.WARNING);
		} else {

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
			controller.setCadastroClientesServico(new CadastroClientesServico());
			controller.subscribeDataChangeListener(this);
			controller.setPessoa(obj);
			controller.setTipo(tipo);
			controller.updateFormDataFisicaJuridica();

			if (getCondicao().equals("fisica")) {
				controller.onRbFisicaAction();
			} else {
				controller.onRbJuridicaAction();
			}

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
		
		tbCadastroClientes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tcSelecionar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));		
		tcSelecionar.setCellFactory(param -> new TableCell<Pessoa, Pessoa>()
		{
			
			private final CheckBox chkSelecionar = new CheckBox("");

			@Override
			protected void updateItem(Pessoa obj, boolean empty) {
							
					
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(chkSelecionar);
				
				chkSelecionar.setOnAction(event -> entidade = obj);
				
				

			}

		});

	}
	
	

	
	private void removeEntity(Pessoa obj) {

		Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza em excluir o item?");

		if (result.get() == ButtonType.OK) {

			if (servico == null) {
				throw new IllegalStateException("Servico está nulo");
			}
			try {
				if (getCondicao().equals("fisica")) {
					servico.excluirPessoaFisica(obj);
					updateTableView();
				} else {
					servico.excluirPessoaJuridica(obj);
					updateTableView();
				}
			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}

	}

}
