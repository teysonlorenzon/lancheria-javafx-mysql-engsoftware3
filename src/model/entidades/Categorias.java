package model.entidades;

import java.io.Serializable;

public class Categorias extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idCategorias;
	private String nomeCategorias;
	
	public Categorias(){
		
	}

	public Categorias(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer idCategorias, String nomeCategorias) {
		super(idPessoa, nome, cidade, endereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular);
		this.idCategorias = idCategorias;
		this.nomeCategorias = nomeCategorias;
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
		return "Categorias [idCategorias=" + idCategorias + ", nomeCategorias=" + nomeCategorias + "]";
	}
	
	

}
