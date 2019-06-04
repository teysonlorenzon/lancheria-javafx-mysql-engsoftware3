package model.servicos;

import java.util.List;

import model.entidades.Estoque;
import model.mysql.EstoqueMYSQL;
import model.mysql.ConexaoTabelas;

public class EstoqueServico {
	private EstoqueMYSQL contrato = ConexaoTabelas.criarEstoqueMYSQL();

	
	public List<Estoque> buscarEstoque() {
		return contrato.acharTudo();

	}
	
	public List<Estoque> buscarListPorProduto(String nome) {
		return contrato.acharListPorProduto(nome);

	}

	
}
