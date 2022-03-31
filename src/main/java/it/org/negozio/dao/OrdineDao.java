package it.org.negozio.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import it.org.negozio.entity.Carrello;
import it.org.negozio.entity.Ordine;
import it.org.negozio.entity.ProdottoInCarrello;
import it.org.negozio.entity.ProdottoInOrdine;

@Stateless
public class OrdineDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(Ordine entity) {
		entityManager.persist(entity);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void pagaCarrello(Carrello car,long userId) {		
		Carrello carrello=null;
		if(userId>0)
			carrello = entityManager.find(Carrello.class, car.getId());
		else carrello=car;
		
		Ordine ordine = new Ordine();
		ordine.setCliente_id(carrello.getCliente_id());
		LinkedList<ProdottoInOrdine> prodottiOrdine = new LinkedList<>();
		ordine.setProdottiAcquistati(prodottiOrdine);
		if(userId>0) {
			entityManager.lock(carrello, LockModeType.PESSIMISTIC_WRITE);
		}
		
		entityManager.persist(ordine);
		for(ProdottoInCarrello p : carrello.getProdotti()) {
			entityManager.refresh(p.getProdotto_id());
			
			if(p.getProdotto_id().getQuantitaResidua()-p.getQuantita()>=0) {
				ProdottoInOrdine prodottoInCarrello = new ProdottoInOrdine();
				prodottoInCarrello.setOrdine_id(ordine);
				p.getProdotto_id().setQuantitaResidua( (int)(p.getProdotto_id().getQuantitaResidua()-p.getQuantita()) );
				prodottoInCarrello.setQuantita(p.getQuantita());
				prodottoInCarrello.setProdotto_id(p.getProdotto_id());
				entityManager.persist(prodottoInCarrello);
				prodottiOrdine.add(prodottoInCarrello);			
			}
			
			else throw new RuntimeException("Ordine non andato a buon fine - meno prodotti dei richiesti");
		}
		
		entityManager.merge(ordine);
	}
	
	public void deleteById(Long id) {
		Ordine entity = entityManager.find(Ordine.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public Ordine findById(Long id) {
		return entityManager.find(Ordine.class, id);
	}

	public Ordine update(Ordine entity) {
		return entityManager.merge(entity);
	}

	public List<Ordine> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Ordine> findAllQuery = entityManager
				.createQuery(
						"SELECT DISTINCT o FROM Ordine o LEFT JOIN FETCH o.prodottiAcquistati LEFT JOIN FETCH o.cliente_id ORDER BY o.id",
						Ordine.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
	
	public List<Ordine> listAllByClient(Integer startPosition, Integer maxResult, long userId) {
		TypedQuery<Ordine> findAllQuery = entityManager
				.createQuery(
						"SELECT DISTINCT o FROM Ordine o LEFT JOIN FETCH o.prodottiAcquistati LEFT JOIN FETCH o.cliente_id "
						+ "WHERE o.cliente_id.id=:first "
						+ "ORDER BY o.id",
						Ordine.class);
		findAllQuery.setParameter("first", userId);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
