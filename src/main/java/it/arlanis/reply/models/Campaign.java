package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.CampaignState;
import it.arlanis.reply.models.enums.CampaignType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "campaign")
public class Campaign extends AbstractEagleCatalogueObject implements Serializable {

	public static final String CODE_PREFIX = "camp-";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "campaign_id")
	private Long campaignId;

	public Long getId() {
		return campaignId;
	}

	@Column(name = "code", unique = true)
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private CampaignType type;

	@Column(name = "state")
	@Enumerated(value = EnumType.STRING)
	private CampaignState state;

	@ManyToMany
	@JoinTable(name = "campaign_channel_relation", joinColumns = @JoinColumn(name = "campaign", referencedColumnName = "campaign_id"), inverseJoinColumns = @JoinColumn(name = "channel", referencedColumnName = "channel_id"))
	private List<Channel> channels = new ArrayList<Channel>();

	@OneToMany(mappedBy = "campaign")
	private List<EnabledUser> enabledUsers = new ArrayList<EnabledUser>();

	@Column(name = "convergenza")
	private Boolean convergenza;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@OneToMany(mappedBy = "campaign")
	@Column(nullable = true)
	private List<OfferInCampaign> offersInCampaign = new ArrayList<OfferInCampaign>();

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CampaignType getType() {
		return type;
	}

	public void setType(CampaignType type) {
		this.type = type;
	}

	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannel(List<Channel> channels) {
		this.channels = channels;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		if (endDate == null) {
			this.endDate = null;
			return;
		}
		Calendar date = Calendar.getInstance();
		date.setTime(endDate);
		date.set(Calendar.HOUR_OF_DAY, 23);
		date.set(Calendar.MINUTE, 59);
		date.set(Calendar.SECOND, 59);
		this.endDate = date.getTime();
	}

	public List<OfferInCampaign> getOffersInCampaign() {
		return offersInCampaign;
	}

	public void setOffersInCampaign(List<OfferInCampaign> offersInCampaign) {
		this.offersInCampaign = offersInCampaign;
	}

	public Boolean getConvergenza() {
		return convergenza;
	}

	public void setConvergenza(Boolean convergenza) {
		this.convergenza = convergenza;
	}

	public CampaignState getState() {
		return state;
	}

	public void setState(CampaignState state) {
		this.state = state;
	}

	public List<EnabledUser> getEnabledUsers() {
		return enabledUsers;
	}

	public void setEnabledUsers(List<EnabledUser> enabledUsers) {
		this.enabledUsers = enabledUsers;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}



}
