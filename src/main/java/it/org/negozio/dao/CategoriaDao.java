package it.org.negozio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import it.org.negozio.entity.Categoria;

@Stateless
public class CategoriaDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(Categoria entity) {
		entityManager.persist(entity);
	}

	public void deleteById(Long id) {
		Categoria entity = entityManager.find(Categoria.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public Categoria findById(Long id) {
		return entityManager.find(Categoria.class, id);
	}

	public Categoria update(Categoria entity) {
		return entityManager.merge(entity);
	}

	public List<Categoria> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Categoria> findAllQuery = entityManager.createQuery(
				"SELECT DISTINCT c FROM Categoria c ORDER BY c.id",
				Categoria.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
