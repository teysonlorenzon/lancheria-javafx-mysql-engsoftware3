package model.mysql;

import java.util.List;

import model.entidades.Lanches;

public interface CadastroLanchesMYSQL {
	void inserirLanches(Lanches obj);
	void atualizarLanches(Lanches obj);
	void deletarLanches(Integer id);
	Lanches acharPorNome(String nome);
	Lanches acharPorId(Integer id);
	List<Lanches> acharListPorNome(String nome);
	List<Lanches> acharListPorId(Integer id);
	List<Lanches> acharTudo();

}
