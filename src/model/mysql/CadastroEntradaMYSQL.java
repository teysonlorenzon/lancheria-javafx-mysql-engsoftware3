package model.mysql;

import java.util.List;

import model.entidades.Estoque;

public interface CadastroEntradaMYSQL {

	void inserirEntrada(Estoque obj);
	void atualizarEntrada(Estoque obj);
	void deletarEntrada(Integer id);
	Estoque acharPorNome(String nome);
	Estoque acharPorId(Integer id);
	List<Estoque> acharListPorNome(String nome);
	List<Estoque> acharListPorId(Integer id);
	List<Estoque> acharTudo();
	
}
