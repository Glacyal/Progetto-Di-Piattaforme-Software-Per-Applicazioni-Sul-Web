package it.org.negozio.ejb;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.org.negozio.dao.CategoriaDao;
import it.org.negozio.dao.ClienteDao;
import it.org.negozio.dao.MarcaDao;
import it.org.negozio.dao.ProdottoDao;
import it.org.negozio.entity.Prodotto;

@Stateless
public class RefresherBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProdottoDao prodottoDao;
	@EJB
	private ClienteDao clienteDao;
	@EJB
	private MarcaDao marcaDao;
	@EJB
	private CategoriaDao categoriaDao;
	
	public void aggiungiProdottoInMagazzino(Prodotto p) {
		prodottoDao.create(p);
	}
	
	public void aggiornaQuantitaMagazzinoProdotto(int newQuantita, long id) {
		if(newQuantita<0) throw new RuntimeException("Nuova quantita non valida(NEGATIVA)");
		Prodotto p = prodottoDao.findById(id);
		p.setQuantitaResidua(newQuantita);
		prodottoDao.update(p);
	}
	
	public void aggiornaPrezzoMagazzinoProdotto(int newPrezzo, long id) {
		if(newPrezzo<0) throw new RuntimeException("Nuova quantita non valida(NEGATIVA)");
		Prodotto p = prodottoDao.findById(id);
		p.setPrezzo(newPrezzo);;
		prodottoDao.update(p);
	}
	
	
}
