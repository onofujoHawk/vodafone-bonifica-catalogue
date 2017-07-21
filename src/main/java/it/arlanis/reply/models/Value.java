package it.arlanis.reply.models;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="value")
public class Value extends AbstractEagleCatalogueObject{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="value_id")
	private Long valueId;
	
	@Column(name="type")
	private String type;

	
	@Column(name="value")
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "parameter", referencedColumnName = "parameter_id")
	private Parameter parameter;
	
	@OneToMany(mappedBy="value")
	private List<OfferInCampaign> offersInCampaign;
	
	@OneToMany(mappedBy="value")
	private List<ProductInOffer> productsInOffer;
	
	@OneToMany(mappedBy="value")
	private List<PlanProductRelation> planProductRelations;
	
	@OneToMany(mappedBy="value")
	private List<ProductParameterRelation> productParameterRelation;
	
	@OneToOne(optional=true)
	@JoinColumn(name = "father", referencedColumnName="value_id") 
	private Value father;
	
	public Long getId() {
		return valueId;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}

	public Value getFather() {
		return father;
	}

	public void setFather(Value father) {
		this.father = father;
	}

	public void setValue(String value) {
		this.value = value;
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

	public void setProductsInOffer(List<ProductInOffer> productsInOffer) {
		this.productsInOffer = productsInOffer;
	}

	public List<PlanProductRelation> getPlanProductRelations() {
		return planProductRelations;
	}

	public void setPlanProductRelations(List<PlanProductRelation> planProductRelations) {
		this.planProductRelations = planProductRelations;
	}

	public List<ProductParameterRelation> getProductParameterRelation() {
		return productParameterRelation;
	}

	public void setProductParameterRelation(List<ProductParameterRelation> productParameterRelation) {
		this.productParameterRelation = productParameterRelation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valueId == null) ? 0 : valueId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Value))
			return false;
		Value other = (Value) obj;
		if (valueId == null) {
			if (other.valueId != null)
				return false;
		} else if (!valueId.equals(other.valueId))
			return false;
		return true;
	}

}
