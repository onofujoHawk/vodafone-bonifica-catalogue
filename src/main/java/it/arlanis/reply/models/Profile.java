package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.ProfileType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "profile")
public class Profile extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profile_id")
	private Long profileId;

	@Basic(optional = false)
	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private ProfileType type;
	
	@OneToMany(mappedBy="profile")
	private List<User> users=new ArrayList<User>(); 
	
	@OneToMany(mappedBy="profile")
	private List<ProfileObjectAccess> profileObjectAccesses=new ArrayList<ProfileObjectAccess>();
	
	public Profile(){
		
	}
	
	public Profile(ProfileType type) {
		this.type = type;
	}

	public Long getId() {
		return profileId;
	}

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long id) {
		this.profileId = id;
	}

	public ProfileType getType() {
		return type;
	}

	public void setType(ProfileType type) {
		this.type = type;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<ProfileObjectAccess> getProfileObjectAccesses() {
		return profileObjectAccesses;
	}

	public void setProfileObjectAccesses(List<ProfileObjectAccess> profileObjectAccesses) {
		this.profileObjectAccesses = profileObjectAccesses;
	}
	
	public void addProfileObjectAccesses(ProfileObjectAccess poa){
		this.profileObjectAccesses.add(poa);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((profileId == null) ? 0 : profileId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Profile))
			return false;
		Profile other = (Profile) obj;
		if (profileId == null) {
			if (other.profileId != null)
				return false;
		} else if (!profileId.equals(other.profileId))
			return false;
		return true;
	}



}
