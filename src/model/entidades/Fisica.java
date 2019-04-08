package model.entidades;

import java.io.Serializable;
import java.util.Date;

public class Fisica extends Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer idFisica;
	private String cpf;
	private String rg;
	private String dataNascimento;
	
	public Fisica() {
		
	}
	
	public Fisica(Integer idPessoa, String nome, String cidade, String edereco, Integer numero, String bairro, String cep,
			String uf, String complemento, String email, String telefoneFixo, String telefoneCelular, Integer idFisica, String cpf,
			String rg, String dataNascimento) {
		super(idPessoa, nome, cidade, edereco, numero, bairro, cep, uf, complemento, email, telefoneFixo, telefoneCelular);

		this.idFisica = idFisica;
		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdFisica() {
		return idFisica;
	}

	public void setIdFisica(Integer idFisica) {
		this.idFisica = idFisica;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Fisica [idFisica=" + idFisica + ", cpf=" + cpf + ", rg=" + rg + ", dataNascimento=" + dataNascimento
				+ "]";
	}
	


	

}
