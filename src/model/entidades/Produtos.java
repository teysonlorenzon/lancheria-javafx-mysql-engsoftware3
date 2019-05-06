package model.entidades;

import java.io.Serializable;

public class Produtos extends Categorias implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idProdutos;
	private String nome;


	public Produtos() {
	}





	public Produtos(Integer idCategorias, String nomeCategorias, Integer idProdutos, String nome) {
		super(idCategorias, nomeCategorias);
		this.idProdutos = idProdutos;
		this.nome = nome;
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



	@Override
	public String toString() {
		return "Produtos [idProdutos=" + idProdutos + ", nome=" + nome + "]";
	}



	

}
