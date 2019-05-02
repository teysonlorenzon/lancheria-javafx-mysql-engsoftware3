package model.entidades;

import java.io.Serializable;

public class Produtos implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idProdutos;
	private String nome;
	private Integer quantidade;
	private Integer idFornecedores;
	private Integer idCategorias;
	private String nomeFornecedores;
	private String nomeCategorias;

	public Produtos() {
	}

	public Produtos(String nome, Integer quantidade, Integer idFornecedores, Integer idCategorias) {
		this.nome = nome;
		this.quantidade = quantidade;
		this.idFornecedores = idFornecedores;
		this.idCategorias = idCategorias;
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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Integer getIdFornecedores() {
		return idFornecedores;
	}

	public void setIdFornecedores(Integer idFornecedores) {
		this.idFornecedores = idFornecedores;
	}

	public Integer getIdCategorias() {
		return idCategorias;
	}

	public void setIdCategorias(Integer idCategorias) {
		this.idCategorias = idCategorias;
	}

	public String getNomeFornecedores() {
		return nomeFornecedores;
	}

	public void setNomeFornecedores(String nomeFornecedores) {
		this.nomeFornecedores = nomeFornecedores;
	}

	public String getNomeCategorias() {
		return nomeCategorias;
	}

	public void setNomeCategorias(String nomeCategorias) {
		this.nomeCategorias = nomeCategorias;
	}

	@Override
	public String toString() {
		return "Produtos [idProdutos=" + idProdutos + ", nome=" + nome + ", quantidade=" + quantidade
				+ ", idFornecedores=" + idFornecedores + ", idCategorias=" + idCategorias + ", nomeFornecedores="
				+ nomeFornecedores + ", nomeCategorias=" + nomeCategorias + "]";
	}

	

}
