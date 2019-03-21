package model.mysql.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import itg.util.Alertas;
import model.entidades.UsuariosLogin;
import model.mysql.UsuariosLoginMYSQL;

public class UsuariosLoginJDBC implements UsuariosLoginMYSQL {

	private Connection conn; // seta conxão com banco

	public UsuariosLoginJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(UsuariosLogin obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void atualizar(UsuariosLogin obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletarPorId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public UsuariosLogin acharPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuariosLogin> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM login ORDER BY Usuario");
			rs = st.executeQuery();

			List<UsuariosLogin> list = new ArrayList<>();

			while (rs.next()) {
				UsuariosLogin obj = new UsuariosLogin();
				obj.setId(rs.getInt("Id"));
				obj.setUsuario(rs.getString("Usuario"));
				obj.setSenha(rs.getString("Senha"));
				obj.setGrau(rs.getInt("Grau"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela login no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public UsuariosLogin verificaUsuarioSenha(String nome, String senha) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM login WHERE Usuario = ? and Senha = ?");
			st.setString(1, nome);
			st.setString(2, senha);
			rs = st.executeQuery();
			if (rs.next()) {
				UsuariosLogin obj = new UsuariosLogin();
				obj.setId(rs.getInt("Id"));
				obj.setUsuario(rs.getString("Usuario"));
				obj.setSenha(rs.getString("Senha"));
				obj.setGrau(rs.getInt("Grau"));
				return obj;
			}
			return null;
		} catch (SQLException e) {

			throw new DbException("Erro ao buscar Lista de Dados da tabela login no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

}
