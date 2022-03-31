package it.org.negozio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import it.org.negozio.entity.Prodotto;

@Stateless
public class ProdottoDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(Prodotto entity) {
		System.out.println("XYY");
		entityManager.persist(entity);
	}

	public void deleteById(Long id) {
		Prodotto entity = entityManager.find(Prodotto.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public Prodotto findById(Long id) {
		return entityManager.find(Prodotto.class, id);
	}

	public Prodotto update(Prodotto entity) {
		return entityManager.merge(entity);
	}

	public List<Prodotto> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Prodotto> findAllQuery = entityManager.createQuery(
				"SELECT DISTINCT p FROM Prodotto p ORDER BY p.id",
				Prodotto.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
