package model.servicos;

import java.util.List;

import model.entidades.Produtos;
import model.mysql.CadastroProdutosMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroProdutosServico {
	private CadastroProdutosMYSQL contrato = ConexaoTabelas.criarCadastroProdutosMYSQL();

	public void iniciarUpdateOuIserirProdutos(Produtos obj) {
		if (obj.getIdProdutos() == null) {

			contrato.inserirProdutos(obj);
		} else {
			contrato.atualizarProdutos(obj);
		}
	}

	public void excluirProdutos(Produtos obj) {
		contrato.deletarProdutos(obj.getIdProdutos());
	}

	public List<Produtos> buscarProdutos() {
		return contrato.acharTudo();

	}

	public Produtos buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}

	public Produtos buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Produtos> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Produtos> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
	public List<Produtos> buscarListProdutosPorCategorias(Integer id) {
		return contrato.acharProdutoPorCategoria(id);

	}

}
