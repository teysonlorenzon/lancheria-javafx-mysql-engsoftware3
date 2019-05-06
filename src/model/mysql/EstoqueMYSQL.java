package model.mysql;

import java.util.List;

import model.entidades.Estoque;

public interface EstoqueMYSQL {
	void inserirEstoque(Estoque obj);
	void atualizarEstoque(Estoque obj);
	Estoque acharPorNome(String nome);
	Estoque acharPorId(Integer id);
	List<Estoque> acharListPorNome(String nome);
	List<Estoque> acharListPorId(Integer id);
	List<Estoque> acharTudo();
}
