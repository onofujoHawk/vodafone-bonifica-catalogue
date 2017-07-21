package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name = "profile_object_access")
public class ProfileObjectAccess extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_object_access_id")
	private Long profileObjectAccessId;

	@Column(name = "lettura")
	private boolean lettura;

	@Column(name = "scrittura")
	private boolean scrittura;

	@Column(name = "creazione")
	private boolean creazione;

	@Column(name = "cancellazione")
	private boolean cancellazione;
	
	@ManyToOne
	@JoinColumn(name = "profile", referencedColumnName = "profile_id")
	private Profile profile;

	@ManyToOne
	@JoinColumn(name = "object", referencedColumnName = "object_id")
	private it.arlanis.reply.models.Object object;

	public ProfileObjectAccess() {
	}

	public Long getId() {
		return profileObjectAccessId;
	}

	public Long getProfileObjectAccessId() {
		return profileObjectAccessId;
	}

	public void setProfileObjectAccessId(Long profileObjectAccessId) {
		this.profileObjectAccessId = profileObjectAccessId;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public boolean isLettura() {
		return lettura;
	}

	public void setLettura(boolean lettura) {
		this.lettura = lettura;
	}

	public boolean isScrittura() {
		return scrittura;
	}

	public void setScrittura(boolean scrittura) {
		this.scrittura = scrittura;
	}

	public boolean isCreazione() {
		return creazione;
	}

	public void setCreazione(boolean creazione) {
		this.creazione = creazione;
	}

	public boolean isCancellazione() {
		return cancellazione;
	}

	public void setCancellazione(boolean cancellazione) {
		this.cancellazione = cancellazione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((profileObjectAccessId == null) ? 0 : profileObjectAccessId.hashCode());
		return result;
	}



	public static ProfileObjectAccess findByProfileAndObject(Profile p, Object o){
		List<ProfileObjectAccess> l=p.getProfileObjectAccesses();
		ProfileObjectAccess poa=null;
		for(ProfileObjectAccess pp : l){
			if(pp.getObject().equals(o)){
				poa=pp;
				break;
			}
		}
		return poa;
	}

}
