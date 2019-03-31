package model.entidades;

import java.io.Serializable;
import java.util.Date;

public class Fisica extends Pessoa implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cpf;
	private Integer rg;
	private String dataNascimento;
	
	public Fisica() {
		
	}
	
	public Fisica(Integer idPessoa, String nome, String cidade, String edereco, Integer numero, String bairro, String cep,
			String uf, String complemento, String email, String telefoneFixo, String telefoneCelular, Integer id, String cpf,
			Integer rg, String dataNascimento) {
		super(idPessoa, nome, cidade, edereco, numero, bairro, cep, uf, complemento, email, telefoneFixo, telefoneCelular);

		this.cpf = cpf;
		this.rg = rg;
		this.dataNascimento = dataNascimento;
	}
	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getRg() {
		return rg;
	}

	public void setRg(Integer rg) {
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
		return "Fisica [cpf=" + cpf + ", rg=" + rg + ", dataNascimento=" + dataNascimento + "]";
	}
	
	

}
