package it.org.negozio.beans;

import java.io.Serializable;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import it.org.negozio.dao.CarrelloDao;
import it.org.negozio.dao.ClienteDao;
import it.org.negozio.ejb.LoginHandler;
import it.org.negozio.entity.Carrello;
import it.org.negozio.entity.Cliente;

@SessionScoped
@Named
public class AccessController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private LoginHandler loginHandler;
	
	@EJB
	private ClienteDao clienteDao;
	
	@EJB
	private CarrelloDao carrelloDao;

	private long userId = 0l;

	private String nome, cognome;
	
	private boolean logged = false;

	private boolean admin = false, alreadySignup=false;

	private String userName="";

	private String password="";

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String signup() {
		if(clienteDao.findByUsername(userName)!=null) return "failed";
		Cliente cliente = new Cliente();
		Carrello carrello = new Carrello();
		cliente.setCognome(cognome);
		cliente.setNome(nome);
		cliente.setNomeUtente(userName);
		cliente.setPassword(password);
		cliente.setAdmin(admin);
		carrello.setCliente_id(cliente);
		carrello.setProdotti(new LinkedList<>());
		try{
			carrelloDao.create(carrello);
		}catch(Exception e){
			System.out.println("errore iscrizione account");
		}
		setAlreadySignup(true);
		return "Success";
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (loginHandler != null)
			result += "lh: " + loginHandler;
		result += ", userId: " + userId;
		result += ", logged: " + logged;
		result += ", admin: " + admin;
		if (userName != null && !userName.trim().isEmpty())
			result += ", userName: " + userName;
		if (password != null && !password.trim().isEmpty())
			result += ", password: " + password;
		return result;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public boolean isAlreadySignup() {
		return alreadySignup;
	}

	public void setAlreadySignup(boolean alreadySignup) {
		this.alreadySignup = alreadySignup;
	}

}