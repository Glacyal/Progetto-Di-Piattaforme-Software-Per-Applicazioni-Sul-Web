package it.org.negozio.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import it.org.negozio.entity.Cliente;

@Stateless
public class ClienteDao {
	@PersistenceContext(unitName = "MioProgetto-persistence-unit")
	private EntityManager entityManager;

	public void create(Cliente entity) {
		entityManager.persist(entity);
	}

	public void deleteById(Long id) {
		Cliente entity = entityManager.find(Cliente.class, id);
		if (entity != null) {
			entityManager.remove(entity);
		}
	}

	public Cliente findById(Long id) {
		return entityManager.find(Cliente.class, id);
	}
	
	public Cliente findByUsername(String username) {
		TypedQuery<Cliente> loginQuery = entityManager.
				createQuery(" SELECT DISTINCT c FROM Cliente c WHERE c.nomeUtente=:username", 
				Cliente.class);
		loginQuery.setParameter("username", username);
		List<Cliente> res = loginQuery.getResultList();
		if(res.size()!=0) return res.get(0);
		return null;
	}

	public Cliente update(Cliente entity) {
		return entityManager.merge(entity);
	}

	public List<Cliente> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Cliente> findAllQuery = entityManager
				.createQuery("SELECT DISTINCT c FROM Cliente c ORDER BY c.id",
						Cliente.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		return findAllQuery.getResultList();
	}
}
