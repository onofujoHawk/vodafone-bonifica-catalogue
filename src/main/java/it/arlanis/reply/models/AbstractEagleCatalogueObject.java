package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEagleCatalogueObject implements EagleCatalogueObject, Serializable {
	
	@Column(name = "creator")
	@Basic(optional = false)
	protected String creator;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
	@Basic(optional = false)
    protected Date creationDate;

    @Column(name = "modifier")
    @Basic(optional = false)
    protected String modifier;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    @Basic(optional = false)
    protected Date lastUpdate;
    
    @Version
    protected long version;
    
    public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date created) {
		this.creationDate = created;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	@PrePersist
	public void onPrePersist() {
		lastUpdate = creationDate = new Date();
	}

	@PreUpdate
	public void onPreUpdate() {
		lastUpdate = new Date();
	}
		
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
