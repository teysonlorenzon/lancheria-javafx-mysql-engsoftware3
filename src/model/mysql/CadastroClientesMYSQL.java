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
	void deletarPorIdFisica(Integer id);
	void deletarPorIdJuridica(Integer id);
	Fisica acharPorNomeFisica(String nome);
	Juridica acharPorNomeJuridica(String nome);
	List<Pessoa> acharTudoFisica();
	List<Pessoa> acharTudoJuridica();

}
