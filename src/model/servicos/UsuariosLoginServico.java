package model.servicos;

import java.util.List;

import model.entidades.UsuariosLogin;
import model.mysql.ConexaoTabelas;
import model.mysql.UsuariosLoginMYSQL;

public class UsuariosLoginServico {

	private UsuariosLoginMYSQL contrato = ConexaoTabelas.criarUsuariosLoginMYSQL();

	public List<UsuariosLogin> buscarTudo() {
		return contrato.acharTudo();
	}

	public void excluirUsuario(UsuariosLogin obj) {
		contrato.deletarPorId(obj.getId());
	}

	public UsuariosLogin buscarUsuarioSenha(String usuario, String senha) {
		return contrato.verificaUsuarioSenha(usuario, senha);
	}

	public UsuariosLogin buscarUsuario(String usuario) {
		return contrato.acharPorUsuario(usuario);
	}

	public UsuariosLogin buscarId(Integer id) {
		return contrato.acharPorId(id);
	}

	public void iniciarUpdateOuIserir(UsuariosLogin obj) {
		if (obj.getId() == null) {

			contrato.inserir(obj);
		} else {
			contrato.atualizar(obj);
		}
	}

}
