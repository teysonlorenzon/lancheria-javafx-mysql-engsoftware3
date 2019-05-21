package model.entidades;

import java.io.Serializable;

public class UsuariosLogin extends Pessoa implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String usuario;
	private String senha;
	private String nivel;
	

	public UsuariosLogin() {

	}


	public UsuariosLogin(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer id, String usuario, String senha, String nivel) {
		super(idPessoa, nome, cidade, endereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular);
		this.id = id;
		this.usuario = usuario;
		this.senha = senha;
		this.nivel = nivel;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
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
		return "UsuariosLogin [id=" + id + ", nome=" + usuario + ", senha=" + senha + ", nivel=" + nivel + "]";
	}

}
