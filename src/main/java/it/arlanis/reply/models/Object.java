package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "object")
public class Object extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "object_id")
	private Long objectId;

	@Column(name = "nome", unique=true)
	@Basic(optional = false)
	private String nome;

	@OneToMany(mappedBy = "object")
	private List<ProfileObjectAccess> profileObjectAccesses=new ArrayList<ProfileObjectAccess>();

	public Object() {

	}

	public Long getId() {
		return objectId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		result = prime * result + ((objectId == null) ? 0 : objectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Object))
			return false;
		Object other = (Object) obj;
		if (objectId == null) {
			if (other.objectId != null)
				return false;
		} else if (!objectId.equals(other.objectId))
			return false;
		return true;
	}


}
