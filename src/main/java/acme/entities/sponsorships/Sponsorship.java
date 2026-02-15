
package acme.entities.sponsorships;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidSponsorship;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidSponsorship
public class Sponsorship extends AbstractEntity {

	private static final long		serialVersionUID	= 1L;

	@Mandatory
	@Column(unique = true)
	@ValidTicker
	private String					ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String					name;

	@Mandatory
	@ValidText
	@Column
	private String					description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date					endMoment;

	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Column
	@Valid
	private Boolean					draftMode;

	// Relaciones
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor					sponsor;

	@Valid
	@OneToMany(mappedBy = "sponsorship")
	private Collection<Donation>	donations;

	// Derivadas --------------------------------------------------
	@Transient
	@Mandatory
	private Double					monthsActive;


	public Double getMonthsActive() {
		if (this.startMoment != null && this.endMoment != null) {
			long diff = this.endMoment.getTime() - this.startMoment.getTime();
			double months = diff / (1000.0 * 60 * 60 * 24 * 30);
			return Math.round(months * 10.0) / 10.0;
		}
		return 0.0;
	}


	@Transient
	@Mandatory
	@ValidMoney(min = 0.0)
	private Money totalMoney;


	public Money getTotalMoney() {
		Money result = new Money();
		result.setCurrency("EUR");
		double total = 0.0;
		if (this.donations != null)
			for (Donation d : this.donations)
				total += d.getMoney().getAmount();
		result.setAmount(total);
		return result;
	}
}
