package it.arlanis.reply.models;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="product_in_offer")
public class ProductInOffer extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_in_offer_id")
	private Long productInOfferId;
	
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
	@JoinColumn(name = "value", referencedColumnName = "value_id")
	private Value value;
	
	@Column(name="is_new_value")
	private Boolean isNewValue;
	
	public Boolean getIsNewValue() {
		return isNewValue;
	}

	public void setIsNewValue(Boolean isNewValue) {
		this.isNewValue = isNewValue;
	}

	@ManyToMany
	@Cascade({CascadeType.DELETE})
    @JoinTable(name="product_in_offer_material_relation",
            joinColumns=@JoinColumn(name="product_in_offer", referencedColumnName="product_in_offer_id"),
            inverseJoinColumns=@JoinColumn(name="material", referencedColumnName="material_id")
    )
	private List<Material> materials=new ArrayList<Material>();
	
	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	// incluso o delegato
	@Column(name="incluso")
	private Boolean included;
	
	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public Boolean getIncluded() {
		return included;
	}

	public void setIncluded(Boolean included) {
		this.included = included;
	}
	
	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public Long getId() {
		return productInOfferId;
	}

	public Long getProductInOfferId() {
		return productInOfferId;
	}


	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productInOfferId == null) ? 0 : productInOfferId.hashCode());
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
		ProductInOffer other = (ProductInOffer) obj;
		if (productInOfferId == null) {
			if (other.productInOfferId != null)
				return false;
		} else if (!productInOfferId.equals(other.productInOfferId))
			return false;
		return true;
	}


}