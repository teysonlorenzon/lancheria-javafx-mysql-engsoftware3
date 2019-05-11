package model.servicos;

import java.util.List;

import model.entidades.Lanches;
import model.mysql.CadastroLanchesMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroLanchesServico {
	private CadastroLanchesMYSQL contrato = ConexaoTabelas.criarCadastroLanchesMYSQL();

	public void iniciarUpdateOuIserirLanches(Lanches obj) {
		if (obj.getIdLanches() == null) {

			contrato.inserirLanches(obj);
		} else {
			contrato.atualizarLanches(obj);
		}
	}

	public void excluirLanches(Lanches obj) {
		contrato.deletarLanches(obj.getIdLanches());
	}

	public List<Lanches> buscarLanches() {
		return contrato.acharTudo();

	}

	public Lanches buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}

	public Lanches buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Lanches> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Lanches> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
}
