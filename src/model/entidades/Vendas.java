package model.entidades;

import java.io.Serializable;

public class Vendas extends Lanches implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idSaida;
	private String dataSaida;
	private Double valorSaida;
	private Integer idLancheSiada;
	private Integer idClienteSaida;
	private Integer idFuncionarioSaida;
	private String nomeLancheSaida;
	private String nomeClienteSaida;
	private String nomeFuncionarioSaida;
	private String descriSaida;
	
	

	public Vendas() {
	}


	public Vendas(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer idLanches, String nomeLanches, String descricao, String linkImgLanche, Double valorLanche,
			String quantidade, Integer idSaida, String dataSaida, Double valorSaida, Integer idLancheSiada,
			Integer idClienteSaida, Integer idFuncionarioSaida, String nomeLancheSaida, String nomeClienteSaida,
			String nomeFuncionarioSaida, String descriSaida) {
		super(idPessoa, nome, cidade, endereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular, idLanches, nomeLanches, descricao, linkImgLanche, valorLanche, quantidade);
		this.idSaida = idSaida;
		this.dataSaida = dataSaida;
		this.valorSaida = valorSaida;
		this.idLancheSiada = idLancheSiada;
		this.idClienteSaida = idClienteSaida;
		this.idFuncionarioSaida = idFuncionarioSaida;
		this.nomeLancheSaida = nomeLancheSaida;
		this.nomeClienteSaida = nomeClienteSaida;
		this.nomeFuncionarioSaida = nomeFuncionarioSaida;
		this.descriSaida = descriSaida;
	}




	public Integer getIdSaida() {
		return idSaida;
	}


	public void setIdSaida(Integer idSaida) {
		this.idSaida = idSaida;
	}

	public String getDescriSaida() {
		return descriSaida;
	}


	public void setDescriSaida(String descriSaida) {
		this.descriSaida = descriSaida;
	}

	public String getDataSaida() {
		return dataSaida;
	}


	public void setDataSaida(String dataSaida) {
		this.dataSaida = dataSaida;
	}


	public Double getValorSaida() {
		return valorSaida;
	}


	public void setValorSaida(Double valorSaida) {
		this.valorSaida = valorSaida;
	}


	public Integer getIdLancheSiada() {
		return idLancheSiada;
	}


	public void setIdLancheSiada(Integer idLancheSiada) {
		this.idLancheSiada = idLancheSiada;
	}


	public Integer getidClienteSaida() {
		return idClienteSaida;
	}


	public void setidClienteSaida(Integer idClienteSaida) {
		this.idClienteSaida = idClienteSaida;
	}


	public Integer getIdFuncionarioSaida() {
		return idFuncionarioSaida;
	}


	public void setIdFuncionarioSaida(Integer idFuncionarioSaida) {
		this.idFuncionarioSaida = idFuncionarioSaida;
	}


	public String getNomeLancheSaida() {
		return nomeLancheSaida;
	}


	public void setNomeLancheSaida(String nomeLancheSaida) {
		this.nomeLancheSaida = nomeLancheSaida;
	}


	public String getNomeClienteSaida() {
		return nomeClienteSaida;
	}


	public void setNomeClienteSaida(String nomeClienteSaida) {
		this.nomeClienteSaida = nomeClienteSaida;
	}


	public String getNomeFuncionarioSaida() {
		return nomeFuncionarioSaida;
	}


	public void setNomeFuncionarioSaida(String nomeFuncionarioSaida) {
		this.nomeFuncionarioSaida = nomeFuncionarioSaida;
	}


	@Override
	public String toString() {
		return "Vendas [idSaida=" + idSaida + ", dataSaida=" + dataSaida + ", valorSaida=" + valorSaida
				+ ", idLancheSiada=" + idLancheSiada + ", idClienteSaida=" + idClienteSaida + ", idFuncionarioSaida="
				+ idFuncionarioSaida + ", nomeLancheSaida=" + nomeLancheSaida + ", nomeClienteSaida=" + nomeClienteSaida
				+ ", nomeFuncionarioSaida=" + nomeFuncionarioSaida + ", descriSaida=" + descriSaida +"]";
	}
	

	
	
	
}

	