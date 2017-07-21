package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="enabled_user")
public class EnabledUser extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enabled_user_id")
	private Long enabledUserId;
	
	@ManyToOne
	@JoinColumn(name = "channel", referencedColumnName = "channel_id")
	private Channel channel;
	
	@ManyToOne
	@JoinColumn(name = "campaign", referencedColumnName = "campaign_id")
	private Campaign campaign;
	
	@ManyToOne
	@JoinColumn(name = "channel_user", referencedColumnName = "channel_user_id")
	private ChannelUser channelUser;
	
	public Long getId() {
		return enabledUserId;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	public ChannelUser getChannelUser() {
		return channelUser;
	}

	public void setChannelUser(ChannelUser channelUser) {
		this.channelUser = channelUser;
	}


}
