package it.org.negozio.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Prodotto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "prezzo")
	private Integer prezzo;

	@ManyToOne(cascade = {
			CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
	private Categoria categoria;

	@ManyToOne(cascade = {
			CascadeType.DETACH, CascadeType.REFRESH,CascadeType.MERGE})
	private Marca marca;

	@Column(name = "qta_residua")
	private Integer quantitaResidua;


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
		if (!(obj instanceof Prodotto))
			return false;
		Prodotto other = (Prodotto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public Integer getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Integer prezzo) {
		this.prezzo = prezzo;
	}

	public Marca getMarca() {
		return this.marca;
	}

	public void setMarca(final Marca marca) {
		this.marca = marca;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(final Categoria categoria) {
		this.categoria = categoria;
	}

	public Integer getQuantitaResidua() {
		return quantitaResidua;
	}

	public void setQuantitaResidua(Integer quantitaResidua) {
		this.quantitaResidua = quantitaResidua;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "prodotto [id=" + id + ", nome=" + nome + "]";
	}
	
	
}