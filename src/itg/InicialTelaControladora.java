package itg;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import db.DB;
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
import model.entidades.Produtos;
import model.servicos.CadastroCategoriasServico;
import model.servicos.CadastroClientesServico;
import model.servicos.CadastroEntradaServico;
import model.servicos.CadastroFornecedoresServico;
import model.servicos.CadastroFuncionariosServico;
import model.servicos.CadastroLanchesServico;
import model.servicos.CadastroProdutosServico;
import model.servicos.EstoqueServico;
import model.servicos.Relatorio;
import model.servicos.UsuariosLoginServico;
import model.servicos.VendasServico;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

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
	private MenuItem miCadEntrada;
	@FXML
	private MenuItem miEstoque;
	@FXML
	private MenuItem miLanches;
	@FXML
	private MenuItem miInicioTela;

	@FXML
	private MenuItem miRelatorioProdutos;
	@FXML
	private MenuItem miRelatorioClientes;
	@FXML
	private MenuItem miRelatorioLanches;
	@FXML
	private MenuItem miRelatorioFuncionarios;
	@FXML
	private MenuItem miRelatorioVendas;
	@FXML
	private MenuItem miRelatorioFornecedores;
	@FXML
	private MenuItem miRelatorioEstoque;

	@FXML
	private Menu mnInicio;
	@FXML
	private Menu mnAdministrador;
	
	
	

	
	@FXML
	public void onMenuRelatorioProdutos() {
		Relatorio.chamarRelatorios("C:/Users/Teyson/JaspersoftWorkspace/MyReports/CadProdutos.jrxml");
	}

	@FXML
	public void onMenuInicioTela() {

		carregarTela("/itg/Branco.fxml", x -> {
		});
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
	public void onMenuVendasLanches() {
		carregarTela("/itg/VendasTela.fxml", (VendasTelaListControladora controller) -> {
			controller.setVendasServico(new VendasServico());
			controller.updateTableView();
		});

	}

	@FXML
	public void onMenuEstoque() {
		carregarTela("/itg/EstoqueTela.fxml", (EstoqueTelaListControladora controller) -> {
			controller.setEstoqueServico(new EstoqueServico());
			controller.updateTableView();
		});

	}

	@FXML
	public void onMenuEntrada() {
		carregarTela("/itg/CadastroEntradaTela.fxml", (CadastroEntradaTelaListControladora controller) -> {
			controller.setCadastroEntradaServico(new CadastroEntradaServico());
			controller.updateTableView();
		});

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
		if(LoginTelaControladora.getGuardaNivel().equals("Usuario")) {
			miCadFuncionarios.setVisible(false);
			miCadProdutos.setVisible(false);
			miCadCategorias.setVisible(false);
			mnAdministrador.setVisible(false);			
		}
	}

}
