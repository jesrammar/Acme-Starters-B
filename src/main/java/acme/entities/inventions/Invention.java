
package acme.entities.inventions;

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
import acme.constraints.ValidHeader;
import acme.constraints.ValidInvention;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.inventions.InventionRepository;
import acme.realms.Inventor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidInvention
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
	@ValidText
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

	@Transient
	@Autowired
	private InventionRepository	repository;


	// @Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {
		// ! For the moment, return 0.0

		//		if (this.startMoment == null || this.endMoment == null)
		//			return 0.0;
		//
		//		Date current = this.startMoment;
		//		double months = 0.0;
		//
		//		// Iteramos mes a mes hasta llegar a endMoment
		//		while (MomentHelper.isBefore(current, this.endMoment)) {
		//			// Avanzamos un mes
		//			Date nextMonth = MomentHelper.deltaFromMoment(current, 1, ChronoUnit.MONTHS);
		//
		//			// Si nextMonth supera endMoment, usamos endMoment
		//			Date monthEnd = MomentHelper.isBefore(nextMonth, this.endMoment) ? nextMonth : this.endMoment;
		//
		//			// Duración de este mes parcial
		//			long daysInMonth = MomentHelper.computeDuration(current, nextMonth).toDays();
		//			long daysInPeriod = MomentHelper.computeDuration(current, monthEnd).toDays();
		//
		//			months += (double) daysInPeriod / (double) daysInMonth;
		//
		//			// Avanzamos al siguiente mes
		//			current = monthEnd;
		//		}
		//
		//		// Redondeamos a un decimal
		//		return Math.round(months * 10.0) / 10.0;

		return 0.0;
	}

	//	@Mandatory
	@Transient
	public Money getCost() {
		Money result = new Money();
		result.setCurrency("EUR");

		Double amount = this.repository.sumPartsCostByInventionId(this.getId());
		result.setAmount(amount == null ? 0. : amount);

		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Inventor inventor;

}
