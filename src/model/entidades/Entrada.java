package model.entidades;

import java.io.Serializable;

public class Entrada  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idEntrada;
	private Double valorUnitario;
	private String dataEntrada;
	private Integer quantidade;
	private Integer idProdutos;
	private Integer idFornecedores;
	private String nomeProdutos;
	private String nomeFornecedores;


	public Entrada() {

	}


	public Entrada(Integer idEntrada, Double valorUnitario, String dataEntrada, Integer quantidade, Integer idProdutos,
			Integer idFornecedores, String nomeProdutos, String nomeFornecedores) {
		this.idEntrada = idEntrada;
		this.valorUnitario = valorUnitario;
		this.dataEntrada = dataEntrada;
		this.quantidade = quantidade;
		this.idProdutos = idProdutos;
		this.idFornecedores = idFornecedores;
		this.nomeProdutos = nomeProdutos;
		this.nomeFornecedores = nomeFornecedores;
	}


	public Integer getIdEntrada() {
		return idEntrada;
	}


	public void setIdEntrada(Integer idEntrada) {
		this.idEntrada = idEntrada;
	}


	public Double getValorUnitario() {
		return valorUnitario;
	}


	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}


	public String getDataEntrada() {
		return dataEntrada;
	}


	public void setDataEntrada(String dataEntrada) {
		this.dataEntrada = dataEntrada;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public Integer getIdProdutos() {
		return idProdutos;
	}


	public void setIdProdutos(Integer idProdutos) {
		this.idProdutos = idProdutos;
	}


	public Integer getIdFornecedores() {
		return idFornecedores;
	}


	public void setIdFornecedores(Integer idFornecedores) {
		this.idFornecedores = idFornecedores;
	}


	public String getNomeProdutos() {
		return nomeProdutos;
	}


	public void setNomeProdutos(String nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}


	public String getNomeFornecedores() {
		return nomeFornecedores;
	}


	public void setNomeFornecedores(String nomeFornecedores) {
		this.nomeFornecedores = nomeFornecedores;
	}


	@Override
	public String toString() {
		return "Entrada [idEntrada=" + idEntrada + ", valorUnitario=" + valorUnitario + ", dataEntrada=" + dataEntrada
				+ ", quantidade=" + quantidade + ", idProdutos=" + idProdutos + ", idFornecedores=" + idFornecedores
				+ ", nomeProdutos=" + nomeProdutos + ", nomeFornecedores=" + nomeFornecedores + "]";
	}



}