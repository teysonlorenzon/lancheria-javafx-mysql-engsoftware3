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
import model.entidades.Estoque;
import model.mysql.CadastroEntradaMYSQL;
import model.servicos.EstoqueServico;

public class CadastroEntradaJDBC implements CadastroEntradaMYSQL {

	private Connection conn; // seta conxão com banco
	private EstoqueServico sv = new EstoqueServico();
	public CadastroEntradaJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirEntrada(Estoque obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			st = conn.prepareStatement("INSERT INTO estoqueentrada "
					+ "(DataEntrada, ValorUnitario, Quantidade, IdProdutos, IdFornecedores, IdFuncionarios) "
					+ "VALUES " + "(?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDataEntrada());
			st.setDouble(2, obj.getValorUnitario());
			st.setInt(3, obj.getQuantidade());
			st.setInt(4, obj.getIdProdutos());
			st.setInt(5, obj.getIdFornecedores());
			st.setInt(6, obj.getIdFuncionario());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdEntrada(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

			Estoque obj2 = new Estoque();
			obj2 = sv.buscarProd(obj.getIdProdutos());
			
			if (obj2 == null) {

				st2 = conn.prepareStatement(
						"INSERT INTO estoque" + "(Quantidade, IdProduto) " + "VALUES " + "(?, ?)",
						Statement.RETURN_GENERATED_KEYS);

				st2.setInt(1, obj.getQuantidade());
				st2.setInt(2, obj.getIdProdutos());

				int rowsAffected2 = st2.executeUpdate();

				if (rowsAffected2 > 0) {
					ResultSet rs = st2.getGeneratedKeys();
					if (rs.next()) {
						int id = rs.getInt(1);
						obj.setIdEntrada(id);
					}
				} else {
					throw new DbException("Unexpected error! No rows affected!");
				}
			} else {
				st2 = conn.prepareStatement(
						"UPDATE estoque " + "SET Quantidade = Quantidade + ? " + "WHERE IdProduto = ?");
				st2.setInt(1, obj.getQuantidade());
				st2.setInt(2, obj.getIdProdutos());

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
	public void atualizarEntrada(Estoque obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			
			Estoque obj2 = new Estoque();
			Estoque obj3 = new Estoque();
			
			obj2 = sv.buscarProd(obj.getIdProdutos());
			obj3 = acharPorId(obj.getIdEntrada());
			
			st = conn.prepareStatement("UPDATE estoqueentrada "
					+ "SET DataEntrada = ?, ValorUnitario = ?, Quantidade = ?, IdProdutos = ?, IdFornecedores = ?, IdFuncionarios = ? "
					+ "WHERE IdEntrada = ?");

			st.setString(1, obj.getDataEntrada());
			st.setDouble(2, obj.getValorUnitario());
			st.setInt(3, obj.getQuantidade());
			st.setInt(4, obj.getIdProdutos());
			st.setInt(5, obj.getIdFornecedores());
			st.setInt(6, obj.getIdFuncionario());
			st.setInt(7, obj.getIdEntrada());
			st.executeUpdate();
			
			Integer guarda = obj2.getQuantidade() - obj3.getQuantidade();
			guarda += obj.getQuantidade();
			
			st2 = conn
					.prepareStatement("UPDATE estoque " + "SET Quantidade = ? " + "WHERE idProduto = ?");
			st2.setInt(1, guarda);
			st2.setInt(2, obj.getIdProdutos());
			st2.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public void deletarEntrada(Integer id) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			System.out.println(id);
			st = conn.prepareStatement("DELETE FROM estoqueentrada WHERE IdEntrada = ?");
			st.setInt(1, id);
			
			
			
			Estoque obj = new Estoque();
			obj = acharPorId(id);
			
			st2 = conn.prepareStatement("UPDATE estoque " + "SET Quantidade = Quantidade - ? " + "WHERE IdProduto = ?");
			st2.setInt(1, obj.getQuantidade());
			st2.setInt(2, obj.getIdProdutos());
			
			st2.executeUpdate();
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public Estoque acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc"
							+ "FROM estoqueentrada e " + "LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos "
							+ "LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores "
							+ "LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios "
							+ "WHERE e.Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {
				Estoque obj = new Estoque();
				obj.setIdEntrada(rs.getInt("IdEntrada"));
				obj.setQuantidade(rs.getInt("Quantidade"));
				obj.setDataEntrada(rs.getString("DataEntrada"));
				obj.setValorUnitario(rs.getDouble("ValorUnitario"));
				obj.setNomeFornecedores(rs.getString("nomeF"));
				obj.setNomeProdutos(rs.getString("nomeP"));
				obj.setNomeFuncionario(rs.getString("NomeFunc"));
				
				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela Entrada no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Estoque acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc, e.IdProdutos as id  "
							+ "FROM estoqueentrada e " + "LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos "
							+ "LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores "
							+ "LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios "
							+ "WHERE e.IdEntrada = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				Estoque obj = new Estoque();
				obj.setIdEntrada(rs.getInt("IdEntrada"));
				obj.setQuantidade(rs.getInt("Quantidade"));
				obj.setDataEntrada(rs.getString("DataEntrada"));
				obj.setValorUnitario(rs.getDouble("ValorUnitario"));
				obj.setNomeFornecedores(rs.getString("nomeF"));
				obj.setNomeProdutos(rs.getString("nomeP"));
				obj.setNomeFuncionario(rs.getString("NomeFunc"));
				obj.setIdProdutos(rs.getInt("id"));


				return obj;

			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela Entrada no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Estoque> acharListPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc "
							+ "FROM estoqueentrada e " + "LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos "
							+ "LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores "
							+ "LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios "
							+ "WHERE p.Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			List<Estoque> list = new ArrayList<>();

			while (rs.next()) {
				Estoque obj = new Estoque();
				obj.setIdEntrada(rs.getInt("IdEntrada"));
				obj.setQuantidade(rs.getInt("Quantidade"));
				obj.setDataEntrada(rs.getString("DataEntrada"));
				obj.setValorUnitario(rs.getDouble("ValorUnitario"));
				obj.setNomeFornecedores(rs.getString("nomeF"));
				obj.setNomeProdutos(rs.getString("nomeP"));
				obj.setNomeFuncionario(rs.getString("NomeFunc"));

				list.add(obj);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela Entrada no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Estoque> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc "
							+ "FROM estoqueentrada e " + "LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos "
							+ "LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores "
							+ "LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios "
							+ "WHERE p.IdEntrada= ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			List<Estoque> list = new ArrayList<>();

			while (rs.next()) {

				Estoque obj = new Estoque();
				obj.setIdEntrada(rs.getInt("IdEntrada"));
				obj.setQuantidade(rs.getInt("Quantidade"));
				obj.setDataEntrada(rs.getString("DataEntrada"));
				obj.setValorUnitario(rs.getDouble("ValorUnitario"));
				obj.setNomeFornecedores(rs.getString("nomeF"));
				obj.setNomeProdutos(rs.getString("nomeP"));
				obj.setNomeFuncionario(rs.getString("NomeFunc"));
				list.add(obj);

			}

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela Entrada no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Estoque> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as nomeFunc "
							+ "FROM estoqueentrada e " + "LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos "
							+ "LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores "
							+ "LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios");
			rs = st.executeQuery();
			List<Estoque> listEntrada = new ArrayList<>();

			while (rs.next()) {

				Estoque obj = new Estoque();
				obj.setIdEntrada(rs.getInt("IdEntrada"));
				obj.setQuantidade(rs.getInt("Quantidade"));
				obj.setDataEntrada(rs.getString("DataEntrada"));
				obj.setValorUnitario(rs.getDouble("ValorUnitario"));
				obj.setNomeFornecedores(rs.getString("nomeF"));
				obj.setNomeProdutos(rs.getString("nomeP"));
				obj.setNomeFuncionario(rs.getString("nomeFunc"));

				listEntrada.add(obj);

			}

			return listEntrada;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela Entrada no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
