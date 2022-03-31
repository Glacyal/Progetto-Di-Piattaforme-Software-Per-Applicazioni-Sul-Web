package it.org.negozio.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@IdClass(ProdottoInOrdinePK.class)
@Entity
@Table(name = "ordine_prodotto")
public class ProdottoInOrdine implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="prodotto_id", referencedColumnName="id", insertable=true, updatable=true)
	private Prodotto prodotto_id;
	
	@Id
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="ordine_id", referencedColumnName="id", insertable=true, updatable=true) 
	private Ordine ordine_id;
	
	@Column(name = "quantita")
	private Long quantita;

	public Prodotto getProdotto_id() {
		return prodotto_id;
	}

	public void setProdotto_id(Prodotto prodotto_id) {
		this.prodotto_id = prodotto_id;
	}

	public Ordine getOrdine_id() {
		return ordine_id;
	}

	public void setOrdine_id(Ordine ordine_id) {
		this.ordine_id = ordine_id;
	}

	public Long getQuantita() {
		return quantita;
	}

	public void setQuantita(Long quantita) {
		this.quantita = quantita;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ordine_id == null) ? 0 : ordine_id.hashCode());
		result = prime * result + ((prodotto_id == null) ? 0 : prodotto_id.hashCode());
		result = prime * result + ((quantita == null) ? 0 : quantita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProdottoInOrdine))
			return false;
		ProdottoInOrdine other = (ProdottoInOrdine) obj;
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
		if (quantita == null) {
			if (other.quantita != null)
				return false;
		} else if (!quantita.equals(other.quantita))
			return false;
		return true;
	}
	
	
}