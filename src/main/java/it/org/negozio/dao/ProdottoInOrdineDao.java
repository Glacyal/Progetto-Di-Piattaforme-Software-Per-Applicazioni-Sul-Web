package it.org.negozio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import it.org.negozio.entity.ProdottoInOrdine;

@Stateless
public class ProdottoInOrdineDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(ProdottoInOrdine entity) {
		entityManager.persist(entity);
	}

	public void deleteById(Long id) {
		ProdottoInOrdine entity = entityManager.find(ProdottoInOrdine.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public ProdottoInOrdine findById(Long id) {
		return entityManager.find(ProdottoInOrdine.class, id);
	}

	public ProdottoInOrdine update(ProdottoInOrdine entity) {
		return entityManager.merge(entity);
	}

	public List<ProdottoInOrdine> listAll(Integer startPosition,
			Integer maxResult) {
		TypedQuery<ProdottoInOrdine> findAllQuery = entityManager
				.createQuery(
						"SELECT DISTINCT p FROM ProdottoInOrdine p ORDER BY p.prodotto_idORDER BY p.ordine_id",
						ProdottoInOrdine.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
