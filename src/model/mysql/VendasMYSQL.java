package model.mysql;

import java.util.List;

import model.entidades.Vendas;

public interface VendasMYSQL {
	void inserirVendas(Vendas obj);
	void deletarVendas(Integer id);
	Vendas acharPorId(Integer id);
	List<Vendas> acharListPorId(Integer id);
	List<Vendas> acharTudo();

}
