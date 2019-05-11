package itg;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.mysql.jdbc.StringUtils;

import db.DbException;
import itg.listeners.DataChangeListener;
import itg.util.Alertas;
import itg.util.Mascaras;
import itg.util.Restricao;
import itg.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.entidades.Categorias;
import model.entidades.Lanches;
import model.entidades.Produtos;
import model.exception.ValidationException;
import model.servicos.CadastroCategoriasServico;
import model.servicos.CadastroLanchesServico;
import model.servicos.CadastroProdutosServico;

public class CadastroLanchesTelaFormControladora implements Initializable {

	private Lanches entidade;
	private List<Produtos> listProd = new ArrayList<>();
	private Categorias listCat;
	private URL url;

	private CadastroLanchesServico servico = new CadastroLanchesServico();
	private CadastroProdutosServico servicoProd = new CadastroProdutosServico();
	private CadastroCategoriasServico servicoCat = new CadastroCategoriasServico();
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtIdLanches;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtDescricao;
	@FXML
	private TextField txtValor;
	@FXML
	private ImageView imgLanche;

	@FXML
	private ComboBox cbAddProdutos;
	@FXML
	private ComboBox cbRmProdutos;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;
	@FXML
	private Button btAdcionarImagem;

	@FXML
	private Label lbErrorNome;
	@FXML
	private Label lbErrorDescricao;
	@FXML
	private Label lbErrorValor;

	@FXML
	public void onBtAdcionarImagem() {

		url = Utilitarios.buscarImagem(imgLanche);
	}

	@FXML
	public void onCbAddProdutos() {
		
		if (!StringUtils.isNullOrEmpty(txtDescricao.getText())) {
			txtDescricao.setText(txtDescricao.getText() + ",");
		}
		txtDescricao.setText(txtDescricao.getText() + (String) cbAddProdutos.valueProperty().get());
		
		
		

	}

	@FXML
	public void onCbRmProdutos() {
		String seraRemovido = String.valueOf(cbRmProdutos.valueProperty().get());
		String naoRemove = txtDescricao.getText();
		if (naoRemove.equals(seraRemovido)) {
			txtDescricao.setText(naoRemove.replaceAll(seraRemovido, ""));
		} else {

			seraRemovido = "," + seraRemovido;
			txtDescricao.setText(naoRemove.replaceAll(seraRemovido, ""));
		}
	}

	@FXML
	public void onBtConfirmarAction(ActionEvent event) {
		if (servico == null) {
			throw new IllegalStateException("Serviço está nullo");
		}

		try {

			entidade = getFormDataLanches();
			servico.iniciarUpdateOuIserirLanches(entidade);
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

	private Lanches getFormDataLanches() {
		Lanches obj = new Lanches();
		Categorias obj3 = new Categorias();
		ValidationException exception = new ValidationException("Erro de Validação");

		if (txtIdLanches.getText() == null || txtIdLanches.getText().trim().equals("")) {
		} else {
			obj.setIdLanches(Integer.parseInt(txtIdLanches.getText()));
		}

		if (txtNome.getText() == null || txtNome.getText().trim().equals("")) {
			exception.addError("nome", "campo obrigatório");
		}
		if (txtDescricao.getText() == null || txtDescricao.getText().trim().equals("")) {
			exception.addError("descricao", "campo obrigatório");
		}
		if (txtValor.getText() == null || txtValor.getText().trim().equals("")) {
			exception.addError("valor", "campo obrigatório");
		}

		if (exception.getErrors().size() > 0) {
			throw exception;
		}
		if (url == null || url.equals("")) {
			obj.setLinkImgLanche(imgLanche.getImage().getUrl());
		} else {
			obj.setLinkImgLanche(String.valueOf(url));
		}

		obj.setNomeLanches(txtNome.getText());
		obj.setDescricao(txtDescricao.getText());
		obj.setValorLanche(Double.parseDouble(txtValor.getText()));

		return obj;

	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utilitarios.currentStage(event).close();

	}

	public void setLanches(Lanches entidade) {
		this.entidade = entidade;
	}

	public void setCadastroLanchesServico(CadastroLanchesServico servico) {
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

		Mascaras.numericField(txtIdLanches);
		Mascaras.maxField(txtNome, 40);
		Restricao.setTextFieldDouble(txtValor);

	}

	public void updateFormDataLanches() {

		txtIdLanches.setText(String.valueOf(entidade.getIdLanches()));
		txtNome.setText(entidade.getNomeLanches());
		txtValor.setText(String.valueOf(entidade.getValorLanche()));
		txtDescricao.setText(entidade.getDescricao());
		imgLanche.setImage(new Image(entidade.getLinkImgLanche()));

	}

	private void setErrorMessage(Map<String, String> errors) {
		Set<String> fields = errors.keySet();
		if (fields.contains("nome")) {
			lbErrorNome.setText(errors.get("nome"));

		}

		if (fields.contains("descricao")) {
			lbErrorDescricao.setText(errors.get("descricao"));

		}
		if (fields.contains("valor")) {
			lbErrorValor.setText(errors.get("valor"));

		}

	}

	public void criarListaComboBox() {

		listCat = servicoCat.buscarNome("Ingredientes");
		listProd = servicoProd.buscarListProdutosPorCategorias(listCat.getIdCategorias());

		for (Produtos listProdutos : listProd) {
			cbAddProdutos.getItems().add(listProdutos.getNomeProdutos());
			cbRmProdutos.getItems().add(listProdutos.getNomeProdutos());
		}

	}

}
