package model.mysql;

import java.util.List;

import model.entidades.Entrada;

public interface CadastroEntradaMYSQL {

	void inserirEntrada(Entrada obj);
	void atualizarEntrada(Entrada obj);
	void deletarEntrada(Integer id);
	Entrada acharPorNome(String nome);
	Entrada acharPorId(Integer id);
	List<Entrada> acharListPorNome(String nome);
	List<Entrada> acharListPorId(Integer id);
	List<Entrada> acharTudo();
	
}
