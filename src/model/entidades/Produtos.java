package model.entidades;

import java.io.Serializable;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idProdutos;
	private String nome;
	private Integer idCategorias;
	private String nomeCategorias;

	public Produtos() {
	}

	public Produtos(String nome, Integer idCategorias, String nomeCategorias) {
		this.nome = nome;
		this.idCategorias = idCategorias;
		this.nomeCategorias = nomeCategorias;
	}

	public Integer getIdProdutos() {
		return idProdutos;
	}

	public void setIdProdutos(Integer idProdutos) {
		this.idProdutos = idProdutos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getIdCategorias() {
		return idCategorias;
	}

	public void setIdCategorias(Integer idCategorias) {
		this.idCategorias = idCategorias;
	}

	public String getNomeCategorias() {
		return nomeCategorias;
	}

	public void setNomeCategorias(String nomeCategorias) {
		this.nomeCategorias = nomeCategorias;
	}

	@Override
	public String toString() {
		return "Produtos [idProdutos=" + idProdutos + ", nome=" + nome + ", idCategorias=" + idCategorias
				+ ", nomeCategorias=" + nomeCategorias + "]";
	}

}
