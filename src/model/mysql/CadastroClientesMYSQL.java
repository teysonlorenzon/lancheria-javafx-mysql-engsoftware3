package model.mysql;

import java.util.List;

import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;

public interface CadastroClientesMYSQL {

	void inserirPessoaFisicaJuridica(Fisica obj, Juridica obj2);
	void atualizar(Pessoa obj);
	void deletarPorId(Integer id);
	Pessoa acharPorId(Integer id);
	List<Pessoa> acharTudoFisica();
	List<Pessoa> acharTudoJuridica();

}
