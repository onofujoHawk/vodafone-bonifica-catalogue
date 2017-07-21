package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="offer")
public class Offer extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offer_id")
	private Long offerId;
	
	@Column(name="code", unique=true)
	private String code;
	
	@Column(name="description")
	private String description;
	
	@Column(name="name", length=247, unique=true)
	private String name;
	
	@Column(name="offer_activation_type")
	@Enumerated(value = EnumType.STRING)
	private OfferActivationType offerActivationType;
	
	@Column(name="market")
	@Enumerated(value = EnumType.STRING)
	private MarketType market;
	
	@Column(name="start_date")
	private Date startDate;
	
	@Column(name="end_date")
	private Date endDate;
	
	@Column(name="state")
	@Enumerated(value = EnumType.STRING)
	private ComponentState state;
	
	@Column(name="offer_type")
	@Enumerated(value=EnumType.STRING)
	private OfferType offerType;
	
	@Column(name="offer_mobile_type")
	@Enumerated(value=EnumType.STRING)
	private OfferMobileType offerMobileType;
	
	public OfferMobileType getOfferMobileType() {
		return offerMobileType;
	}

	public void setOfferMobileType(OfferMobileType offerMobileType) {
		this.offerMobileType = offerMobileType;
	}


	@ManyToMany
    @JoinTable(name="offer_payment_relation",
            joinColumns=@JoinColumn(name="offer", referencedColumnName="offer_id"),
            inverseJoinColumns=@JoinColumn(name="payment", referencedColumnName="payment_id")
    )
	private List<Payment> payments=new ArrayList<Payment>();
	
	@OneToMany(mappedBy = "offer")
	private List<OfferInCampaign> offersInCampaign=new ArrayList<OfferInCampaign>();
	
	@OneToMany(mappedBy = "offer")
	private List<ProductInOffer> productsInOffer=new ArrayList<ProductInOffer>();
	

	public Long getId() {
		return offerId;
	}
	
	public Long getOfferId() {
		return offerId;
	}
	
	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OfferActivationType getOfferActivationType() {
		return offerActivationType;
	}

	public void setOfferActivationType(OfferActivationType type) {
		this.offerActivationType = type;
	}

	public MarketType getMarket() {
		return market;
	}

	public void setMarket(MarketType market) {
		this.market = market;
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
		if(endDate == null) {
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

	public ComponentState getState() {
		return state;
	}

	public void setState(ComponentState state) {
		this.state = state;
	}
	
	public List<OfferInCampaign> getOffersInCampaign() {
		return offersInCampaign;
	}

	public void setOffersInCampaign(List<OfferInCampaign> offersInCampaign) {
		this.offersInCampaign = offersInCampaign;
	}

	public List<ProductInOffer> getProductsInOffer() {
		return productsInOffer;
	}
	
	public Plan getPlan(String type) {
		Plan res = null;
		PlanType planType;
		
		if (type.equalsIgnoreCase("sim")) {
			planType = PlanType.SIM;
		} else {
			planType = PlanType.PBX;
		}
		
		if (this.productsInOffer != null) {
			for (ProductInOffer prod : this.productsInOffer) {
				if ((prod.getPlan() != null) && (prod.getPlan().getType() == planType)) {
					res = prod.getPlan();
					break;
				}
			}
		}
		
		return res;
	}
	
	public void setProductsInOffer(List<ProductInOffer> productsInOffer) {
		this.productsInOffer = productsInOffer;
	}
	
	public OfferType getOfferType() {
		return offerType;
	}

	public void setOfferType(OfferType offerType) {
		this.offerType = offerType;
	}
	
	
	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((offerId == null) ? 0 : offerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Offer))
			return false;
		Offer other = (Offer) obj;
		if (offerId == null) {
			if (other.offerId != null)
				return false;
		} else if (!offerId.equals(other.offerId))
			return false;
		return true;
	}

}
