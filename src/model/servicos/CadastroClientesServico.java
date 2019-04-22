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

	public void excluirPessoa(Pessoa obj) {
		contrato.deletarClientes(obj.getIdPessoa());
	}

	public List<Pessoa> buscarClientes(char tipo) {
		return contrato.acharTudo(tipo);

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

	public List<Pessoa> buscarCPF(String cpf) {
		return contrato.acharCPF(cpf);
	}

	public List<Pessoa> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Pessoa> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
}
