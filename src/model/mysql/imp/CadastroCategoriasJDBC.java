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
import model.entidades.Categorias;
import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.mysql.CadastroCategoriasMYSQL;

public class CadastroCategoriasJDBC implements CadastroCategoriasMYSQL {

	private Connection conn; // seta conxão com banco

	public CadastroCategoriasJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirCategorias(Categorias obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO categorias " + "(Nome) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdCategorias(id);
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
	public void atualizarCategorias(Categorias obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("UPDATE categorias " + "SET Nome = ?" + "WHERE IdCategorias = ?");

			st.setString(1, obj.getNome());
			st.setInt(2, obj.getIdCategorias());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarCategorias(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM categorias WHERE IdCategorias= ?");
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Categorias acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categorias " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {
				Categorias obj = new Categorias();
				obj.setIdCategorias(rs.getInt("IdCategorias"));
				obj.setNome(rs.getString("Nome"));

				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela categorias no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Categorias acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categorias " + "WHERE IdCategorias = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				Categorias obj = new Categorias();
				obj.setIdCategorias(rs.getInt("IdCategorias"));
				obj.setNome(rs.getString("Nome"));

				return obj;

			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela categorias no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Categorias> acharListPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categorias " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			List<Categorias> list = new ArrayList<>();

			while (rs.next()) {

				Categorias obj = new Categorias();
				obj.setIdCategorias(rs.getInt("IdCategorias"));
				obj.setNome(rs.getString("Nome"));

				list.add(obj);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela categorias no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Categorias> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categorias " + "WHERE IdCategorias = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			List<Categorias> list = new ArrayList<>();

			while (rs.next()) {
				Categorias obj = new Categorias();
				obj.setIdCategorias(rs.getInt("IdCategorias"));
				obj.setNome(rs.getString("Nome"));

				list.add(obj);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela categorias no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Categorias> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categorias");
			rs = st.executeQuery();

			List<Categorias> list = new ArrayList<>();

			while (rs.next()) {

				Categorias obj = new Categorias();
				obj.setIdCategorias(rs.getInt("IdCategorias"));
				obj.setNome(rs.getString("Nome"));

				list.add(obj);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela categorias no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
