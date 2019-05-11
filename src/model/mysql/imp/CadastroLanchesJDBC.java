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
import model.entidades.Lanches;
import model.entidades.Produtos;
import model.mysql.CadastroLanchesMYSQL;

public class CadastroLanchesJDBC implements CadastroLanchesMYSQL {

	private Connection conn; // seta conxão com banco

	public CadastroLanchesJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirLanches(Lanches obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO lanches " + "(Nome, Descricao, LinkImgLanche, ValorLanche) "
					+ "VALUES " + "(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeLanches());
			st.setString(2, obj.getDescricao());
			st.setString(3, obj.getLinkImgLanche());
			st.setDouble(4, obj.getValorLanche());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdLanches(id);
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
	public void atualizarLanches(Lanches obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("UPDATE lanches "
					+ "SET Nome = ?, Descricao = ?, LinkImgLanche = ?, ValorLanche = ? " + "WHERE IdLanches = ?");

			st.setString(1, obj.getNomeLanches());
			st.setString(2, obj.getDescricao());
			st.setString(3, obj.getLinkImgLanche());
			st.setDouble(4, obj.getValorLanche());
			st.setInt(5, obj.getIdLanches());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarLanches(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM lanches WHERE IdLanches= ?");
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Lanches acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM lanches " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {
				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
				obj.setLinkImgLanche(rs.getString("LinkImgLanche"));
				obj.setValorLanche(rs.getDouble("ValorLanche"));

				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela lanches no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Lanches acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM lanches " + "WHERE IdLanches = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {
				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
				obj.setLinkImgLanche(rs.getString("LinkImgLanche"));
				obj.setValorLanche(rs.getDouble("ValorLanche"));

				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela lanches no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Lanches> acharListPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM lanches " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();
			List<Lanches> list = new ArrayList<>();
			while (rs.next()) {

				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
				obj.setLinkImgLanche(rs.getString("LinkImgLanche"));
				obj.setValorLanche(rs.getDouble("ValorLanche"));

				list.add(obj);

			}
			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela lanches no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Lanches> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM lanches " + "WHERE IdLanches = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			List<Lanches> list = new ArrayList<>();
			while (rs.next()) {

				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
				obj.setLinkImgLanche(rs.getString("LinkImgLanche"));
				obj.setValorLanche(rs.getDouble("ValorLanche"));

				list.add(obj);

			}
			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela lanches no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Lanches> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM lanches ");

			rs = st.executeQuery();
			List<Lanches> list = new ArrayList<>();
			while (rs.next()) {

				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Descricao"));
				obj.setLinkImgLanche(rs.getString("LinkImgLanche"));
				obj.setValorLanche(rs.getDouble("ValorLanche"));

				list.add(obj);

			}
			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela lanches no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}

	}

}
