
package it.org.negozio.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import it.org.negozio.dao.CategoriaDao;
import it.org.negozio.dao.MarcaDao;
import it.org.negozio.dao.ProdottoDao;
import it.org.negozio.ejb.RefresherBean;
import it.org.negozio.entity.Categoria;
import it.org.negozio.entity.Marca;
import it.org.negozio.entity.Prodotto;

@ViewScoped
@Named
public class AdminAbility implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private ProdottoDao prodottoDao;
	
	@EJB
	private MarcaDao marcaDao;
	
	@EJB
	private CategoriaDao categoriaDao;
	
	@EJB
	private RefresherBean refresherBean;
	
	private long id_prodotto, id_marca, id_categoria;
	private String nome_prodotto, nome_categoria, nome_marca, descrizione_marca;
	private int quantitaResidua, prezzo;
	
	public String aggiungiMarca() {
		Marca marca = new Marca();
		marca.setNome(nome_marca);
		marca.setDescrizione(descrizione_marca);
		marcaDao.create(marca);
		return "success";
	}
	
	public String aggiungiCategoria() {
		Categoria categoria = new Categoria();
		categoria.setNome(nome_categoria);
		categoriaDao.create(categoria);
		return "success";
	}
	
	public String aggiungiProdotto() {
		Prodotto prodotto = new Prodotto();
		Marca marca = new Marca();
		Categoria categoria = new Categoria();
		marca.setId(id_marca);
		categoria.setId(id_categoria);
		prodotto.setMarca(marca);
		prodotto.setCategoria(categoria);
		prodotto.setNome(nome_prodotto);
		prodotto.setPrezzo(prezzo);
		prodotto.setQuantitaResidua(quantitaResidua);
		refresherBean.aggiungiProdottoInMagazzino(prodotto);
		return "success";
	}
	
	public List<Prodotto> getProdottiDisponibili() {
		return prodottoDao.listAll(null, null);
	}
	public List<Marca> getMarcheDisponibili() {
		return marcaDao.listAll(null, null);
	}
	
	public List<Categoria> getCategorieDisponibili(){
		return categoriaDao.listAll(null, null);
	}
	
	public String aggiornaQuantitaMagazzinoProdotto() {
		try{
			refresherBean.aggiornaQuantitaMagazzinoProdotto(quantitaResidua, id_prodotto);
		}catch(RuntimeException e) {
			return "failed";
		}
		return "success";
	}
	
	public String aggiornaPrezzoMagazzinoProdotto() {
		try{
			refresherBean.aggiornaPrezzoMagazzinoProdotto(prezzo, id_prodotto);
		}catch(RuntimeException e) {
			return "failed";
		}
		return "success";
	}
	
	public int getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}
	public String getNome_prodotto() {
		return nome_prodotto;
	}
	public void setNome_prodotto(String nome_prodotto) {
		this.nome_prodotto = nome_prodotto;
	}
	public String getNome_categoria() {
		return nome_categoria;
	}
	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}
	public String getNome_marca() {
		return nome_marca;
	}
	public void setNome_marca(String nome_marca) {
		this.nome_marca = nome_marca;
	}
	public String getDescrizione_marca() {
		return descrizione_marca;
	}
	public void setDescrizione_marca(String descrizione_marca) {
		this.descrizione_marca = descrizione_marca;
	}
	public int getQuantitaResidua() {
		return quantitaResidua;
	}
	public void setQuantitaResidua(int quantitaResidua) {
		this.quantitaResidua = quantitaResidua;
	}
	public long getId_prodotto() {
		return id_prodotto;
	}

	public void setId_prodotto(long id_prodotto) {
		this.id_prodotto = id_prodotto;
	}

	public long getId_marca() {
		return id_marca;
	}

	public void setId_marca(long id_marca) {
		this.id_marca = id_marca;
	}

	public long getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(long id_categoria) {
		this.id_categoria = id_categoria;
	}

}