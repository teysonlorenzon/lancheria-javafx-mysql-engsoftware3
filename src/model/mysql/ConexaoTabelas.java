package model.mysql;

import db.DB;
import model.mysql.imp.UsuariosLoginJDBC;

public class ConexaoTabelas {

	public static UsuariosLoginMYSQL criarUsuariosLoginMYSQL() {
		return new UsuariosLoginJDBC(DB.getConnection());
	}


}
