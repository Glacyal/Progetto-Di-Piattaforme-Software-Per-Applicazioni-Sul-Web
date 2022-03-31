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

@IdClass(ProdottoInCarrelloPK.class)
@Entity
@Table(name = "carrello_prodotto")
public class ProdottoInCarrello implements Serializable {
 
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="carrello_id", referencedColumnName="id", insertable=true, updatable=true) 
	private Carrello carrello_id;
	
	@Id
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="prodotto_id", referencedColumnName="id", insertable=true, updatable=true)
	private Prodotto prodotto_id;
	
	@Column(name = "quantita")
	private Long quantita;
	public Long getQuantita() {
		return quantita;
	}
	
	public void setQuantita(Long quantita) {
		this.quantita = quantita;
	}
	
	public Carrello getCarrello_id() {
		return carrello_id;
	}

	public void setCarrello_id(Carrello carrello_id) {
		this.carrello_id = carrello_id;
	}

	public Prodotto getProdotto_id() {
		return prodotto_id;
	}

	public void setProdotto_id(Prodotto prodotto_id) {
		this.prodotto_id = prodotto_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carrello_id == null) ? 0 : carrello_id.hashCode());
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
		if (!(obj instanceof ProdottoInCarrello))
			return false;
		ProdottoInCarrello other = (ProdottoInCarrello) obj;
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
		if (quantita == null) {
			if (other.quantita != null)
				return false;
		} else if (!quantita.equals(other.quantita))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProdottoInCarrello [carrello_id=" + carrello_id + ", quantita=" + quantita
				+ ", prodotto_id=" + prodotto_id + "]";
	}

	
}