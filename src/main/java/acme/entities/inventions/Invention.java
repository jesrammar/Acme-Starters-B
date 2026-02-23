
package acme.entities.inventions;

import java.beans.Transient;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidTicker;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Invention extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	// Private attributes

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String				ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String				name;

	@Mandatory
	@ValidHeader
	@Column
	private String				description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				endMoment;

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean				draftMode;

	// Derivated attributes ----------------------------------------------------------


	// @Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		long diff = this.endMoment.getTime() - this.startMoment.getTime();
		double months = diff / (1000 * 60 * 60 * 24 * 30);
		return Math.round(months * 10.0) / 10.0;	// Redondeo al decimal más cercano
	}

	//	@Mandatory
	//	@ValidMoney(min = 0)
	@Transient
	public Money getCost() {
		Money res = null;
		Double totalAmount = this.parts.stream().mapToDouble(part -> part.getCost().getAmount()).sum();
		res.setAmount(totalAmount);
		// WIP
		String finalCurrency = this.parts.stream().findFirst().get().getCost().getCurrency();
		res.setCurrency(finalCurrency);
		return res;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@ManyToOne(optional = false)
	private Inventor			inventor;

	@Mandatory
	@OneToMany(mappedBy = "part")
	private Collection<Part>	parts;
}
