package it.org.negozio.entity;

import java.io.Serializable;

public class ProdottoInOrdinePK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long prodotto_id;

	private Long ordine_id;

	public Long getProdotto_id() {
		return prodotto_id;
	}

	public void setProdotto_id(Long prodotto_id) {
		this.prodotto_id = prodotto_id;
	}

	public Long getOrdine_id() {
		return ordine_id;
	}

	public void setOrdine_id(Long ordine_id) {
		this.ordine_id = ordine_id;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (prodotto_id != null)
			result += "prodotto_id: " + prodotto_id;
		if (ordine_id != null)
			result += ", ordine_id: " + ordine_id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ordine_id == null) ? 0 : ordine_id.hashCode());
		result = prime * result + ((prodotto_id == null) ? 0 : prodotto_id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdottoInOrdinePK other = (ProdottoInOrdinePK) obj;
		if (ordine_id == null) {
			if (other.ordine_id != null)
				return false;
		} else if (!ordine_id.equals(other.ordine_id))
			return false;
		if (prodotto_id == null) {
			if (other.prodotto_id != null)
				return false;
		} else if (!prodotto_id.equals(other.prodotto_id))
			return false;
		return true;
	}

}
