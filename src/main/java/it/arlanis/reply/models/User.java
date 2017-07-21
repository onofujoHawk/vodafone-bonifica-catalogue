package it.arlanis.reply.models;

import it.arlanis.reply.hibernate.CatalogueService;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "utente")
public class User extends AbstractEagleCatalogueObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "username", unique = true)
	@Basic(optional = false)
	@Min(6)
	private String username;

	@Basic(optional = false)
	@Column(name = "nome", nullable = false)
	private String nome;

	@Basic(optional = false)
	@Column(name = "cognome")
	private String cognome;

	@Basic(optional = false)
	@Column(name = "password")
	@Min(8)
	private String password;

	@Basic(optional = false)
	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "active")
	private boolean active = false;

	@JoinColumn(name = "profile", referencedColumnName = "profile_id")
	@ManyToOne
	private Profile profile;

	@OneToMany(mappedBy = "user")
	private List<LogAccess> logAccesses=new ArrayList<LogAccess>();

	@Column(name = "password_temp")
	@Basic(optional = true)
	private String passwordTemp;

	@Column(name = "password_temp_exp_date")
	@Basic(optional = true)
	private Date passwordTempExpDate;
	
	@Column(name = "validated")
	private boolean validated=false;

	public User() {
	}

	public User(String username, String nome, String cognome, String password, String email, boolean isActive, Profile profilo) {
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.password = password;
		this.email = email;
		this.active = isActive;
		this.profile = profilo;
	}

	public Long getId() {
		return userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public String getPasswordTemp() {
		return passwordTemp;
	}

	public void setPasswordTemp(String passwordTemp) {
		this.passwordTemp = passwordTemp;
	}

	public Date getPasswordTempExpDate() {
		return passwordTempExpDate;
	}

	public void setPasswordTempExpDate(Date passwordTempExpDate) {
		this.passwordTempExpDate = passwordTempExpDate;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public List<LogAccess> getLogAccesses() {
		return logAccesses;
	}

	public void setLogAccesses(List<LogAccess> logAccesses) {
		this.logAccesses = logAccesses;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	private Object findByNome() {
		CatalogueService catalogueService = new CatalogueService();
		return catalogueService.findObjectByNome("User");
	}
	
	public boolean isAdmin(){
		ProfileObjectAccess profObjAccess = ProfileObjectAccess.findByProfileAndObject(this.profile, findByNome());
		return profObjAccess.isCreazione();
	}

}
