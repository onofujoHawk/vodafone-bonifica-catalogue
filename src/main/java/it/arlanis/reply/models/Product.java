package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.ComponentState;
import it.arlanis.reply.models.enums.MarketType;
import it.arlanis.reply.models.enums.ProductType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "code", unique = true)
	private Long code;

	@Column(name = "name")
	private String name;

	@Column(name = "market")
	@Enumerated(value = EnumType.STRING)
	private MarketType market;

	@Column(name = "state")
	@Enumerated(value = EnumType.STRING)
	private ComponentState state;

	@Column(name = "extraInfo")
	private String extraInfo;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private ProductType type;

	@OneToOne(optional = true)
	@JoinColumn(name = "father", referencedColumnName = "product_id")
	private Product father;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@OneToMany(mappedBy = "product")
	private List<PlanProductRelation> planProductRelation = new ArrayList<PlanProductRelation>();

	@OneToMany(mappedBy = "product")
	private List<ProductParameterRelation> productParameterRelation = new ArrayList<ProductParameterRelation>();

	@OneToMany(mappedBy = "product")
	private List<ProductInOffer> productsInOffer = new ArrayList<ProductInOffer>();

	public Long getId() {
		return productId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MarketType getMarket() {
		return market;
	}

	public void setMarket(MarketType market) {
		this.market = market;
	}

	public ComponentState getState() {
		return state;
	}

	public void setState(ComponentState state) {
		this.state = state;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Product getFather() {
		return father;
	}

	public void setFather(Product father) {
		this.father = father;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public List<ProductInOffer> getProductsInOffer() {
		return productsInOffer;
	}

	public List<PlanProductRelation> getPlanProductRelation() {
		return planProductRelation;
	}

	public void setPlanProductRelation(List<PlanProductRelation> planProductRelation) {
		this.planProductRelation = planProductRelation;
	}

	public void setProductsInOffer(List<ProductInOffer> productsInOffer) {
		this.productsInOffer = productsInOffer;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public List<ProductParameterRelation> getProductParameterRelation() {
		return productParameterRelation;
	}

	public void setProductParameterRelation(List<ProductParameterRelation> productParameterRelation) {
		this.productParameterRelation = productParameterRelation;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}


	public static int FIELDS_NUMBER = 10;
	public static int CODE_POSITION = 0;
	public static int STATE_POSITION = 1;
	public static int MARKET_POSITION = 2;
	public static int NAME_POSITION = 3;
	public static int PRODUCT_PARAMETER_RELATION_POSITION = 4;
	public static int START_DATE_POSITION = 5;
	public static int END_DATE_POSITION = 6;
	public static int TYPE_POSITION = 7;
	// public static int EXTRA_INFO = 8;
	public static int DESCRIPTION_POSITION = 8;
	public static int FATHER_POSITION = 9;

}
