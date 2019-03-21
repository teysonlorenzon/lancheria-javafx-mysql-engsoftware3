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

	public UsuariosLogin buscarUsuarioSenha(String usuario, String senha) {
		return contrato.verificaUsuarioSenha(usuario, senha);
	}
}
