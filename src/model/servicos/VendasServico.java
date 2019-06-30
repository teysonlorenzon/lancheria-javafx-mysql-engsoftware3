package model.servicos;

import java.util.List;

import model.entidades.Vendas;
import model.mysql.ConexaoTabelas;
import model.mysql.VendasMYSQL;

public class VendasServico {
	private VendasMYSQL contrato = ConexaoTabelas.criarVendasMYSQL();

	public void iniciarUpdateOuIserirVendas(Vendas obj) {
		if (obj.getIdSaida() == null) {

			contrato.inserirVendas(obj);
		}
	}

	public void excluirVendas(Vendas obj) {
		contrato.deletarVendas(obj.getIdSaida());
	}

	public List<Vendas> buscarVendas() {
		return contrato.acharTudo();

	}


	public Vendas buscarId(Integer id) {
		return contrato.acharPorId(id);
	}



	public List<Vendas> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
}
