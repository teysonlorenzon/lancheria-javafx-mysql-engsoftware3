package itg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import itg.listeners.DataChangeListener;
import itg.util.Alertas;
import itg.util.Mascaras;
import itg.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Categorias;
import model.exception.ValidationException;
import model.servicos.CadastroCategoriasServico;

public class CadastroCategoriasTelaFormControladora implements Initializable {

	private Categorias entidade;

	private CadastroCategoriasServico servico = new CadastroCategoriasServico();
	private String tipo;

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdCategorias;
	@FXML
	private TextField txtNome;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private Label lbErrorNome;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {
			entidade = getFormDataCategorias();
			servico.iniciarUpdateOuIserirCategorias(entidade);
			notifyDataChangeListeners();
			Utilitarios.currentStage(event).close();
			Alertas.showAlert("Informação", null, "Operação realizada com sucesso", AlertType.INFORMATION);

		} catch (DbException e) {
			Alertas.showAlert("Error Save Object", null, e.getMessage(), AlertType.ERROR);
		} catch (ValidationException e) {
			setErrorMessage(e.getErrors());
		}
	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}

	}

	private Categorias getFormDataCategorias() {
		Categorias obj = new Categorias();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdCategorias.getText() == null || txtIdCategorias.getText().trim().equals("")) {
		} else {
			obj.setIdCategorias(Integer.parseInt(txtIdCategorias.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo obrigatório");
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		obj.setNomeCategorias(txtNome.getText());

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setCategorias(Categorias entidade) {
		this.entidade = entidade;
	}

	public void setCadastroCategoriasServico(CadastroCategoriasServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {

		Mascaras.maxField(txtNome, 40);
	}

	public void updateFormDataCategorias() {

		txtIdCategorias.setText(String.valueOf(entidade.getIdCategorias()));
		txtNome.setText(entidade.getNomeCategorias());

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErrorNome.setText(errors.get("nome"));

		}

	}

}
