package model.mysql;

import java.util.List;

import model.entidades.Fisica;
import model.entidades.Pessoa;

public interface CadastroFucionariosMYSQL {

	void inserirPessoaFisica(Fisica obj);
	void atualizarPessoaFisica(Fisica obj);
	void deletarFuncionarios(Integer id);
	Pessoa acharPorNome(String nome);
	Pessoa acharPorId(Integer id);
	List<Pessoa> acharCPF(String cpf);
	List<Pessoa> acharListPorNome(String nome);
	List<Pessoa> acharListPorId(Integer id);
	List<Pessoa> acharTudo();

}
