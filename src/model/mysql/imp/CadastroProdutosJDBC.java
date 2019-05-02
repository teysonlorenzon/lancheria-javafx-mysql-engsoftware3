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
import model.entidades.Pessoa;
import model.entidades.Produtos;
import model.mysql.CadastroProdutosMYSQL;

public class CadastroProdutosJDBC implements CadastroProdutosMYSQL {

	private Connection conn; // seta conxão com banco

	public CadastroProdutosJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirProdutos(Produtos obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO produtos " + "(Nome, Quantidade, IdFornecedores, IdCategorias) "
					+ "VALUES " + "(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setInt(2, obj.getQuantidade());
			st.setInt(3, obj.getIdFornecedores());
			st.setInt(4, obj.getIdCategorias());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdProdutos(id);
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
	public void atualizarProdutos(Produtos obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("UPDATE produtos " + "SET Nome = ?, Quantidade = ?, IdFornecedores = ?, IdCategorias = ? " + "WHERE IdProdutos = ?");

			st.setString(1, obj.getNome());
			st.setInt(2, obj.getQuantidade());
			st.setInt(3, obj.getIdFornecedores());
			st.setInt(4, obj.getIdCategorias());
			st.setInt(5, obj.getIdProdutos());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarProdutos(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM produtos WHERE IdProdutos= ?");
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Produtos acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {
				Produtos obj = new Produtos();
				obj.setIdProdutos(rs.getInt("IdProdutos"));
				obj.setNome(rs.getString("Nome"));
				obj.setQuantidade(rs.getInt("IdQuantidade"));
				obj.setIdFornecedores(rs.getInt("IdFornecedores"));
				obj.setIdCategorias(rs.getInt("IdCategorias"));

				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Produtos acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos " + "WHERE IdProdutos = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				Produtos obj = new Produtos();
				obj.setIdCategorias(rs.getInt("IdProdutos"));
				obj.setNome(rs.getString("Nome"));
				obj.setQuantidade(rs.getInt("IdQuantidade"));
				obj.setIdFornecedores(rs.getInt("IdFornecedores"));
				obj.setIdCategorias(rs.getInt("IdCategorias"));

				return obj;

			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Produtos> acharListPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			List<Produtos> list = new ArrayList<>();

			while (rs.next()) {

				Produtos obj = new Produtos();
				obj.setIdCategorias(rs.getInt("IdProdutos"));
				obj.setNome(rs.getString("Nome"));
				obj.setQuantidade(rs.getInt("IdQuantidade"));
				obj.setIdFornecedores(rs.getInt("IdFornecedores"));
				obj.setIdCategorias(rs.getInt("IdCategorias"));

				list.add(obj);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Produtos> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM produtos " + "WHERE IdProdutos = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			List<Produtos> list = new ArrayList<>();

			while (rs.next()) {

				Produtos obj = new Produtos();
				obj.setIdCategorias(rs.getInt("IdProdutos"));
				obj.setNome(rs.getString("Nome"));
				obj.setQuantidade(rs.getInt("IdQuantidade"));
				obj.setIdFornecedores(rs.getInt("IdFornecedores"));
				obj.setIdCategorias(rs.getInt("IdCategorias"));

				list.add(obj);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Produtos> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT p.IdProdutos, p.Nome, p.Quantidade, f.Nome as nomeF, c.Nome as nomeC " +
					"FROM produtos p " + 
					"LEFT JOIN fornecedores f on f.IdFornecedores = p.IdFornecedores "  + 
					"LEFT JOIN categorias c on c.IdCategorias = p.IdCategorias " + "GROUP BY p.nome");
			rs = st.executeQuery();
			List<Produtos> listProd = new ArrayList<>();

			while (rs.next()) {

				Produtos obj = new Produtos();
				obj.setIdProdutos(rs.getInt("IdProdutos"));
				obj.setNome(rs.getString("Nome"));
				obj.setQuantidade(rs.getInt("Quantidade"));
				obj.setNomeFornecedores(rs.getString("nomeF"));
				obj.setNomeCategorias(rs.getString("nomeC"));

				listProd.add(obj);

			}
					
			return listProd;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
