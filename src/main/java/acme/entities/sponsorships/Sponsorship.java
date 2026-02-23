
package acme.entities.sponsorships;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.sponsorships.SponsorshipRepository;
import acme.realms.Sponsor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
// @ValidSponsorship
public class Sponsorship extends AbstractEntity {

	private static final long		serialVersionUID	= 1L;

	@Transient
	@Autowired
	private SponsorshipRepository	repository;

	@Mandatory
	@ValidTicker
	@Column(unique = true)
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

	@Optional
	@ValidUrl
	@Column
	private String					moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean					draftMode;

	// Relaciones
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Sponsor					sponsor;


	// Derivadas --------------------------------------------------
	@Valid
	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;

		Date current = this.startMoment;
		double months = 0.0;

		// Iteramos mes a mes hasta llegar a endMoment
		while (MomentHelper.isBefore(current, this.endMoment)) {
			// Avanzamos un mes
			Date nextMonth = MomentHelper.deltaFromMoment(current, 1, ChronoUnit.MONTHS);

			// Si nextMonth supera endMoment, usamos endMoment
			Date monthEnd = MomentHelper.isBefore(nextMonth, this.endMoment) ? nextMonth : this.endMoment;

			// Duración de este mes parcial
			long daysInMonth = MomentHelper.computeDuration(current, nextMonth).toDays();
			long daysInPeriod = MomentHelper.computeDuration(current, monthEnd).toDays();

			months += (double) daysInPeriod / (double) daysInMonth;

			// Avanzamos al siguiente mes
			current = monthEnd;
		}

		// Redondeamos a un decimal
		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Money getTotalMoney() {
		Double amount = this.repository.sumTotalMoneyBySponsorshipId(this.getId());

		Money total = new Money();
		total.setAmount(amount);
		total.setCurrency("EUR");
		return total;
	}
}
