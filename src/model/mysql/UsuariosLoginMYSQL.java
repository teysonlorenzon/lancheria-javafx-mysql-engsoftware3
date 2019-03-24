package model.mysql;

import java.util.List;

import model.entidades.UsuariosLogin;

public interface UsuariosLoginMYSQL {

	void inserir(UsuariosLogin obj);
	void atualizar(UsuariosLogin obj);
	void deletarPorId(Integer id);
	UsuariosLogin verificaUsuarioSenha(String usuario, String senha);
	UsuariosLogin acharPorId(Integer id);
	UsuariosLogin acharPorUsuario(String usuario);
	List<UsuariosLogin> acharTudo();

}
