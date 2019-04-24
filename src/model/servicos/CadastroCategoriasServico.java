package model.servicos;

import java.util.List;

import model.entidades.Categorias;
import model.mysql.CadastroCategoriasMYSQL;
import model.mysql.ConexaoTabelas;

public class CadastroCategoriasServico {
	private CadastroCategoriasMYSQL contrato = ConexaoTabelas.criarCadastroCategoriasMYSQL();

	public void iniciarUpdateOuIserirCategorias(Categorias obj) {
		if (obj.getIdCategorias() == null) {

			contrato.inserirCategorias(obj);
		} else {
			contrato.atualizarCategorias(obj);
		}
	}

	public void excluirCategorias(Categorias obj) {
		contrato.deletarCategorias(obj.getIdCategorias());
	}

	public List<Categorias> buscarCategorias() {
		return contrato.acharTudo();

	}

	public Categorias buscarNome(String nome) {
		return contrato.acharPorNome(nome);
	}

	public Categorias buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public List<Categorias> buscarListPorNome(String nome) {
		return contrato.acharListPorNome(nome);

	}

	public List<Categorias> buscarListPorId(Integer id) {
		return contrato.acharListPorId(id);

	}
}
