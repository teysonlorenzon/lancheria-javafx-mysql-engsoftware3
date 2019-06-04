package model.servicos;

import java.util.List;

import model.entidades.Estoque;
import model.mysql.CadastroEntradaMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroEntradaServico {
	private CadastroEntradaMYSQL contrato = ConexaoTabelas.criarCadastroEntradaMYSQL();

	public void iniciarUpdateOuIserirEntrada(Estoque obj) {
		if (obj.getIdEntrada() == null) {

			contrato.inserirEntrada(obj);
		} else {
			contrato.atualizarEntrada(obj);
		}
	}

	public void excluirEntrada(Estoque obj) {
		contrato.deletarEntrada(obj.getIdEntrada());
	}

	public List<Estoque> buscarEntrada() {
		return contrato.acharTudo();

	}

	public Estoque buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}

	public Estoque buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Estoque> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Estoque> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
	

}
