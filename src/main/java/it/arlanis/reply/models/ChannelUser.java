package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="channel_user")
public class ChannelUser extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "channel_user_id")
	private Long channelUserId;
	
	@Column(name="code")
	private String code;
	
	@Column(name = "username", unique=true)
	private String username;
	
	@Column(name="email")
	private String email;
	
	@Column(name = "application")
	private String application;
	
	@Column(name = "pos_code")
	private String posCode;
	
	@ManyToOne
	@JoinColumn(name= "channel", referencedColumnName="channel_id")
	private Channel channel;
	
	@OneToMany(mappedBy="channelUser")
	private List<EnabledUser> enabledUsers=new ArrayList<EnabledUser>();
	
	public Long getId() {
		return channelUserId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public List<EnabledUser> getEnabledUsers() {
		return enabledUsers;
	}

	public void setEnabledUsers(List<EnabledUser> enabledUsers) {
		this.enabledUsers = enabledUsers;
	}

	public Long getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(Long channelUserId) {
		this.channelUserId = channelUserId;
	}


}
