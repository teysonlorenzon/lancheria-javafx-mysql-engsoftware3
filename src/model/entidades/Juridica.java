package model.entidades;

import java.io.Serializable;

public class Juridica extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer IdJuridica;
	private String cnpj;
	private String nomeFantasia;
	
	

	public Juridica() {

	}

	public Juridica(Integer idPessoa, String nome, String cidade, String edereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer idJuridica, String cnpj, String nomeFantasia) {
		super(idPessoa, nome, cidade, edereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular);
		this.IdJuridica = IdJuridica;
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
	}

	public Integer getIdJuridica() {
		return IdJuridica;
	}

	public void setIdJuridica(Integer idJuridica) {
		IdJuridica = idJuridica;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	@Override
	public String toString() {
		return "Juridica [IdJuridica=" + IdJuridica + ", cnpj=" + cnpj + ", nomeFantasia=" + nomeFantasia + "]";
	}



}
