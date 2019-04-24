package model.entidades;

import java.io.Serializable;

public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idCategorias;
	private String nome;
	
	public Categorias(){
		
	}

	public Categorias(Integer idCategorias, String nome) {
		this.idCategorias = idCategorias;
		this.nome = nome;
	}

	public Integer getIdCategorias() {
		return idCategorias;
	}

	public void setIdCategorias(Integer idCategorias) {
		this.idCategorias = idCategorias;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Categorias [idCategorias=" + idCategorias + ", nome=" + nome + "]";
	}
	
	

}
