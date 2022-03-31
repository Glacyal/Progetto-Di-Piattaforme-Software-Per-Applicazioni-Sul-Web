package it.org.negozio.entity;

import java.io.Serializable;

public class ProdottoInCarrelloPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long carrello_id;
	private Long prodotto_id;

	public Long getCarrello_id() {
		return carrello_id;
	}

	public void setCarrello_id(Long carrello_id) {
		this.carrello_id = carrello_id;
	}

	

	@Override
	public String toString() {
		return "ProdottoInCarrelloPK [carrello_id=" + carrello_id + ", prodotto=" + prodotto_id + "]";
	}

	public Long getProdotto_id() {
		return prodotto_id;
	}

	public void setProdotto_id(Long prodotto_id) {
		this.prodotto_id = prodotto_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrello_id == null) ? 0 : carrello_id.hashCode());
		result = prime * result + ((prodotto_id == null) ? 0 : prodotto_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProdottoInCarrelloPK))
			return false;
		ProdottoInCarrelloPK other = (ProdottoInCarrelloPK) obj;
		if (carrello_id == null) {
			if (other.carrello_id != null)
				return false;
		} else if (!carrello_id.equals(other.carrello_id))
			return false;
		if (prodotto_id == null) {
			if (other.prodotto_id != null)
				return false;
		} else if (!prodotto_id.equals(other.prodotto_id))
			return false;
		return true;
	}

	
	
}
