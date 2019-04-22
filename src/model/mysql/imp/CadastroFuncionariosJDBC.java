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
import model.entidades.Fisica;
import model.entidades.Pessoa;
import model.mysql.CadastroFucionariosMYSQL;

public class CadastroFuncionariosJDBC implements CadastroFucionariosMYSQL {
	private Connection conn; // seta conxão com banco

	public CadastroFuncionariosJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void inserirPessoaFisica(Fisica obj) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO funcionarios "
					+ "(Nome, Cidade, Cep, Uf, Bairro, Endereco, Numero, TelefoneFixo, TelefoneCelular, Complemento, Email, Cpf, Rg, DataNascimento) "
					+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

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
			st.setString(12, obj.getCpf());
			st.setString(13, obj.getRg());
			st.setString(14, obj.getDataNascimento());

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
		}

	}

	@Override
	public void atualizarPessoaFisica(Fisica obj) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("UPDATE funcionarios "
					+ "SET Nome = ?, Cidade = ?, Cep = ?, Uf = ?, Bairro = ?, Endereco = ?, Numero = ?, TelefoneFixo = ?, TelefoneCelular = ?, Complemento = ?, Email = ?, Cpf = ?, Rg = ?, DataNascimento = ? "
					+ "WHERE IdFuncionarios = ?");

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
			st.setString(12, obj.getCpf());
			st.setString(13, obj.getRg());
			st.setString(14, obj.getDataNascimento());
			st.setInt(15, obj.getIdPessoa());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletarFuncionarios(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM funcionarios WHERE IdFuncionarios = ?");
			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Pessoa acharPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM funcionarios " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			while (rs.next()) {

				Fisica obj = new Fisica();
				obj.setIdPessoa(rs.getInt("IdFuncionarios"));
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
				obj.setRg(rs.getString("Rg"));
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
	public Pessoa acharPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM funcionarios " + "WHERE IdFuncionarios = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			while (rs.next()) {

				Fisica obj = new Fisica();
				obj.setIdPessoa(rs.getInt("IdFuncionarios"));
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
				obj.setRg(rs.getString("Rg"));
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
	public List<Pessoa> acharCPF(String cpf) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM funcionarios " + "WHERE Cpf = ?");

			st.setString(1, cpf);
			rs = st.executeQuery();

			List<Fisica> listF = new ArrayList<>();
			while (rs.next()) {
				Fisica obj = new Fisica();
				obj.setIdPessoa(rs.getInt("IdFuncionarios"));
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
				obj.setRg(rs.getString("Rg"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setDataNascimento(rs.getString("DataNascimento"));

				listF.add(obj);
			}

			List<Pessoa> list = new ArrayList<>(listF);

			return list;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Pessoa> acharTudo() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM funcionarios");
			rs = st.executeQuery();

			List<Pessoa> listFisica = new ArrayList<>();

			while (rs.next()) {

				Fisica obj = new Fisica();
				obj.setIdPessoa(rs.getInt("IdFuncionarios"));
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
				obj.setRg(rs.getString("Rg"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setDataNascimento(rs.getString("DataNascimento"));

				listFisica.add(obj);
			}
			return listFisica;

		} catch (

		SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Pessoa> acharListPorNome(String nome) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM funcionarios " + "WHERE Nome = ?");

			st.setString(1, nome);
			rs = st.executeQuery();

			List<Pessoa> listFisica = new ArrayList<>();

			while (rs.next()) {

				Fisica obj = new Fisica();
				obj.setIdPessoa(rs.getInt("IdFuncionarios"));
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
				obj.setRg(rs.getString("Rg"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setDataNascimento(rs.getString("DataNascimento"));
				listFisica.add(obj);

			}
			return listFisica;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

	@Override
	public List<Pessoa> acharListPorId(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM funcionarios " + "WHERE IdFuncionarios = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			List<Pessoa> listFisica = new ArrayList<>();

			while (rs.next()) {

				Fisica obj = new Fisica();
				obj.setIdPessoa(rs.getInt("IdFuncionarios"));
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
				obj.setRg(rs.getString("Rg"));
				obj.setCpf(rs.getString("Cpf"));
				obj.setDataNascimento(rs.getString("DataNascimento"));
				listFisica.add(obj);

			}

			return listFisica;

		} catch (SQLException e) {
			throw new DbException("Erro ao buscar Lista de Dados da tabela clientes no banco");
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
	}

}
