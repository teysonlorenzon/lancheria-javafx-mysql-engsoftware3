package model.entidades;

import java.io.Serializable;

public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idCategorias;
	private String nomeCategorias;
	
	public Categorias(){
		
	}

	public Categorias(Integer idCategorias, String nomeCategorias) {
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
