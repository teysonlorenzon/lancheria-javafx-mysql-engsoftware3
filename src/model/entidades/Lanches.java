package model.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lanches extends Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer idLanches;
	private String nomeLanches;
	private String descricao;
	private String linkImgLanche;
	private Double valorLanche;
	private String quantidade;

	public Lanches() {

	}

	public Lanches(Integer idPessoa, String nome, String cidade, String endereco, Integer numero, String bairro,
			String cep, String uf, String complemento, String email, String telefoneFixo, String telefoneCelular,
			Integer idLanches, String nomeLanches, String descricao, String linkImgLanche, Double valorLanche,
			String quantidade) {
		super(idPessoa, nome, cidade, endereco, numero, bairro, cep, uf, complemento, email, telefoneFixo,
				telefoneCelular);
		this.idLanches = idLanches;
		this.nomeLanches = nomeLanches;
		this.descricao = descricao;
		this.linkImgLanche = linkImgLanche;
		this.valorLanche = valorLanche;
		this.quantidade = quantidade;
	}

	public Integer getIdLanches() {
		return idLanches;
	}

	public void setIdLanches(Integer idLanches) {
		this.idLanches = idLanches;
	}

	public String getNomeLanches() {
		return nomeLanches;
	}

	public void setNomeLanches(String nomeLanches) {
		this.nomeLanches = nomeLanches;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLinkImgLanche() {
		return linkImgLanche;
	}

	public void setLinkImgLanche(String linkImgLanche) {
		this.linkImgLanche = linkImgLanche;
	}

	public Double getValorLanche() {
		return valorLanche;
	}

	public void setValorLanche(Double valorLanche) {
		this.valorLanche = valorLanche;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	@Override
	public String toString() {
		return "Lanches [idLanches=" + idLanches + ", nomeLanches=" + nomeLanches + ", descricao=" + descricao
				+ ", linkImgLanche=" + linkImgLanche + ", valorLanche=" + valorLanche + ", quantidade=" + quantidade
				+ "]";
	}


}

	