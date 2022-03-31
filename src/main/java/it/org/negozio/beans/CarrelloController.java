package it.org.negozio.beans;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import it.org.negozio.dao.ClienteDao;
import it.org.negozio.dao.OrdineDao;
import it.org.negozio.ejb.CarrelloHandler;
import it.org.negozio.ejb.LoginHandler;
import it.org.negozio.entity.Carrello;
import it.org.negozio.entity.Ordine;
import it.org.negozio.entity.Prodotto;
import it.org.negozio.entity.ProdottoInCarrello;


@SessionScoped
@Named
public class CarrelloController implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CarrelloHandler CarrelloHandler;

	@EJB
	private OrdineDao ordineDao;
	
	@EJB
	private ClienteDao clienteDao;
	
	@EJB
	private LoginHandler loginHandler;

	private boolean carrelloInizializato = false;

	private boolean admin;

	private long userId = 0l;
	
	@SuppressWarnings("unused")
	private Carrello carrello;

	private boolean logged;

	private String userName;

	private String password;

	public String login() {
		return login(getUserName(),getPassword());
	}
	
	public List<Ordine> storicoOrdini(){
		return ordineDao.listAllByClient(null, null, userId);
	}
	
	private String login(String nomeUtente, String psw) {
		long id = loginHandler.login(nomeUtente, psw);
		if (id <= 0) {
			carrelloInizializato = true;
			CarrelloHandler.initCarrello(0l);
			return "failed";
		}
		carrelloInizializato = true;
		setUserId(id);
		setAdmin(clienteDao.findById(id).getAdmin());
		CarrelloHandler.initCarrello(id);
		setLogged(true);
		return "/index";
	}

	public String logout() {
		return logout(getUserName(),getPassword());
	}
	private String logout(String nomeUtente, String psw) {
		nomeUtente=null;
		psw=null;
		logged=false;
		carrelloInizializato = false;
		admin=false;
		
		return "/index";
		
	}
	
	public String aggiungiProdotto(Prodotto p) {
		if (!carrelloInizializato) {
			CarrelloHandler.initCarrello(0l);
			carrelloInizializato = true;
		}
		CarrelloHandler.aggiungiProdotto(p);
		return "listaprodotti";
	}
	
	public String rimuoviProdotto(Prodotto p) {
		if (!carrelloInizializato) {
			throw new RuntimeException("Carrello non pronto");
		}
		
		CarrelloHandler.rimuoviProdotto(p);
		
		return "listaprodotti";
	}

	public String paga() {
		if(!carrelloInizializato)
			return "failed";
		Carrello carrello = CarrelloHandler.getCarrello();
		try{
			ordineDao.pagaCarrello(carrello,userId);
			CarrelloHandler.svuotaCarrello();
		}catch(Exception e) {
			System.out.println("Acquisto annullato");
			return "failed";
		}
		return "/storicoOrdini";
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Carrello getCarrello() {
		return CarrelloHandler.getCarrello();
	}

	public void setCarrello(Carrello c) {
		CarrelloHandler.setCarrello(c);
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public int getPrezzoTotale() {
		int tot = 0;
		
		for(ProdottoInCarrello p : CarrelloHandler.getCarrello()) {
			tot += p.getQuantita()* p.getProdotto_id().getPrezzo() ;
		}
		return tot;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}



}