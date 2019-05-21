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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entidades.UsuariosLogin;
import model.servicos.UsuariosLoginServico;

public class NovoUsuarioTelaListControladora implements Initializable, DataChangeListener {

	private UsuariosLoginServico servico;

	@FXML
	private TableView<UsuariosLogin> tbNovoUsuario;

	@FXML
	private TableColumn<UsuariosLogin, Integer> tcId;
	@FXML
	private TableColumn<UsuariosLogin, String> tcUsuario;
	@FXML
	private TableColumn<UsuariosLogin, String> tcSenha;
	@FXML
	private TableColumn<UsuariosLogin, String> tcNivel;
	@FXML
	private TableColumn<UsuariosLogin, UsuariosLogin> tcEditar;
	@FXML
	private TableColumn<UsuariosLogin, UsuariosLogin> tcExcluir;
	@FXML
	private TableColumn<UsuariosLogin, String> tcFuncionarios;
	@FXML
	private Button btNovo;

	private ObservableList<UsuariosLogin> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {

		Stage parentStage = Utilitarios.currentStage(event);
		UsuariosLogin obj = new UsuariosLogin();
		criarForm(obj, "/itg/NovoUsuarioFormTela.fxml", parentStage);
	}

	public void setUsuariosLoginService(UsuariosLoginServico servico) {
		this.servico = servico;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializarNodes();

	}

	private void initializarNodes() {
		tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tcUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
		tcSenha.setCellValueFactory(new PropertyValueFactory<>("senha"));
		tcNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
		tcFuncionarios.setCellValueFactory(new PropertyValueFactory<>("nome"));

		Stage stage = (Stage) LoginTelaControladora.getMenuScene().getWindow();
		tbNovoUsuario.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (servico == null) {
			throw new IllegalStateException("Servico está nulo");
		}

		List<UsuariosLogin> list = servico.buscarTudo();
		obsList = FXCollections.observableArrayList(list);
		tbNovoUsuario.setItems(obsList);
		
		initEditButtons();
		initRemoveButtons();
	}

	private void criarForm(UsuariosLogin obj, String absoluteName, Stage parentStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			NovoUsuarioTelaFormControladora controller = loader.getController();
			controller.setUsuariosLogin(obj);
			controller.setUsuariosLoginServico(new UsuariosLoginServico());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

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

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tcEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcEditar.setCellFactory(param -> new TableCell<UsuariosLogin, UsuariosLogin>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(UsuariosLogin obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> criarForm(obj, "/itg/NovoUsuarioFormTela.fxml", Utilitarios.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tcExcluir.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tcExcluir.setCellFactory(param -> new TableCell<UsuariosLogin, UsuariosLogin>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(UsuariosLogin obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(UsuariosLogin obj) {

		Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza em excluir o item?");

		if (result.get() == ButtonType.OK) {

			if (servico == null) {
				throw new IllegalStateException("Servico está nulo");
			}
			try {
				servico.excluirUsuario(obj);
				updateTableView();
			} catch (DbIntegrityException e) {
				Alertas.showAlert("Erro ao remover objeto", null, e.getMessage(), AlertType.ERROR);
			}
		}

	}

}