package model.mysql;

import java.util.List;

import model.entidades.Estoque;

public interface EstoqueMYSQL {

	List<Estoque> acharTudo();
	List<Estoque> acharListPorProduto(String nome);

}
