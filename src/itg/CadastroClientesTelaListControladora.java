package itg;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entidades.Pessoa;
import model.servicos.CadastroClientesServico;

public class CadastroClientesTelaListControladora implements Initializable {

	private CadastroClientesServico servico;
	private ObservableList<Pessoa> obsList;
	private String condicao = "fisica";

	public String setCondicao(String condicao) {
		return this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
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
	private RadioButton rbFisica;
	@FXML
	private RadioButton rbJuridica;

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
		} else {
			List<Pessoa> list = servico.buscaClientesJuridico();
			obsList = FXCollections.observableArrayList(list);
			tbCadastroClientes.setItems(obsList);
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

	}

}
