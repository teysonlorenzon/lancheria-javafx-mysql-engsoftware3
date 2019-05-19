package model.entidades;

public class ItensLanches {
	
	private Integer idItensLanche;
	private Integer idProduto;
	private Integer idLanche;
	private Integer quantidade;
	public ItensLanches(Integer idItensLanche, Integer idProduto, Integer idLanche, Integer quantidade) {
		
		this.idItensLanche = idItensLanche;
		this.idProduto = idProduto;
		this.idLanche = idLanche;
		this.quantidade = quantidade;
	}
	public Integer getIdItensLanche() {
		return idItensLanche;
	}
	public void setIdItensLanche(Integer idItensLanche) {
		this.idItensLanche = idItensLanche;
	}
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public Integer getIdLanche() {
		return idLanche;
	}
	public void setIdLanche(Integer idLanche) {
		this.idLanche = idLanche;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	@Override
	public String toString() {
		return "ItensLanches [idItensLanche=" + idItensLanche + ", idProduto=" + idProduto + ", idLanche=" + idLanche
				+ ", quantidade=" + quantidade + "]";
	}
	
	

}
