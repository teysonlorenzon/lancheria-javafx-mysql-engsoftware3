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
import model.entidades.Entrada;
import model.mysql.CadastroEntradaMYSQL;

public class CadastroEntradaJDBC implements CadastroEntradaMYSQL {

	private Connection conn; // seta conxão com banco

	public CadastroEntradaJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirEntrada(Entrada obj ) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO estoqueentrada " + "(DataEntrada, ValorUnitario, Quantidade, IdProdutos, IdFornecedores, IdFuncionarios) " + "VALUES " + "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

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

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void atualizarEntrada(Entrada obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("UPDATE estoqueentrada " + "SET DataEntrada = ?, ValorUnitario = ?, Quantidade = ?, IdProdutos = ?, IdFornecedores = ?, IdFuncionarios = ? " + 
			"WHERE IdEntrada = ?");

			st.setString(1, obj.getDataEntrada());
			st.setDouble(2, obj.getValorUnitario());
			st.setInt(3, obj.getQuantidade());
			st.setInt(4, obj.getIdProdutos());
			st.setInt(5, obj.getIdFornecedores());
			st.setInt(6, obj.getIdFuncionario());
			st.setInt(7, obj.getIdEntrada());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarEntrada(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM estoqueentrada WHERE IdEntrada= ?");
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Entrada acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc " + 
					"FROM estoqueentrada e " +
					"LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos " +
					"LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores " +
					"LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios " +
					"WHERE e.Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {
				Entrada obj = new Entrada();
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
	public Entrada acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc " + 
					"FROM estoqueentrada e " +
					"LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos " +
					"LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores " +
					"LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios " + 
					"WHERE e.IdEntrada = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				Entrada obj = new Entrada();
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
	public List<Entrada> acharListPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc " + 
					"FROM estoqueentrada e " +
					"LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos " +
					"LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores " +
					"LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios " + "WHERE p.Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			List<Entrada> list = new ArrayList<>();

			while (rs.next()) {
				Entrada obj = new Entrada();
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
	public List<Entrada> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as NomeFunc " + 
					"FROM estoqueentrada e " +
					"LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos " +
					"LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores " +
					"LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios " + 
					"WHERE p.IdEntrada= ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			List<Entrada> list = new ArrayList<>();

			while (rs.next()) {

				Entrada obj = new Entrada();
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
	public List<Entrada> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT e.IdEntrada, e.DataEntrada, e.ValorUnitario, e.Quantidade, p.Nome as nomeP, f.Nome as nomeF , func.Nome as nomeFunc " + 
					"FROM estoqueentrada e " +
					"LEFT JOIN produtos p on p.IdProdutos = e.IdProdutos " +
					"LEFT JOIN fornecedores f on f.IdFornecedores = e.IdFornecedores " +
					"LEFT JOIN funcionarios func on func.IdFuncionarios = e.IdFuncionarios");
			rs = st.executeQuery();
			List<Entrada> listEntrada = new ArrayList<>();

			while (rs.next()) {

				Entrada obj = new Entrada();
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
