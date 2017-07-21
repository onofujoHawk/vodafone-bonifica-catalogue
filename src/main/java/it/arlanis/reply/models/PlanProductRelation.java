package it.arlanis.reply.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "plan_product_relation")
public class PlanProductRelation extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_product_relation_id")
	private Long planProductRelationId;

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

	@Column(name = "new_value")
	private Boolean newValue;

	@Column(name = "included")
	private Boolean included;

	public Long getId() {
		return planProductRelationId;
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

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public Boolean getNewValue() {
		return newValue;
	}

	public void setNewValue(Boolean newValue) {
		this.newValue = newValue;
	}

	public Boolean getIncluded() {
		return included;
	}

	public void setIncluded(Boolean included) {
		this.included = included;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((planProductRelationId == null) ? 0 : planProductRelationId
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PlanProductRelation))
			return false;
		PlanProductRelation other = (PlanProductRelation) obj;
		if (planProductRelationId == null) {
			if (other.planProductRelationId != null)
				return false;
		} else if (!planProductRelationId.equals(other.planProductRelationId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanProductRelation{" +
				"planProductRelationId=" + planProductRelationId +
				", plan=" + plan +
				", product=" + product +
				", parameter=" + parameter +
				", value=" + value +
				", newValue=" + newValue +
				", included=" + included +
				'}';
	}

}
