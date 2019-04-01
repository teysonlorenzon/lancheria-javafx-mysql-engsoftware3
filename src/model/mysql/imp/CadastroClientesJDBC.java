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

	public void inserirPessoaFisica(Fisica obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			st = conn.prepareStatement("INSERT INTO clientes "
					+ "(Nome, Cidade, Cep, Uf, Bairro, Endereco, Numero, TelefoneFixo, TelefoneCelular, Complemento, Email) "
					+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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

			st2 = conn.prepareStatement(
					"INSERT INTO fisico " + "(Cpf, Rg, DataNascimento, IdClientes) " + "VALUES " + "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st2.setString(1, obj.getCpf());
			st2.setInt(2, obj.getRg());
			st2.setString(3, obj.getDataNascimento());
			st2.setInt(4, obj.getIdPessoa());

			int rowsAffected2 = st2.executeUpdate();

			if (rowsAffected2 > 0) {
				ResultSet rs = st2.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdFisica(id);
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
	public void inserirPessoaJuridica(Juridica obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;

		try {
			st = conn.prepareStatement("INSERT INTO clientes "
					+ "(Nome, Cidade, Cep, Uf, Bairro, Endereco, Numero, TelefoneFixo, TelefoneCelular, Complemento, Email) "
					+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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

			st2 = conn.prepareStatement(
					"INSERT INTO juridico " + "(Cnpj, NomeFantasia, IdClientes) " + "VALUES " + "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st2.setString(1, obj.getCnpj());
			st2.setString(2, obj.getNomeFantasia());
			st2.setInt(3, obj.getIdPessoa());

			int rowsAffected2 = st2.executeUpdate();

			if (rowsAffected2 > 0) {
				ResultSet rs = st2.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdJuridica(id);
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
	public void atualizarPessoaFisica(Fisica obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {

			st = conn.prepareStatement(
					"UPDATE fisico " + "SET Cpf = ?, Rg = ?, DataNascimento = ? " + "WHERE IdClientes = ?");

			st.setString(1, obj.getCpf());
			st.setInt(2, obj.getRg());
			st.setString(3, obj.getDataNascimento());
			st.setInt(4, obj.getIdPessoa());
			st.executeUpdate();
			
			
			
			st2 = conn.prepareStatement("UPDATE clientes "
					+ "SET Nome = ?, Cidade = ?, Cep = ?, Uf = ?, Bairro = ?, Endereco = ?, Numero = ?, TelefoneFixo = ?, TelefoneCelular = ?, Complemento = ?, Email = ?"
					+ "WHERE idClientes = ? ");

			st2.setString(1, obj.getNome());
			st2.setString(2, obj.getCidade());
			st2.setString(3, obj.getCep());
			st2.setString(4, obj.getUf());
			st2.setString(5, obj.getBairro());
			st2.setString(6, obj.getEndereco());
			st2.setInt(7, obj.getNumero());
			st2.setString(8, obj.getTelefoneFixo());
			st2.setString(9, obj.getTelefoneCelular());
			st2.setString(10, obj.getComplemento());
			st2.setString(11, obj.getEmail());
			st2.setInt(12, obj.getIdPessoa());
			st2.executeUpdate();


		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public void atualizarPessoaJuridica(Juridica obj) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {

			st = conn.prepareStatement("UPDATE juridico " + "SET Cnpj = ?, NomeFantasia = ? " + "WHERE IdClientes = ?");

			st.setString(1, obj.getCnpj());
			st.setString(2, obj.getNomeFantasia());
			st.setInt(4, obj.getIdPessoa());
			st.executeUpdate();
			
			
			st2 = conn.prepareStatement("UPDATE clientes "
					+ "SET Nome = ?, Cidade = ?, Cep = ?, Uf = ?, Bairro = ?, Endereco = ?, Numero = ?, TelefoneFixo = ?, TelefoneCelular = ?, Complemento = ?, Email = ?"
					+ "WHERE idClientes = ? ");

			st2.setString(1, obj.getNome());
			st2.setString(2, obj.getCidade());
			st2.setString(3, obj.getCep());
			st2.setString(4, obj.getUf());
			st2.setString(5, obj.getBairro());
			st2.setString(6, obj.getEndereco());
			st2.setInt(7, obj.getNumero());
			st2.setString(8, obj.getTelefoneFixo());
			st2.setString(9, obj.getTelefoneCelular());
			st2.setString(10, obj.getComplemento());
			st2.setString(11, obj.getEmail());
			st2.setInt(12, obj.getIdPessoa());
			st2.executeUpdate();


		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeStatement(st2);
		}

	}

	@Override
	public void deletarPorIdFisica(Integer id) {
		PreparedStatement st = null;
		PreparedStatement st2 = null;
		try {
			st = conn.prepareStatement("DELETE FROM fisico WHERE IdClientes = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
			st2 = conn.prepareStatement("DELETE FROM clientes WHERE IdClientes= ?");
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
	public void deletarPorIdJuridica(Integer id) {
		PreparedStatement st = null;
		PreparedStatement  st2 = null;
		try {
			st = conn.prepareStatement("DELETE FROM juridico WHERE IdClientes = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
			st2 = conn.prepareStatement("DELETE FROM clientes WHERE IdClientes = ?");
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
	public Fisica acharPorNomeFisica(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cli.IdClientes, cli.Nome, cli.Cidade, cli.Cep, cli.Uf, cli.Bairro, cli.Endereco, cli.Numero, cli.TelefoneFixo, cli.TelefoneCelular, cli.Complemento, cli.Email, clif.Cpf, clif.Rg, clif.DataNascimento "
							+ "FROM clientes as cli " + "INNER JOIN fisico as clif "
							+ "on cli.IdClientes = clif.IdClientes " + "WHERE Nome = ?");
			
			st.setString(1, nome);
			rs = st.executeQuery();
	

			while (rs.next()) {
				Fisica obj = new Fisica();
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
	public Juridica acharPorNomeJuridica(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cli.IdClientes, cli.Nome, cli.Cidade, cli.Cep, cli.Uf, cli.Bairro, cli.Endereco, cli.Numero, cli.TelefoneFixo, cli.TelefoneCelular, cli.Complemento, cli.Email, clij.Cnpj, clij.NomeFantasia "
							+ "FROM clientes as cli " + "INNER JOIN juridico as clij "
							+ "on cli.IdClientes = clij.IdClientes " + "WHERE Nome = ?");
			st.setString(1, nome);
			rs = st.executeQuery();


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
	public List<Pessoa> acharTudoFisica() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT cli.IdClientes, cli.Nome, cli.Cidade, cli.Cep, cli.Uf, cli.Bairro, cli.Endereco, cli.Numero, cli.TelefoneFixo, cli.TelefoneCelular, cli.Complemento, cli.Email, clif.Cpf, clif.Rg, clif.DataNascimento "
							+ "FROM clientes as cli " + "INNER JOIN fisico as clif "
							+ "on cli.IdClientes = clif.IdClientes " + "ORDER BY Nome");
			rs = st.executeQuery();

			List<Pessoa> listFisica = new ArrayList<>();

			while (rs.next()) {
				Fisica obj = new Fisica();
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
			st = conn.prepareStatement(
					"SELECT cli.IdClientes, cli.Nome, cli.Cidade, cli.Cep, cli.Uf, cli.Bairro, cli.Endereco, cli.Numero, cli.TelefoneFixo, cli.TelefoneCelular, cli.Complemento, cli.Email, clij.Cnpj, clij.NomeFantasia "
							+ "FROM clientes as cli " + "INNER JOIN juridico as clij "
							+ "on cli.IdClientes = clij.IdClientes " + "ORDER BY Nome");
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
