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
import itg.CadastroClientesTelaListControladora;
import model.entidades.Fisica;
import model.entidades.Juridica;
import model.entidades.Pessoa;
import model.mysql.CadastroClientesMYSQL;

public class CadastroClientesJDBC implements CadastroClientesMYSQL {

	private Connection conn; // seta conxão com banco

	public CadastroClientesJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirPessoaFisicaJuridica(Fisica obj, Juridica obj2) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			st = conn.prepareStatement("INSERT INTO clientes "
					+ "(Nome, Cidade, Cep, Uf, Bairro, Endereco, Numero, TelefoneFixo, TelefoneCelular, Complemento, Email, Cpf, Rg, DataNascimento, Cnpj, NomeFantasia) "
					+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getNome());
			st.setString(2, obj.getCidade());
			st.setString(3, obj.getCep());
			st.setString(4, obj.getUf());
			st.setString(5, obj.getBairro());
			st.setString(6, obj.getEndereco());
			st.setInt(7, obj.getNumero());
			st.setString(8, obj.getTelefoneFixo());
			st.setString(9, obj.getTelefoneCelular());
			st.setString(10, obj.getComplemento());
			st.setString(11, obj.getEmail());
			st.setString(1, obj.getCpf());
			st.setInt(2, obj.getRg());
			st.setString(3, obj.getDataNascimento());
			st.setString(1, obj2.getCnpj());
			st.setString(2, obj2.getNomeFantasia());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdPessoa(id);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public void atualizar(Pessoa obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletarPorId(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Pessoa acharPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pessoa> acharTudoFisica() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clientes ORDER BY Nome");
			rs = st.executeQuery();

			List<Pessoa> listFisica = new ArrayList<>();

			while (rs.next()) {
				Fisica obj = new Fisica();
				Juridica obj2 = new Juridica();
				obj.setIdPessoa(rs.getInt("IdClientes"));
				obj.setNome(rs.getString("Nome"));
				obj.setCidade(rs.getString("Cidade"));
				obj.setCep(rs.getString("Cep"));
				obj.setUf(rs.getString("Uf"));
				obj.setBairro(rs.getString("Bairro"));
				obj.setEndereco(rs.getString("Endereco"));
				obj.setNumero(rs.getInt("Numero"));
				obj.setTelefoneFixo(rs.getString("TelefoneFixo"));
				obj.setTelefoneCelular(rs.getString("TelefoneCelular"));
				obj.setComplemento(rs.getString("Complemento"));
				obj.setEmail(rs.getString("Email"));
				obj.setRg(rs.getInt("Rg"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setDataNascimento(rs.getString("DataNascimento"));

				listFisica.add(obj);
			}
			;

			return listFisica;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Pessoa> acharTudoJuridica() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clientes ORDER BY Nome");
			rs = st.executeQuery();

			List<Pessoa> listJuridica = new ArrayList<>();

			while (rs.next()) {
				Juridica obj = new Juridica();
				obj.setIdPessoa(rs.getInt("IdClientes"));
				obj.setNome(rs.getString("Nome"));
				obj.setCidade(rs.getString("Cidade"));
				obj.setCep(rs.getString("Cep"));
				obj.setUf(rs.getString("Uf"));
				obj.setBairro(rs.getString("Bairro"));
				obj.setEndereco(rs.getString("Endereco"));
				obj.setNumero(rs.getInt("Numero"));
				obj.setTelefoneFixo(rs.getString("TelefoneFixo"));
				obj.setTelefoneCelular(rs.getString("TelefoneCelular"));
				obj.setComplemento(rs.getString("Complemento"));
				obj.setEmail(rs.getString("Email"));
				obj.setNomeFantasia(rs.getString("NomeFantasia"));
				obj.setCnpj(rs.getString("Cnpj"));

				listJuridica.add(obj);

			}
			;
			return listJuridica;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

}
