package itg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import itg.listeners.DataChangeListener;
import itg.util.Mascaras;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import model.entidades.Estoque;
import model.servicos.CadastroEntradaServico;
import model.servicos.EstoqueServico;

public class EstoqueTelaListControladora implements Initializable, DataChangeListener {

	private EstoqueServico servico;
	private ObservableList<Estoque> obsList;
	private String condicao = "tudo";
	private List<Estoque> list = new ArrayList<>();

	public void setCondicao(String condicao) {
		this.condicao = condicao;
	}

	public String getCondicao() {
		return condicao;
	}

	@FXML
	private TableView<Estoque> tbEstoque;

	@FXML
	private TableColumn<Estoque, String> tcProduto;
	@FXML
	private TableColumn<Estoque, String> tcQuantidade;


	@FXML
	private TextField txtPesquisarProduto;

	
	@FXML
	private MenuButton mbPesquisar;
	@FXML
	private MenuItem miProduto;
	@FXML
	private MenuItem miTudo;

	@FXML
	public void onBtPesquisarAction() {
		updateTableView();
		initializarNodes();
	}

	@FXML
	public void onBtMenuItemTudo() {
		mbPesquisar.setText("Pesquisar");
		setarInicioTxtPesquisar(txtPesquisarProduto);
		setCondicao("tudo");
		onBtPesquisarAction();

	}

	@FXML
	public void onBtMenuItemProduto() {
		mbPesquisar.setText("Produto");
		txtPesquisarProduto.setVisible(true);
		txtPesquisarProduto.setText("");
		setCondicao("nome");
	}



	private void setarInicioTxtPesquisar(TextField txt) {
		txt.setVisible(false);
		txt.setText("");
	}

	public void setEstoqueServico(EstoqueServico servico) {
		this.servico = servico;
	}

	public void updateTableView() {

		if (getCondicao().equals("tudo")) {
			int cont = 0;

			list = servico.buscarEstoque();
			obsList = FXCollections.observableArrayList(list);
			tbEstoque.setItems(obsList);
		}

		else if (getCondicao().equals("nome")) {
			list = servico.buscarListPorProduto(txtPesquisarProduto.getText());
			obsList = FXCollections.observableArrayList(list);
			tbEstoque.setItems(obsList);
		}

	}

	public void initializarNodes() {

		tcProduto.setCellValueFactory(new PropertyValueFactory<>("nomeProdutos"));
		tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbEstoque.prefHeightProperty().bind(stage.heightProperty());

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

		Mascaras.maxField(txtPesquisarProduto, 60);
		tecladoEnter(txtPesquisarProduto);

		initializarNodes();
	
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	
}
