package itg;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import itg.util.Alertas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.servicos.CadastroCategoriasServico;
import model.servicos.CadastroClientesServico;
import model.servicos.CadastroFornecedoresServico;
import model.servicos.CadastroFuncionariosServico;
import model.servicos.CadastroLanchesServico;
import model.servicos.CadastroProdutosServico;
import model.servicos.UsuariosLoginServico;

public class InicialTelaControladora implements Initializable {

	private static Scene telainicialScene;
	private static Stage telainicialStage;
	private static Stage telaTrocarSenhaStage;
	private static Scene telaTrocarSenhaScene;

	public static Scene getTelaInicialScene() {
		return telainicialScene;
	}

	public static Stage getTelaInicialStage() {
		return telainicialStage;
	}

	public static Stage getTelaTrocarSenhaStage() {
		return telaTrocarSenhaStage;
	}

	public static Scene getTelaTrocarSenhaScene() {
		return telaTrocarSenhaScene;
	}

	@FXML
	private MenuItem miSobre;
	@FXML
	private MenuItem miTrocarSenha;
	@FXML
	private MenuItem miNovoUsuario;
	@FXML
	private MenuItem miCadCategorias;
	@FXML
	private MenuItem miCadClientes;
	@FXML
	private MenuItem miCadFuncionarios;
	@FXML
	private MenuItem miCadFornecedores;
	@FXML
	private MenuItem miCadProdutos;
	@FXML
	private MenuItem miCadLanches;

	@FXML
	private Menu mnInicio;

	@FXML
	public void onMenuInicio() {
		//carregarTelaInicial("/itg/InicialTela.fxml");

	}


	@FXML
	public void onMenuItemSobre() {
		carregarTela("/itg/SobreTela.fxml", x -> {
		});
	}

	@FXML
	public void onMenuItemTrocarSenha() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/itg/TrocarSenhaTela.fxml"));
			AnchorPane anchorPane = loader.load();

			telaTrocarSenhaStage = new Stage();
			telaTrocarSenhaStage.initStyle(StageStyle.DECORATED);
			telaTrocarSenhaStage.setMinHeight(245.0);
			telaTrocarSenhaStage.setMinWidth(451.0);
			telaTrocarSenhaStage.resizableProperty().setValue(Boolean.FALSE);

			telaTrocarSenhaScene = new Scene(anchorPane);
			telaTrocarSenhaStage.setScene(telaTrocarSenhaScene);
			telaTrocarSenhaStage.setTitle("Trocar Senha");
			telaTrocarSenhaStage.show();

		} catch (

		IOException e) {
			Alertas.showAlert("IO exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}
	

	@FXML
	public void onMenuNovoUsuario() {
		carregarTela("/itg/NovoUsuarioTela.fxml", (NovoUsuarioTelaListControladora controller) -> {
			controller.setUsuariosLoginService(new UsuariosLoginServico());
			controller.updateTableView();
		});

	}
	
	@FXML
	public void onMenuLanches() {
		carregarTela("/itg/CadastroLanchesTela.fxml", (CadastroLanchesTelaListControladora controller) -> {
			controller.setCadastroLanchesServico(new CadastroLanchesServico());
			controller.updateTableView();
		});

	}
	
	@FXML
	public void onMenuProdutos() {
		carregarTela("/itg/CadastroProdutosTela.fxml", (CadastroProdutosTelaListControladora controller) -> {
			controller.setCadastroProdutosServico(new CadastroProdutosServico());
			controller.updateTableView();
		});

	}
	
	@FXML
	public void onMenuCategorias() {
		carregarTela("/itg/CadastroCategoriasTela.fxml", (CadastroCategoriasTelaListControladora controller) -> {
			controller.setCadastroCategoriasServico(new CadastroCategoriasServico());
			controller.updateTableView();
		});

	}

	@FXML
	public void onMenuClientes() {
		carregarTela("/itg/CadastroClientesTela.fxml", (CadastroClientesTelaListControladora controller) -> {
			controller.setCadastroClientesServico(new CadastroClientesServico());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuFuncionarios() {
		carregarTela("/itg/CadastroFuncionariosTela.fxml", (CadastroFuncionariosTelaListControladora controller) -> {
			controller.setCadastroFuncionariosServico(new CadastroFuncionariosServico());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuFornecedores() {
		carregarTela("/itg/CadastroFornecedoresTela.fxml", (CadastroFornecedoresTelaListControladora controller) -> {
			controller.setCadastroFornecedoresServico(new CadastroFornecedoresServico());
			controller.updateTableView();
		});
	}

	public synchronized <T> void carregarTela(String absoluteName, Consumer<T> initializingAction) {

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();

			Scene mainScene = LoginTelaControladora.getMenuScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren());

			T controller = loader.getController();
			initializingAction.accept(controller);

		} catch (IOException e) {
			Alertas.showAlert("IO exception", "Erro ao carregar tela", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

}
