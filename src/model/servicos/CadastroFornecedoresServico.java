package model.servicos;

import java.util.List;

import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.mysql.CadastroFornecedoresMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroFornecedoresServico {

	private CadastroFornecedoresMYSQL contrato = ConexaoTabelas.criarCadastroFornecedoresMYSQL();

	public void iniciarUpdateOuIserirPessoaJuridica(Juridica obj) {
		if (obj.getIdPessoa() == null) {

			contrato.inserirPessoaJuridica(obj);
		} else {
			contrato.atualizarPessoaJuridica(obj);
		}
	}

	public void excluirPessoa(Pessoa obj) {
		contrato.deletarFornecedores(obj.getIdPessoa());
	}

	public List<Pessoa> buscarFornecedores() {
		return contrato.acharTudo();

	}

	public Pessoa buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}

	public Pessoa buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Pessoa> buscarCNPJ(String cnpj) {
		return contrato.acharCNPJ(cnpj);
	}
	
	public List<Pessoa> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Pessoa> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}

}
