package it.org.negozio.ejb;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import javax.ejb.EJB;
//import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import it.org.negozio.dao.CarrelloDao;
import it.org.negozio.entity.Carrello;
import it.org.negozio.entity.Prodotto;
import it.org.negozio.entity.ProdottoInCarrello;

@Stateful
public class CarrelloHandler implements Serializable {

	private static final long serialVersionUID = -1L;
	
	@PersistenceContext(unitName = "MioProgetto-persistence-unit",type=PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	@EJB
	private CarrelloDao carrelloDao;
	
	private Carrello carrello;

	private long userId=0l;
	
	public void initCarrello(long userId) {
		
		if(userId<0) throw new RuntimeException("userID negativo non consentito - " + userId);	
		if(userId==0l) {
			carrello = new Carrello();
			carrello.setProdotti(new LinkedList<>());
		}
		else {
			this.userId=userId;
			carrello = carrelloDao.findByCliente(userId);
		}
	}
		
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void aggiungiProdotto(Prodotto pr) {
		Prodotto p = null;
		if(userId>0) {
			entityManager.lock(carrello, LockModeType.PESSIMISTIC_WRITE);
			p = entityManager.find(Prodotto.class, pr.getId());			
		}
		else p=pr;
		if(p.getQuantitaResidua()<=0) return;
		ProdottoInCarrello pOld =  null;
		Iterator<ProdottoInCarrello> it = carrello.iterator();
		while(it.hasNext()) {
			ProdottoInCarrello pic = it.next();
			if(pic.getProdotto_id().getId()==p.getId()) {
				pOld = pic;
			}
		}
		if( pOld == null && p.getQuantitaResidua()>0) {
			pOld = new ProdottoInCarrello();
			pOld.setCarrello_id(carrello);
			pOld.setProdotto_id(p);
			pOld.setQuantita(1l);;
			carrello.getProdotti().add(pOld);
			if(userId>0)
				entityManager.persist(pOld);
		}
		else if(pOld.getProdotto_id().getQuantitaResidua()-pOld.getQuantita()>0){
			pOld.setQuantita( pOld.getQuantita()+1 );
		}
		if(userId>0) {
			entityManager.merge(carrello);
		}
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void svuotaCarrello() {
		if(userId==0) {
			carrello.setProdotti(new LinkedList<>());
			return;
		}
		entityManager.lock(carrello, LockModeType.PESSIMISTIC_WRITE);
		Iterator<ProdottoInCarrello> it = carrello.iterator();
		while(it.hasNext()) {
			ProdottoInCarrello pc = it.next();
			entityManager.remove(pc);
			//pc.setCarrello_id(null);
			it.remove();
		}
		//carrello.getProdotti().clear();
		entityManager.merge(carrello);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void rimuoviProdotto(Prodotto p) {
		
		if(userId>0)
			entityManager.lock(carrello, LockModeType.PESSIMISTIC_WRITE);
			
		Iterator<ProdottoInCarrello> it = carrello.iterator();
		while(it.hasNext()) {
			ProdottoInCarrello pc = it.next();
			if(pc.getProdotto_id().getId()==p.getId()) {
				if(pc.getQuantita()==1) {
					if(userId>0) {
						entityManager.remove(pc);
						//pc.setCarrello_id(null);
					}
					it.remove();
					
				}
				else
					pc.setQuantita(pc.getQuantita()-1);
			}
		}
		if(userId>0) carrelloDao.update(carrello);
		
		
	}
	
	public long getUserId() {
		return userId;
	}
	
	public Carrello getCarrello() {
		return carrello;
	}
	
	public void setCarrello(Carrello c) {
		this.carrello = c;
	}
	
}