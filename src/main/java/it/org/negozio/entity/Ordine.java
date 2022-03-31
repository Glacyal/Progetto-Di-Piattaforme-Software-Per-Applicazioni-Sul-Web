package it.org.negozio.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Ordine implements Serializable,Iterable<ProdottoInOrdine> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy="ordine_id",fetch=FetchType.EAGER)
	private Collection<ProdottoInOrdine> prodottiAcquistati = new LinkedList<>();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	private Cliente cliente_id;
	
	
	public long getPrezzoTotale() {
		long res = 0;
		for(ProdottoInOrdine p : this) {
			res = res +p.getProdotto_id().getPrezzo() * p.getQuantita();
		}
		return res;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Ordine))
			return false;
		Ordine other = (Ordine) obj;
		if (cliente_id == null) {
			if (other.cliente_id != null)
				return false;
		} else if (!cliente_id.equals(other.cliente_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (prodottiAcquistati == null) {
			if (other.prodottiAcquistati != null)
				return false;
		} else if (!prodottiAcquistati.equals(other.prodottiAcquistati))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente_id == null) ? 0 : cliente_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((prodottiAcquistati == null) ? 0 : prodottiAcquistati.hashCode());
		return result;
	}

	public Collection<ProdottoInOrdine> getProdottiAcquistati() {
		return prodottiAcquistati;
	}

	public void setProdottiAcquistati(Collection<ProdottoInOrdine> prodottiAcquistati) {
		this.prodottiAcquistati = prodottiAcquistati;
	}

	@Override
	public Iterator<ProdottoInOrdine> iterator() {
		return prodottiAcquistati.iterator();
	}
	
	
	public Cliente getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Cliente cliente_id) {
		this.cliente_id = cliente_id;
	}
}