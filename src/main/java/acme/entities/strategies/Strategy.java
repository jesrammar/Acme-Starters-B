
package acme.entities.strategies;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidHeader;
import acme.constraints.ValidStrategy;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
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
	@Column(unique = true)
	@ValidTicker
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
	@Transient
	@Mandatory
	private Double				monthsActive;


	public Double getMonthsActive() {
		if (this.startMoment != null && this.endMoment != null) {
			long days = (this.endMoment.getTime() - this.startMoment.getTime()) / (1000 * 60 * 60 * 24);
			double months = days / 30.0;
			return Math.round(months * 10.0) / 10.0; // redondeo a un decimal
		}
		return 0.0;
	}


	@Transient
	@Mandatory
	@ValidScore
	private Double expectedPercentage;


	public Double getExpectedPercentage() {
		if (this.tactics != null)
			return this.tactics.stream().mapToDouble(Tactic::getExpectedPercentage).sum();
		return 0.0;
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
	@OneToMany(mappedBy = "strategy")
	private Collection<Tactic>	tactics;
}
