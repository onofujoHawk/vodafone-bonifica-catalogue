package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.ComponentState;
import it.arlanis.reply.models.enums.MarketType;
import it.arlanis.reply.models.enums.PlanType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "plan")
public class Plan extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "plan_id")
	private Long planId;

	@Column(name = "code", unique = true)
	private Long code;

	@Column(name = "description")
	private String description;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private PlanType type;

	@Column(name = "market")
	@Enumerated(value = EnumType.STRING)
	private MarketType market;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "state")
	@Enumerated(value = EnumType.STRING)
	private ComponentState state;

	@ManyToMany
	@JoinTable(name = "plan_material_relation", joinColumns = @JoinColumn(name = "plan", referencedColumnName = "plan_id"), inverseJoinColumns = @JoinColumn(name = "material", referencedColumnName = "material_id"))
	private List<Material> materials = new ArrayList<Material>();

	@OneToMany(mappedBy = "plan")
	private List<ProductInOffer> productsInOffer = new ArrayList<ProductInOffer>();

	@OneToMany(mappedBy = "plan")
	private List<PlanProductRelation> planProductRelation = new ArrayList<PlanProductRelation>();

	public Long getId() {
		return planId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
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

	public PlanType getType() {
		return type;
	}

	public void setType(PlanType type) {
		this.type = type;
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

	public List<Product> getProducts() {
		List<Product> listProducts = new ArrayList<Product>();
		for (PlanProductRelation ppr : planProductRelation) {
			listProducts.add(ppr.getProduct());
		}
		return listProducts;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public List<PlanProductRelation> getPlanProductRelation() {
		return planProductRelation;
	}

	public void setPlanProductRelation(
			List<PlanProductRelation> planProductRelation) {
		this.planProductRelation = planProductRelation;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public void addMaterial(Material d) {
		this.materials.add(d);
	}

	public List<ProductInOffer> getProductsInOffer() {
		return productsInOffer;
	}

	public void setProductsInOffer(List<ProductInOffer> productsInOffer) {
		this.productsInOffer = productsInOffer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((planId == null) ? 0 : planId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Plan))
			return false;
		Plan other = (Plan) obj;
		if (planId == null) {
			if (other.planId != null)
				return false;
		} else if (!planId.equals(other.planId))
			return false;
		return true;
	}


	public static int FIELDS_NUMBER = 10;
	public static int CODE_POSITION = 0;
	public static int STATE_POSITION = 1;
	public static int MARKET_POSITION = 2;
	public static int NAME_POSITION = 3;
	public static int START_DATE_POSITION = 4;
	public static int END_DATE_POSITION = 5;
	public static int DESCRIPTION_POSITION = 6;
	public static int TYPE_POSITION = 7;
	public static int MATERIALS_POSITION = 8;
	public static int PRODUCTS_POSITION = 9;

}
