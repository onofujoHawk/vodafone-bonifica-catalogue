package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.ChannelType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channel")
public class Channel extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "channel_id")
	private Long channelId;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private ChannelType type;

	@ManyToMany(mappedBy = "channels")
	private List<Campaign> campaigns = new ArrayList<Campaign>();

	@OneToMany(mappedBy = "channel")
	private List<EnabledUser> enabledUsers = new ArrayList<EnabledUser>();

	@OneToMany(mappedBy = "channel")
	private List<ChannelUser> channelUsers = new ArrayList<ChannelUser>();

	public Long getId() {
		return channelId;
	}

	public ChannelType getType() {
		return type;
	}

	public void setType(ChannelType type) {
		this.type = type;
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public List<EnabledUser> getEnabledUsers() {
		return enabledUsers;
	}

	public void setEnabledUsers(List<EnabledUser> enabledUsers) {
		this.enabledUsers = enabledUsers;
	}

	public List<ChannelUser> getChannelUsers() {
		return channelUsers;
	}

	public void setChannelUsers(List<ChannelUser> channelUsers) {
		this.channelUsers = channelUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelId == null) ? 0 : channelId.hashCode());
		return result;
	}



}
