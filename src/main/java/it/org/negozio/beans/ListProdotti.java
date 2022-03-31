package it.org.negozio.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import it.org.negozio.dao.ProdottoDao;
import it.org.negozio.entity.Prodotto;


@RequestScoped
@Named
public class ListProdotti implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	ProdottoDao prodottoDao;

	private String messaggio;

	private List<Prodotto> listaProdotti;

	public String getMessaggio() {
		return "";
	}

	public void setMessaggio(String m) {
		messaggio = m;
	}

	public List<Prodotto> getListaProdotti() {
		return prodottoDao.listAll(null,null);
	}

	public void setListaProdotti(List<Prodotto> listaProdotti) {
		this.listaProdotti = listaProdotti;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (prodottoDao != null)
			result += "prodDao: " + prodottoDao;
		if (messaggio != null && !messaggio.trim().isEmpty())
			result += ", messaggio: " + messaggio;
		if (listaProdotti != null)
			result += ", listaProdotti: " + listaProdotti;
		return result;
	}

}
