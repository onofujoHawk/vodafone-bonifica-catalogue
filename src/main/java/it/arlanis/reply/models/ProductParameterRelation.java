package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name="product_parameter_relation",uniqueConstraints=@UniqueConstraint(columnNames={"product", "parameter"}))
public class ProductParameterRelation extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_parameter_relation_id")
	private Long productParameterRelationId;
	
	@ManyToOne
	@JoinColumn(name = "product", referencedColumnName = "product_id")
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "parameter", referencedColumnName = "parameter_id")
	private Parameter parameter;
	
	@ManyToOne
	@JoinColumn(name = "value", referencedColumnName = "value_id")
	private Value value;
	
	@Column(name="new_value")
	private Boolean newValue;
	
	public Long getId() {
		return productParameterRelationId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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
		result = prime * result + ((productParameterRelationId == null) ? 0 : productParameterRelationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProductParameterRelation))
			return false;
		ProductParameterRelation other = (ProductParameterRelation) obj;
		if (productParameterRelationId == null) {
			if (other.productParameterRelationId != null)
				return false;
		} else if (!productParameterRelationId.equals(other.productParameterRelationId))
			return false;
		return true;
	}

}
