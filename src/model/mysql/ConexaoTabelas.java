package model.mysql;

import db.DB;
import model.mysql.imp.CadastroClientesJDBC;
import model.mysql.imp.UsuariosLoginJDBC;

public class ConexaoTabelas {

	public static UsuariosLoginMYSQL criarUsuariosLoginMYSQL() {
		return new UsuariosLoginJDBC(DB.getConnection());
	}
	
	public static CadastroClientesMYSQL criarCadastroClientesMYSQL()
	{
		return new CadastroClientesJDBC(DB.getConnection());
	}
	

}
