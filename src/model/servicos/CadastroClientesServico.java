package model.servicos;

import java.util.List;

import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.mysql.CadastroClientesMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroClientesServico {
	private CadastroClientesMYSQL contrato = ConexaoTabelas.criarCadastroClientesMYSQL();

	public void iniciarUpdateOuIserirPessoaFisica(Fisica obj, Juridica obj2) {
		if (obj.getIdPessoa() == null) {

			contrato.inserirPessoaFisicaJuridica(obj, obj2);
		} else {
			contrato.atualizar(obj);
		}
	}

	public List<Pessoa> buscaClientesFisico() {
		return contrato.acharTudoFisica();

	}
	public List<Pessoa> buscaClientesJuridico() {
		return contrato.acharTudoJuridica();

	}

}
