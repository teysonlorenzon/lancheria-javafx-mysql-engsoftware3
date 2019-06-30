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
import model.entidades.Lanches;
import model.entidades.Produtos;
import model.entidades.Vendas;
import model.mysql.VendasMYSQL;
import model.servicos.CadastroLanchesServico;
import model.servicos.CadastroProdutosServico;

public class VendasJDBC implements VendasMYSQL {

	private Connection conn;
	private CadastroProdutosServico service = new CadastroProdutosServico();
	private CadastroLanchesServico sv = new CadastroLanchesServico();

	public VendasJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirVendas(Vendas obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO estoquesaida " + "(DataSaida, ValorUnitario, IdLanches, IdClientes, IdFuncionario, Descri) "
							+ "VALUES " + "(?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getDataSaida());
			st.setDouble(2, obj.getValorSaida());
			st.setInt(3, obj.getIdLancheSiada());
			
			try {
				st.setInt(4, obj.getidClienteSaida());
			} catch (NullPointerException e) {
				st.setInt(4, 1);
			}
			st.setInt(5, obj.getIdFuncionarioSaida());
			st.setString(6,obj.getDescriSaida());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdSaida(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

			st2 = conn.prepareStatement("UPDATE estoque " + "SET Quantidade = Quantidade - ? " + "WHERE IdProduto = ?");

			String stNome = obj.getDescricao();
			String stQuant = obj.getQuantidade();
			Produtos result = new Produtos();
			String[] stArrayNome = stNome.split(",");
			String[] stArrayQuant = stQuant.split(",");

			for (int i = 0; i < stArrayNome.length; i++) {
				result = service.buscarNome(stArrayNome[i]);
				st2.setInt(1, Integer.parseInt(stArrayQuant[i]));
				st2.setInt(2, result.getIdProdutos());
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
	public void deletarVendas(Integer id) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {

			st = conn.prepareStatement("DELETE FROM estoquesaida WHERE IdSaida = ?");
			st.setInt(1, id);

			Vendas obj = new Vendas();
			obj = acharPorId(id);
	

			
			
			String stNome = obj.getDescriSaida();
			stNome = stNome.replace(",", " ");
			String[] stSepara = stNome.split(" ");

			List<String> quant = new ArrayList<>();
			List<String> nome = new ArrayList<>();
			
			Integer posQuant = 0;
			Integer posNome = 1;
			for (int i = 0; i < stSepara.length; i++) {
				
				if (posQuant < stSepara.length) {
					quant.add(stSepara[posQuant]);
				}
				if (posNome < stSepara.length) {
					nome.add(stSepara[posNome]);
				}
				posQuant = posQuant + 2;
				posNome = posNome + 2;
			}
			
			

			Produtos result = new Produtos();
			st2 = conn.prepareStatement("UPDATE estoque " + "SET Quantidade = Quantidade + ? " + "WHERE IdProduto = ?");
			for (int i = 0; i < nome.size(); i++) {
				result = service.buscarNome(nome.get(i));
				st2.setInt(1, Integer.parseInt(quant.get(i)));
				st2.setInt(2, result.getIdProdutos());
				st2.executeUpdate();
			}

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public Vendas acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdSaida,l.Nome as Lanche, e.DataSaida, e.ValorUnitario, c.Nome as Cliente, f.Nome as Funcionario, e.IdLanches as id , e.Descri "
							+ "FROM estoquesaida as e " + "LEFT JOIN lanches l on e.IdLanches = l.IdLanches "
							+ "LEFT JOIN clientes c  on e.IdClientes = c.IdClientes "
							+ "LEFT JOIN funcionarios f on e.IdFuncionario = f.IdFuncionarios " + "WHERE e.IdSaida = ? "
							+ "ORDER BY e.IdSaida");
			st.setInt(1, id);
			rs = st.executeQuery();
			while (rs.next()) {
				Vendas obj = new Vendas();
				obj.setIdSaida(rs.getInt("IdSaida"));
				obj.setNomeLancheSaida(rs.getString("Lanche"));
				obj.setDataSaida(rs.getString("DataSaida"));
				obj.setValorSaida(rs.getDouble("ValorUnitario"));
				obj.setNomeClienteSaida(rs.getString("Cliente"));
				obj.setNomeFuncionarioSaida(rs.getString("Funcionario"));
				obj.setIdLancheSiada(rs.getInt("id"));
				obj.setDescriSaida(rs.getString("Descri"));

				return obj;
			}
			return null;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela vendas no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Vendas> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdSaida,l.Nome as Lanche, e.DataSaida, e.ValorUnitario, c.Nome as Cliente, f.Nome as Funcionario "
							+ "FROM estoquesaida as e " + "LEFT JOIN lanches l on e.IdLanches = l.IdLanches "
							+ "LEFT JOIN clientes c  on e.IdClientes = c.IdClientes "
							+ "LEFT JOIN funcionarios f on e.IdFuncionario = f.IdFuncionarios " + "WHERE e.IdSaida = ? "
							+ "ORDER BY e.IdSaida");
			st.setInt(1, id);
			rs = st.executeQuery();
			List<Vendas> list = new ArrayList<>();
			while (rs.next()) {

				Vendas obj = new Vendas();
				obj.setIdSaida(rs.getInt("IdSaida"));
				obj.setNomeLancheSaida(rs.getString("Lanche"));
				obj.setDataSaida(rs.getString("DataSaida"));
				obj.setValorSaida(rs.getDouble("ValorUnitario"));
				obj.setNomeClienteSaida(rs.getString("Cliente"));
				obj.setNomeFuncionarioSaida(rs.getString("Funcionario"));

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
	public List<Vendas> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT e.IdSaida,l.Nome as Lanche, e.DataSaida, e.ValorUnitario, c.Nome as Cliente, f.Nome as Funcionario "
							+ "FROM estoquesaida as e " + "LEFT JOIN lanches l on e.IdLanches = l.IdLanches "
							+ "LEFT JOIN clientes c  on e.IdClientes = c.IdClientes "
							+ "LEFT JOIN funcionarios f on e.IdFuncionario = f.IdFuncionarios " + "ORDER BY e.IdSaida");

			rs = st.executeQuery();
			List<Vendas> list = new ArrayList<>();
			while (rs.next()) {

				Vendas obj = new Vendas();
				obj.setIdSaida(rs.getInt("IdSaida"));
				obj.setNomeLancheSaida(rs.getString("Lanche"));
				obj.setDataSaida(rs.getString("DataSaida"));
				obj.setValorSaida(rs.getDouble("ValorUnitario"));
				obj.setNomeClienteSaida(rs.getString("Cliente"));
				obj.setNomeFuncionarioSaida(rs.getString("Funcionario"));

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
