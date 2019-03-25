package model.mysql.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import itg.util.Alertas;
import model.entidades.UsuariosLogin;
import model.mysql.UsuariosLoginMYSQL;

public class UsuariosLoginJDBC implements UsuariosLoginMYSQL {

	private Connection conn; // seta conx�o com banco

	public UsuariosLoginJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserir(UsuariosLogin obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO login " + "(Usuario, Senha, Nivel) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getUsuario());
			st.setString(2, obj.getSenha());
			st.setString(3, obj.getNivel());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void atualizar(UsuariosLogin obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE login " +
			"SET Usuario = ?, Senha = ?, Nivel = ? " + 
			"WHERE Id = ?");

			st.setString(1, obj.getUsuario());
			st.setString(2, obj.getSenha());
			st.setString(3, obj.getNivel());
			st.setInt(4, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarPorId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"DELETE FROM login WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} 
		finally {
			DB.closeStatement(st);
		}
	}


	@Override
	public UsuariosLogin acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM login WHERE Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				UsuariosLogin obj = new UsuariosLogin();
				obj.setId(rs.getInt("Id"));
				obj.setUsuario(rs.getString("Usuario"));
				obj.setSenha(rs.getString("Senha"));
				obj.setNivel(rs.getString("Nivel"));
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
				obj.setNivel(rs.getString("Nivel"));
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
				obj.setNivel(rs.getString("Nivel"));
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

	@Override
	public UsuariosLogin acharPorUsuario(String usuario) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM login WHERE Usuario = ?");
			st.setString(1, usuario);
			rs = st.executeQuery();
			if (rs.next()) {
				UsuariosLogin obj = new UsuariosLogin();
				obj.setId(rs.getInt("Id"));
				obj.setUsuario(rs.getString("Usuario"));
				obj.setSenha(rs.getString("Senha"));
				obj.setNivel(rs.getString("Nivel"));
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
