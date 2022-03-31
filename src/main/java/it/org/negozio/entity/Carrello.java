package it.org.negozio.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Cacheable
public class Carrello implements Serializable,Iterable<ProdottoInCarrello> {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Cliente cliente_id;
	private Collection<ProdottoInCarrello> prodotti;

	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProdotti(Collection<ProdottoInCarrello> prodotti) {
		this.prodotti = prodotti;
	}

	@OneToMany(cascade= {CascadeType.ALL},mappedBy="carrello_id",fetch=FetchType.EAGER)
	public Collection<ProdottoInCarrello> getProdotti() {
		return prodotti;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Carrello)) {
			return false;
		}
		Carrello other = (Carrello) obj;
		if (id != null) {
			if (!id.equals(other.id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Carrello [id=" + id +", cliente_id=" + cliente_id
				+ "]";
	}

	@Override
	public Iterator<ProdottoInCarrello> iterator() {
		return prodotti.iterator();
	}

	@ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinColumn(name="cliente_id", referencedColumnName="id")
	public Cliente getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Cliente cliente_id) {
		this.cliente_id = cliente_id;
	}

	
}