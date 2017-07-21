package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.ComponentState;
import it.arlanis.reply.models.enums.MarketType;
import it.arlanis.reply.models.enums.MaterialType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "material")
public class Material extends AbstractEagleCatalogueObject implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "material_id")
	private Long materialId;

	@Column(name = "material_code", unique=true, length=18)
	private String materialCode;

	@Column(name = "description")
	private String description;

	@Column(name = "displayed_name")
	private String displayedName;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "market")
	@Enumerated(value = EnumType.STRING)
	private MarketType market;
	
	@Column(name="type")
	@Enumerated(value = EnumType.STRING)
	private MaterialType type;

	@Column(name = "state")
	@Enumerated(value = EnumType.STRING)
	private ComponentState state;

	@ManyToMany(mappedBy="materials")
	@Column(nullable=true)
	private List<Plan> plans=new ArrayList<Plan>();
	
	@ManyToMany(mappedBy="materials")
	@Cascade({CascadeType.DELETE})
	@Column(nullable=true)
	private List<ProductInOffer> productsInOffer=new ArrayList<ProductInOffer>();
	
	public List<ProductInOffer> getProductsInOffer() {
		return productsInOffer;
	}

	public void setProductsInOffer(List<ProductInOffer> productsInOffer) {
		this.productsInOffer = productsInOffer;
	}

	public Material() {

	}

	public Long getId() {
		return materialId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
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

	public MarketType getMarket() {
		return market;
	}

	public void setMarket(MarketType market) {
		this.market = market;
	}

	public MaterialType getType() {
		return type;
	}

	public void setType(MaterialType type) {
		this.type = type;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((materialId == null) ? 0 : materialId.hashCode());
		return result;
	}

	public static int FIELDS_NUMBER=8;
	public static int CODE_POSITION=0;
	public static int STATE_POSITION=1;
	public static int DESCRIPTION_POSITION=2;
	public static int MARKET_POSITION=3;
	public static int DISPLAYED_NAME_POSITION=4;
	public static int TYPE_POSITION=5;
	public static int START_DATE_POSITION=6;
	public static int END_DATE_POSITION=7;
}
