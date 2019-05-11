package model.mysql;

import java.util.List;

import model.entidades.Categorias;
import model.entidades.Pessoa;
import model.entidades.Produtos;

public interface CadastroProdutosMYSQL {

	void inserirProdutos(Produtos obj);
	void atualizarProdutos(Produtos obj);
	void deletarProdutos(Integer id);
	Produtos acharPorNome(String nome);
	Produtos acharPorId(Integer id);
	List<Produtos> acharListPorNome(String nome);
	List<Produtos> acharListPorId(Integer id);
	List<Produtos> acharTudo();
	List<Produtos> acharProdutoPorCategoria(Integer id);
	
}
