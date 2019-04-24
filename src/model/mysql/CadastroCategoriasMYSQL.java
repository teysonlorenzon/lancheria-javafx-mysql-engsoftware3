package model.mysql;

import java.util.List;

import model.entidades.Categorias;

public interface CadastroCategoriasMYSQL {

	void inserirCategorias(Categorias obj);
	void atualizarCategorias(Categorias obj);
	void deletarCategorias(Integer id);
	Categorias acharPorNome(String nome);
	Categorias acharPorId(Integer id);
	List<Categorias> acharListPorNome(String nome);
	List<Categorias> acharListPorId(Integer id);
	List<Categorias> acharTudo();
	
	
}
