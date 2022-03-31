package it.org.negozio.ejb;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import it.org.negozio.entity.Cliente;

@Stateless
@LocalBean
public class LoginHandler implements Serializable {

	private static final long serialVersionUID = -1L;
	
	@PersistenceContext(unitName="MioProgetto-persistence-unit")
	private EntityManager entityManager;
	
	public long login(String nomeUtente, String password) {
		System.out.println("nomeutente: " + nomeUtente);
		TypedQuery<Cliente> loginQuery = entityManager.createQuery(" SELECT DISTINCT c FROM Cliente c WHERE c.nomeUtente=:username",Cliente.class);
		loginQuery.setParameter("username", nomeUtente);
		List<Cliente> res = loginQuery.getResultList();
			if(!res.isEmpty() ) {
				if(res.get(0).getPassword().equals(password))
					return res.get(0).getId();
				else
					return -1l;
			}
		return -1l;
	}
}