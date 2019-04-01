package model.servicos;

import java.util.List;

import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.entidades.UsuariosLogin;
import model.mysql.CadastroClientesMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroClientesServico {
	private CadastroClientesMYSQL contrato = ConexaoTabelas.criarCadastroClientesMYSQL();

	public void iniciarUpdateOuIserirPessoaFisica(Fisica obj) {
		if (obj.getIdPessoa() == null) {

			contrato.inserirPessoaFisica(obj);
		} else {
			contrato.atualizarPessoaFisica(obj);
		}
	}

	public void iniciarUpdateOuIserirPessoaJuridica(Juridica obj) {
		if (obj.getIdPessoa() == null) {

			contrato.inserirPessoaJuridica(obj);
		} else {
			contrato.atualizarPessoaJuridica(obj);
		}
	}

	public void excluirPessoaFisica(Pessoa obj) {
		contrato.deletarPorIdFisica(obj.getIdPessoa());
	}

	public void excluirPessoaJuridica(Pessoa obj) {
		contrato.deletarPorIdJuridica(obj.getIdPessoa());
	}

	public List<Pessoa> buscaClientesFisico() {
		return contrato.acharTudoFisica();

	}

	public List<Pessoa> buscaClientesJuridico() {
		return contrato.acharTudoJuridica();

	}
	public Fisica buscarNomeFisica(String nome) {
		return contrato.acharPorNomeFisica(nome);
	}
	
	public Juridica buscarNomeJuridica(String nome) {
		return contrato.acharPorNomeJuridica(nome);
	}

}
