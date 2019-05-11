package model.entidades;

import java.io.Serializable;

public class Produtos extends Categorias implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idProdutos;
	private String nomeProdutos;

	public Produtos() {
	}
	
	public Produtos(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer idCategorias, String nomeCategorias, Integer idProdutos, String nomeProdutos) {
		super(idPessoa, nome, cidade, endereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular, idCategorias, nomeCategorias);
		this.idProdutos = idProdutos;
		this.nomeProdutos = nomeProdutos;
	}


	public Integer getIdProdutos() {
		return idProdutos;
	}

	public void setIdProdutos(Integer idProdutos) {
		this.idProdutos = idProdutos;
	}

	public String getNomeProdutos() {
		return nomeProdutos;
	}

	public void setNomeProdutos(String nomeProdutos) {
		this.nomeProdutos = nomeProdutos;
	}

	@Override
	public String toString() {
		return "Produtos [idProdutos=" + idProdutos + ", nomeProdutos=" + nomeProdutos + "]";
	}

}
