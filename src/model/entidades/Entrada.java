package model.entidades;

import java.io.Serializable;

public class Entrada extends Produtos implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idEntrada;
	private Double valorUnitario;
	private String dataEntrada;

	public Entrada() {

	}

	public Entrada(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer idCategorias, String nomeCategorias, Integer idProdutos, String nome2, Integer idEntrada,
			Double valorUnitario, String dataEntrada) {
		super(idPessoa, nome, cidade, endereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular, idCategorias, nomeCategorias, idProdutos, nome2);
		this.idEntrada = idEntrada;
		this.valorUnitario = valorUnitario;
		this.dataEntrada = dataEntrada;
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

	@Override
	public String toString() {
		return "Entrada [idEntrada=" + idEntrada + ", valorUnitario=" + valorUnitario + ", dataEntrada=" + dataEntrada
				+ "]";
	}

}
