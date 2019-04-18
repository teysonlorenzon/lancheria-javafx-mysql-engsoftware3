package itg;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import itg.util.Utilitarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.entidades.Pessoa;
import model.servicos.CadastroClientesServico;

public class CadastroClientesTelaPesquisarFormControladora implements Initializable {

	private CadastroClientesServico servico =  new CadastroClientesServico();

	

	@FXML
	private CheckBox chkId;
	@FXML
	private CheckBox chkNome;
	@FXML
	private CheckBox chkCpf;
	@FXML
	private CheckBox chkCnpj;

	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;
	@FXML
	private TextField txtCpf;
	@FXML
	private TextField txtCnpj;

	@FXML
	private RadioButton rbFisica;
	@FXML
	private RadioButton rbJuridica;

	@FXML
	private Button btConfirmar;
	@FXML
	private Button btCancelar;

	@FXML
	public void onBtConfirmar(ActionEvent event) {
		CadastroClientesTelaListControladora controller = new CadastroClientesTelaListControladora();
		controller.setCondicao("cpf");

		updateList("CadastroClientesTela.fxml", txtCpf.getText(), "cpf");
		Utilitarios.currentStage(event).close();
	}

	@FXML
	public void onBtCancelar(ActionEvent event) {
		Utilitarios.currentStage(event).close();
	}

	@FXML
	public void onChkId() {
		if (chkId.isSelected() == false) {
			txtId.setVisible(false);
			txtId.setDisable(true);
		} else {

			txtId.setVisible(true);
			txtId.setDisable(false);
			txtNome.setVisible(false);
			txtCpf.setVisible(false);
			txtCnpj.setVisible(false);

			chkNome.setSelected(false);
			chkCpf.setSelected(false);
			chkCnpj.setSelected(false);
		}
	}

	@FXML
	public void onChkNome() {

		if (chkNome.isSelected() == false) {
			txtNome.setVisible(false);
			txtNome.setDisable(true);
		} else {

			txtId.setVisible(false);
			txtNome.setVisible(true);
			txtNome.setDisable(false);
			txtCpf.setVisible(false);
			txtCnpj.setVisible(false);

			chkId.setSelected(false);
			chkCnpj.setSelected(false);
			chkCpf.setSelected(false);
		}
	}

	@FXML
	public void onChkCpf() {

		if (chkCpf.isSelected() == false) {
			txtCpf.setVisible(false);
			txtCpf.setDisable(true);
		} else {
			txtId.setVisible(false);
			txtNome.setVisible(false);
			txtCpf.setVisible(true);
			txtCpf.setDisable(false);
			txtCnpj.setVisible(false);

			chkId.setSelected(false);
			chkNome.setSelected(false);
			chkCnpj.setSelected(false);
		}
	}

	@FXML
	public void onChkCnpj() {

		if (chkCnpj.isSelected() == false) {
			txtCnpj.setVisible(false);
			txtCnpj.setDisable(true);
		} else {
			txtId.setVisible(false);
			txtNome.setVisible(false);
			txtCpf.setVisible(false);
			txtCnpj.setVisible(true);
			txtCnpj.setDisable(false);

			chkId.setSelected(false);
			chkNome.setSelected(false);
			chkCpf.setSelected(false);
		}
	}

	@FXML
	public void onRbFisica() {
		rbJuridica.setSelected(false);
		chkCpf.setVisible(true);
		chkCpf.setDisable(false);
		chkCnpj.setVisible(false);
		chkCnpj.setDisable(true);
		chkCnpj.setSelected(false);
		txtCnpj.setVisible(false);
		txtCnpj.setDisable(true);

	}

	@FXML
	public void onRbJuridica() {
		rbFisica.setSelected(false);
		chkCpf.setVisible(false);
		chkCpf.setDisable(true);
		chkCpf.setSelected(false);
		chkCnpj.setVisible(true);
		chkCnpj.setDisable(false);
		txtCpf.setVisible(false);
		txtCpf.setDisable(true);

	}

	private void updateList(String absoluteName, String txt, String tipo) {
		try {

			List<Pessoa> listObj = servico.buscarCPF(txtCpf.getText());

			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			CadastroClientesTelaListControladora controller = loader.getController();
			controller.setCondicao(tipo);
			controller.setList(listObj);
			controller.initializarNodes();
			controller.actionToolBar();
		} catch (IOException e) {

		}

	}

	@Override
	public void initialize(URL rb, ResourceBundle url) {
		txtId.setVisible(false);
		txtNome.setVisible(false);
		txtCpf.setVisible(false);
		txtCnpj.setVisible(false);
		chkCpf.setVisible(false);
		chkCnpj.setVisible(false);

	}

}
