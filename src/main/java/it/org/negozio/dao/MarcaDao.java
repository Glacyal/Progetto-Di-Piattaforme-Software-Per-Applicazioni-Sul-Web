package it.org.negozio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import it.org.negozio.entity.Marca;

@Stateless
public class MarcaDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(Marca entity) {
		entityManager.persist(entity);
	}

	public void deleteById(long id) {
		Marca entity = entityManager.find(Marca.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public Marca findById(long id) {
		return entityManager.find(Marca.class, id);
	}

	public Marca update(Marca entity) {
		return entityManager.merge(entity);
	}

	public List<Marca> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Marca> findAllQuery = entityManager.createQuery(
				"SELECT DISTINCT m FROM Marca m ORDER BY m.id", Marca.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
