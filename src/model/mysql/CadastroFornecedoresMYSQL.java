package model.mysql;

import java.util.List;

import model.entidades.Juridica;
import model.entidades.Pessoa;

public interface CadastroFornecedoresMYSQL {
	void inserirPessoaJuridica(Juridica obj);
	void atualizarPessoaJuridica(Juridica obj);
	void deletarFornecedores(Integer id);
	Pessoa acharPorNome(String nome);
	Pessoa acharPorId(Integer id);
	List<Pessoa> acharCNPJ(String cnpj);
	List<Pessoa> acharTudo();

}
