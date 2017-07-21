package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="offer_in_campaign")
public class OfferInCampaign extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offer_in_campaign_id")
	private Long offerInCampaignId;

	@ManyToOne
	@JoinColumn(name = "campaign", referencedColumnName = "campaign_id")
	private Campaign campaign;
	
	@ManyToOne
	@JoinColumn(name = "offer", referencedColumnName = "offer_id")
	private Offer offer;
	
	@ManyToOne
	@JoinColumn(name = "plan", referencedColumnName = "plan_id")
	private Plan plan;
	
	@ManyToOne
	@JoinColumn(name = "product", referencedColumnName = "product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "parameter", referencedColumnName = "parameter_id")
	private Parameter parameter;
	
	@ManyToOne
	@JoinColumn(name = "material", referencedColumnName = "material_id")
	private Material material;
	
	@ManyToOne
	@JoinColumn(name = "value", referencedColumnName = "value_id")
	private Value value;
	
	@ManyToOne
	@JoinColumn(name = "payment", referencedColumnName = "payment_id")
	private Payment payment;
	
	@Column(name="included")
	private Boolean included;
	
	@Column(name = "new_value")
	private Boolean newValue;
	
	public Long getId() {
		return offerInCampaignId;
	}

	public Long getOfferInCampaignId() {
		return offerInCampaignId;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}


	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Boolean getIncluded() {
		return included;
	}

	public void setIncluded(Boolean included) {
		this.included = included;
	}

	public Boolean getNewValue() {
		return newValue;
	}

	public void setNewValue(Boolean newValue) {
		this.newValue = newValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offerInCampaignId == null) ? 0 : offerInCampaignId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OfferInCampaign))
			return false;
		OfferInCampaign other = (OfferInCampaign) obj;
		if (offerInCampaignId == null) {
			if (other.offerInCampaignId != null)
				return false;
		} else if (!offerInCampaignId.equals(other.offerInCampaignId))
			return false;
		return true;
	}

}
