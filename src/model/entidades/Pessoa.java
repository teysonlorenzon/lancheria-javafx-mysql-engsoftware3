package model.entidades;

import java.io.Serializable;

public class Pessoa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idPessoa;
	private String nome;
	private String cidade;
	private String endereco;
	private Integer numero;
	private String bairro;
	private String cep;
	private String uf;
	private String complemento;
	private String email;
	private String  telefoneFixo;
	private String telefoneCelular;
	
	public Pessoa() {
		
	}
	
	public Pessoa(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro, String cep, String uf,
			String complemento, String email, String telefoneFixo, String telefoneCelular) {
		this.idPessoa = idPessoa;
		this.nome = nome;
		this.cidade = cidade;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.uf = uf;
		this.complemento = complemento;
		this.email = email;
		this.telefoneFixo = telefoneFixo;
		this.telefoneCelular = telefoneCelular;
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneFixo() {
		return telefoneFixo;
	}

	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [idPessoa=" + idPessoa + ", nome=" + nome + ", cidade=" + cidade + ", endereco=" + endereco + ", numero="
				+ numero + ", bairro=" + bairro + ", cep=" + cep + ", uf=" + uf + ", complemento=" + complemento
				+ ", email=" + email + ", telefoneFixo=" + telefoneFixo + ", telefoneCelular=" + telefoneCelular + "]";
	}
	

}
