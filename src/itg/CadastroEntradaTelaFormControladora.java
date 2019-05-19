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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entidades.Categorias;
import model.entidades.Entrada;
import model.exception.ValidationException;
import model.servicos.CadastroCategoriasServico;
import model.servicos.CadastroEntradaServico;

public class CadastroEntradaTelaFormControladora implements Initializable {

	private Entrada entidade;
	private List<Categorias> listCat = new ArrayList<>();

	private CadastroEntradaServico servico = new CadastroEntradaServico();
	private CadastroCategoriasServico servicoCat = new CadastroCategoriasServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdEntrada;
	@FXML
	private TextField txtNome;
	@FXML
	private ComboBox cbCategoria;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	private Label lbErrorNome;
	@FXML
	private Label lbErrorFornecedor;

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidade = getFormDataEntrada();
			servico.iniciarUpdateOuIserirEntrada(entidade);
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

	private Entrada getFormDataEntrada() {
		Entrada obj = new Entrada();
		Categorias obj3 = new Categorias();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdEntrada.getText() == null || txtIdEntrada.getText().trim().equals("")) {
		} else {
			obj.setIdEntrada(Integer.parseInt(txtIdEntrada.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo obrigatório");
		}

		if (cbCategoria.valueProperty().get() == null || cbCategoria.valueProperty().get().equals("")) {
			exception.addError("categorias", "campo obrigatório");
		}

		obj3 = servicoCat.buscarNome((String) cbCategoria.valueProperty().get());

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		//obj.setNomeEntrada(txtNome.getText());
		//obj.setIdCategorias(obj3.getIdCategorias());

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setEntrada(Entrada entidade) {
		this.entidade = entidade;
	}

	public void setCadastroEntradaServico(CadastroEntradaServico servico) {
		this.servico = servico;
	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
		criarListaComboBox();

	}

	private void initializeNodes() {

		Mascaras.numericField(txtIdEntrada);
		Mascaras.maxField(txtNome, 40);

	}

	public void updateFormDataEntrada() {

		txtIdEntrada.setText(String.valueOf(entidade.getIdEntrada()));
	//	txtNome.setText(entidade.getNomeEntrada());
		//cbCategoria.valueProperty().set(entidade.getNomeCategorias());

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErrorNome.setText(errors.get("nome"));

		}

		if (fields.contains("fornecedores")) {
			lbErrorFornecedor.setText(errors.get("fornecedores"));

		}

	}

	public void criarListaComboBox() {

		listCat = servicoCat.buscarCategorias();

		for (Categorias listCate : listCat) {
			cbCategoria.getItems().add(listCate.getNomeCategorias());
		}

	}

}
