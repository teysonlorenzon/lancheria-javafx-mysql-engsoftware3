package model.entidades;

import java.io.Serializable;

public class Estoque implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEstoque;
	private String nomeProduto;
	private Integer quantidade;
	private String tipoProduto;

	public Estoque() {
	}

	public Estoque(Integer idEstoque, String nomeProduto, Integer quantidade, String tipoProduto) {
		this.idEstoque = idEstoque;
		this.nomeProduto = nomeProduto;
		this.quantidade = quantidade;
		this.tipoProduto = tipoProduto;
	}

	public Integer getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Integer idEstoque) {
		this.idEstoque = idEstoque;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(String tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	@Override
	public String toString() {
		return "Estoque [idEstoque=" + idEstoque + ", nomeProduto=" + nomeProduto + ", quantidade=" + quantidade
				+ ", tipoProduto=" + tipoProduto + "]";
	}

}
