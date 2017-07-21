package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "log_access")
public class LogAccess extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "log_access_id")
	private Long logAccessId;

	@Column(name = "access_date")
	private Date accessDate;

	@Column(name = "user_agent")
	private String userAgent;

	@Column(name = "description")
	private String description;

	@Column(name = "ip_address")
	private String ipAddress;

	@JoinColumn(name = "utente", referencedColumnName = "user_id")
	@ManyToOne
	private User user;

	public LogAccess() {
	}

	public Long getId() {
		return logAccessId;
	}

	public Long getLogAccessId() {
		return logAccessId;
	}

	public void setLogAccessId(Long logAccessId) {
		this.logAccessId = logAccessId;
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logAccessId == null) ? 0 : logAccessId.hashCode());
		return result;
	}



}
