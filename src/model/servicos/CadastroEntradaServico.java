package model.servicos;

import java.util.List;

import model.entidades.Entrada;
import model.mysql.CadastroEntradaMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroEntradaServico {
	private CadastroEntradaMYSQL contrato = ConexaoTabelas.criarCadastroEntradaMYSQL();

	public void iniciarUpdateOuIserirEntrada(Entrada obj) {
		if (obj.getIdEntrada() == null) {

			contrato.inserirEntrada(obj);
		} else {
			contrato.atualizarEntrada(obj);
		}
	}

	public void excluirEntrada(Entrada obj) {
		contrato.deletarEntrada(obj.getIdEntrada());
	}

	public List<Entrada> buscarEntrada() {
		return contrato.acharTudo();

	}

	public Entrada buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}

	public Entrada buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Entrada> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Entrada> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
	

}
