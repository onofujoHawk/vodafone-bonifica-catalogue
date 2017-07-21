package it.arlanis.reply.models;

import it.arlanis.reply.models.enums.PaymentType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="payment")
public class Payment extends AbstractEagleCatalogueObject implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long paymentId;
	
	@Column(name="payment_name")
	@Enumerated(value = EnumType.STRING)
	private PaymentType paymentName;
	
	@ManyToMany(mappedBy="payments")
	@Column(nullable=true)
	private List<Offer> offers=new ArrayList<Offer>();

	
	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}

	public PaymentType getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(PaymentType paymentName) {
		this.paymentName = paymentName;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public Long getId() {
		return paymentId;
	}
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paymentId == null) ? 0 : paymentId.hashCode());
		return result;
	}

	@Override
	public boolean equals(java.lang.Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Payment))
			return false;
		Payment other = (Payment) obj;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		return true;
	}


}
