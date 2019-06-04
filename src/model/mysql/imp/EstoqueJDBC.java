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
import model.mysql.EstoqueMYSQL;

public class EstoqueJDBC implements EstoqueMYSQL {

	private Connection conn; // seta conxão com banco

	public EstoqueJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Estoque> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT p.Nome as Produto, sum(e.Quantidade) as Quantidade " +
					"FROM estoque as e " +
					"LEFT JOIN produtos as p on e.IdProduto = p.IdProdutos " +
					"GROUP BY p.Nome");
			rs = st.executeQuery();
			List<Estoque> listEntrada = new ArrayList<>();

			while (rs.next()) {

				Estoque obj = new Estoque();
				obj.setNomeProdutos(rs.getString("Produto"));
				obj.setQuantidade(rs.getInt("Quantidade"));

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

	@Override
	public List<Estoque> acharListPorProduto(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT p.Nome as Produto, sum(e.Quantidade) as Quantidade " +
					"FROM estoque as e " +
					"LEFT JOIN produtos as p on e.IdProduto = p.IdProdutos " +
					"GROUP BY p.Nome " +
					"WHERE p.Nome = ?");
			st.setString(1, nome);
			rs = st.executeQuery();
			List<Estoque> listEntrada = new ArrayList<>();

			while (rs.next()) {

				Estoque obj = new Estoque();
				obj.setNomeProdutos(rs.getString("Produto"));
				obj.setQuantidade(rs.getInt("Quantidade"));

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
