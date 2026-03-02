
package acme.entities.strategies;

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
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
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

	@Optional
	@ValidUrl
	@Column
	private String				moreInfo;

	// Atributos derivados: ------------------------------------------------------------


	@Transient
	@Valid
	public Double getMonthsActive() {
		return 0.0;
	}


	@Transient
	@Autowired
	private StrategyRepository repository;


	@Transient
	public Double getExpectedPercentage() {
		Double res;
		Double repo = this.repository.getExpectedPercentage(this.getId());

		res = repo == null ? 0 : repo.doubleValue();
		return res;
	}

	// Relaciones: ---------------------------------------------------------------------


	@Mandatory
	@Column
	@Valid
	Boolean				draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Fundraiser	fundraiser;

	/*
	 * @Valid
	 * private List<Tactic> tactics;
	 */
}
