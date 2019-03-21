package model.entidades;

import java.io.Serializable;

public class UsuariosLogin implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String usuario;
	private String senha;
	private Integer grau;

	public UsuariosLogin() {

	}

	public UsuariosLogin(Integer id, String nome, String senha, Integer grau) {
		this.id = id;
		this.usuario = nome;
		this.senha = senha;
		this.grau = grau;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getGrau() {
		return grau;
	}

	public void setGrau(Integer grau) {
		this.grau = grau;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuariosLogin other = (UsuariosLogin) obj;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuariosLogin [id=" + id + ", nome=" + usuario + ", senha=" + senha + ", grau=" + grau + "]";
	}

}
