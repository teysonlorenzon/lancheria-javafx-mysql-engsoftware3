package model.servicos;

import java.util.List;

import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.mysql.CadastroFucionariosMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroFuncionariosServico {
	private CadastroFucionariosMYSQL contrato = ConexaoTabelas.criarCadastroFuncionariosMYSQL();

	public void iniciarUpdateOuIserirPessoaFisica(Fisica obj) {
		if (obj.getIdPessoa() == null) {

			contrato.inserirPessoaFisica(obj);
		} else {
			contrato.atualizarPessoaFisica(obj);
		}
	}


	public void excluirPessoa(Pessoa obj) {
		contrato.deletarFuncionarios(obj.getIdPessoa());
	}


	public List<Pessoa> buscarFuncionarios() {
		return contrato.acharTudo();

	}

	public Pessoa buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}
	
	
	public Pessoa buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Pessoa> buscarCPF(String cpf) {
		return contrato.acharCPF(cpf);
	}

}
