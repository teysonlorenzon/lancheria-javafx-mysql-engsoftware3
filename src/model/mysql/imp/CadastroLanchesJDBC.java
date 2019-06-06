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
import model.servicos.CadastroProdutosServico;

public class CadastroLanchesJDBC implements CadastroLanchesMYSQL {

	private Connection conn; // seta conxão com banco
	private CadastroProdutosServico service = new CadastroProdutosServico();

	public CadastroLanchesJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirLanches(Lanches obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO lanches " + "(Nome, LinkImgLanche, ValorLanche) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNomeLanches());
			st.setString(2, obj.getLinkImgLanche());
			st.setDouble(3, obj.getValorLanche());

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

			st2 = conn.prepareStatement("INSERT INTO itenslanche (IdLanche, IdProduto, Quantidade) " + "VALUES (?, ?, ?)");

			String stNome = obj.getDescricao();
			String stQuant = obj.getQuantidade();
			Produtos result = new Produtos();
			String[] stArrayNome = stNome.split(",");
			String[] stArrayQuant = stQuant.split(",");

			for (int i = 0; i < stArrayNome.length; i++) {
				result = service.buscarNome(stArrayNome[i]);
				st2.setInt(1, obj.getIdLanches());
				st2.setInt(2, result.getIdProdutos());
				st2.setInt(3, Integer.parseInt(stArrayQuant[i]));
				st2.executeUpdate();
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public void atualizarLanches(Lanches obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		PreparedStatement st3 = null;
		try {

			st = conn.prepareStatement(
					"UPDATE lanches " + "SET Nome = ?, LinkImgLanche = ?, ValorLanche = ? " + "WHERE IdLanches = ?");

			st.setString(1, obj.getNomeLanches());
			st.setString(2, obj.getLinkImgLanche());
			st.setDouble(3, obj.getValorLanche());
			st.setInt(4, obj.getIdLanches());
			st.executeUpdate();

			st2 = conn.prepareStatement("DELETE FROM itenslanche WHERE IdLanche= ?");
			st2.setInt(1, obj.getIdLanches());
			st2.executeUpdate();

			st3 = conn.prepareStatement("INSERT INTO itenslanche (IdLanche, IdProduto, Quantidade) " + "VALUES (?, ?, ?)");

			String stNome = obj.getDescricao();
			String stQuant = obj.getQuantidade();
			Produtos result = new Produtos();
			String[] stArrayNome = stNome.split(",");
			String[] stArrayQuant = stQuant.split(",");

			for (int i = 0; i < stArrayNome.length; i++) {
				result = service.buscarNome(stArrayNome[i]);
				st3.setInt(1, obj.getIdLanches());
				st3.setInt(2, result.getIdProdutos());
				st3.setInt(3, Integer.parseInt(stArrayQuant[i]));
				st3.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
			DB.closeStatement(st3);
		}

	}

	@Override
	public void deletarLanches(Integer id) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			
			st = conn.prepareStatement("DELETE FROM itenslanche WHERE IdLanche= ?");
			st.setInt(1, id);
			st.executeUpdate();

			st2 = conn.prepareStatement("DELETE FROM lanches WHERE IdLanches= ?");
			st2.setInt(1, id);
			st2.executeUpdate();
			

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public Lanches acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT l.IdLanches,l.Nome, group_concat(il.Quantidade,' ',p.nome) as Produto, l.ValorLanche,l.LinkImgLanche "
							+ "FROM lanches as l " + "LEFT JOIN itenslanche il on l.IdLanches = il.IdLanche "
							+ "LEFT JOIN produtos p on p.IdProdutos = il.IdProduto " + "WHERE l.Nome = ? "
							+ "GROUP BY l.Nome");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {
				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Produto"));
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
			st = conn.prepareStatement(
					"SELECT l.IdLanches,l.Nome, group_concat(il.Quantidade,' ',p.nome) as Produto, l.ValorLanche,l.LinkImgLanche "
							+ "FROM lanches as l " + "LEFT JOIN itenslanche il on l.IdLanches = il.IdLanche "
							+ "LEFT JOIN produtos p on p.IdProdutos = il.IdProduto " + "WHERE l.IdLanches = ? "
							+ "GROUP BY l.Nome");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {
				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Produto"));
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
			st = conn.prepareStatement(
					"SELECT l.IdLanches,l.Nome, group_concat(il.Quantidade,' ',p.nome) as Produto, l.ValorLanche,l.LinkImgLanche "
							+ "FROM lanches as l " + "LEFT JOIN itenslanche il on l.IdLanches = il.IdLanche "
							+ "LEFT JOIN produtos p on p.IdProdutos = il.IdProduto " + "WHERE l.Nome = ? "
							+ "GROUP BY l.Nome");

			st.setString(1, nome);
			rs = st.executeQuery();
			List<Lanches> list = new ArrayList<>();
			while (rs.next()) {

				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Produto"));
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
			st = conn.prepareStatement(
					"SELECT l.IdLanches,l.Nome, group_concat(il.Quantidade,' ',p.nome) as Produto, l.ValorLanche,l.LinkImgLanche " + 
							"FROM lanches as l " +
							"LEFT JOIN itenslanche il on l.IdLanches = il.IdLanche " +
							"LEFT JOIN produtos p on p.IdProdutos = il.IdProduto " +
							"WHERE l.IdLanches = ? " +
							"GROUP BY l.Nome");

			st.setInt(1, id);
			rs = st.executeQuery();
			List<Lanches> list = new ArrayList<>();
			while (rs.next()) {

				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Produto"));
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
			st = conn.prepareStatement(
					"SELECT l.IdLanches,l.Nome, group_concat(il.Quantidade,' ',p.nome) as Produto, l.ValorLanche,l.LinkImgLanche " + 
					"FROM lanches as l " +
					"LEFT JOIN itenslanche il on l.IdLanches = il.IdLanche " +
					"LEFT JOIN produtos p on p.IdProdutos = il.IdProduto " +
					"GROUP BY l.Nome");

			rs = st.executeQuery();
			List<Lanches> list = new ArrayList<>();
			while (rs.next()) {

				Lanches obj = new Lanches();
				obj.setIdLanches(rs.getInt("IdLanches"));
				obj.setNomeLanches(rs.getString("Nome"));
				obj.setDescricao(rs.getString("Produto"));
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
