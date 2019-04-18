package model.mysql;

import java.util.List;

import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;

public interface CadastroClientesMYSQL {

	void inserirPessoaFisica(Fisica obj);
	void inserirPessoaJuridica(Juridica obj);
	void atualizarPessoaFisica(Fisica obj);
	void atualizarPessoaJuridica(Juridica obj);
	void deletarClientes(Integer id);
	Pessoa acharPorNome(String nome);
	Pessoa acharPorId(Integer id);
	List<Pessoa> acharCPF(String cpf);
	List<Pessoa> acharCNPJ(String cnpj);
	List<Pessoa> acharTudo(char tipo);


}
