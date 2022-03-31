package it.org.negozio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import it.org.negozio.entity.Carrello;

@Stateless
public class CarrelloDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(Carrello entity) {
		entityManager.persist(entity);
	}

	public void deleteById(Long id) {
		Carrello entity = entityManager.find(Carrello.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public Carrello findById(Long id) {
		return entityManager.find(Carrello.class, id);
	}
	
	public Carrello findByCliente(Long clientId) {
		TypedQuery<Carrello> findQuery = entityManager
				.createQuery(
						"SELECT DISTINCT c FROM Carrello c LEFT JOIN FETCH c.prodotti "
					  + "WHERE c.cliente_id.id=:first",
						Carrello.class);
		findQuery.setParameter("first", clientId);
		return findQuery.getSingleResult();
	}

	public Carrello update(Carrello entity) {
		return entityManager.merge(entity);
	}

	public List<Carrello> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Carrello> findAllQuery = entityManager
				.createQuery(
						"SELECT DISTINCT c FROM Carrello c LEFT JOIN FETCH c.prodotti ORDER BY c.id",
						Carrello.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
