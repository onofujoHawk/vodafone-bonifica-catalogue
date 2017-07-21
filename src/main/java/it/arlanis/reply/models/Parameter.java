package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.ParameterType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="parameter")
public class Parameter extends AbstractEagleCatalogueObject implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="parameter_id")
	private Long parameterId;
	
	@Column(name="code", unique=true)
	private String code;
	
	@Column(name="description")
	private String description;
	
	@Column(name="displayed_name")
	private String displayedName;
	
	@Column(name="type")
	@Enumerated(value = EnumType.STRING)
	private ParameterType type;

	@OneToOne(optional=true)
	@JoinColumn(name = "value", referencedColumnName = "value_id")
	private Value value;
	

	@OneToMany(mappedBy = "parameter")
	private List<ProductInOffer> productsInOffer=new ArrayList<ProductInOffer>();
	
	@OneToMany(mappedBy = "parameter")
	private List<OfferInCampaign> offersInCampaign=new ArrayList<OfferInCampaign>();
	
	@OneToMany(mappedBy = "parameter")
	private List<ProductParameterRelation> productParameterRelation=new ArrayList<ProductParameterRelation>();
	
	@OneToMany(mappedBy = "parameter")
	private List<PlanProductRelation> planProductRelation=new ArrayList<PlanProductRelation>();
	
	public Long getId() {
		return parameterId;
	}

	public List<ProductInOffer> getProductsInOffer() {
		return productsInOffer;
	}

	public void setProductsInOffer(List<ProductInOffer> productsInOffer) {
		this.productsInOffer = productsInOffer;
	}

	public Long getParameterId() {
		return parameterId;
	}

	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
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

	public String getDisplayedName() {
		return displayedName;
	}

	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}

	public ParameterType getType() {
		return type;
	}

	public void setType(ParameterType type) {
		this.type = type;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public List<OfferInCampaign> getOffersInCampaign() {
		return offersInCampaign;
	}

	public void setOffersInCampaign(List<OfferInCampaign> offersInCampaign) {
		this.offersInCampaign = offersInCampaign;
	}

	public List<ProductParameterRelation> getProductParameterRelation() {
		return productParameterRelation;
	}

	public void setProductParameterRelation(List<ProductParameterRelation> productParameterRelation) {
		this.productParameterRelation = productParameterRelation;
	}

	public List<PlanProductRelation> getPlanProductRelation() {
		return planProductRelation;
	}

	public void setPlanProductRelation(List<PlanProductRelation> planProductRelation) {
		this.planProductRelation = planProductRelation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parameterId == null) ? 0 : parameterId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Parameter))
			return false;
		Parameter other = (Parameter) obj;
		if (parameterId == null) {
			if (other.parameterId != null)
				return false;
		} else if (!parameterId.equals(other.parameterId))
			return false;
		return true;
	}

}
