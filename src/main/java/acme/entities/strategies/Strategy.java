
package acme.entities.strategies;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.strategies.StrategyRepository;
import acme.realms.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidStrategy
public class Strategy extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

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
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date				startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date				endMoment;

	@ValidUrl
	@Column
	private String				moreInfo;

	// Derivadas ------------------------------------------------------------


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
	@Autowired
	private StrategyRepository repository;


	@Transient
	//@ValidScore
	public Double getExpectedPercentage() {
		Double res;
		Double repo = this.repository.getExpectedPercentage(this.getId());

		res = repo == null ? 0 : repo.doubleValue();
		return res;
	}


	// ---------------------------------------------------------------------
	@Mandatory
	@Column
	@Valid
	Boolean						draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser			fundraiser;

	@Valid
	private Collection<Tactic>	tactics;
}
