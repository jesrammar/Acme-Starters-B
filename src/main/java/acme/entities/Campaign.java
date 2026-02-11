package acme.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Moment;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.validators.ValidHeader;
import acme.validators.ValidText;
import acme.validators.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Campaign extends AbstractEntity {

	@Mandatory
	@ValidTicker
	@Column(unique = true)
	private String ticker;

	@Mandatory
	@ValidHeader
	@Column
	private String name;

	@Mandatory
	@ValidText
	@Column
	private String description;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Column
	private Moment startMoment;

	@Mandatory
	@ValidMoment(constraint = ValidMoment.Constraint.ENFORCE_FUTURE)
	@Column
	private Moment endMoment;

	@ValidUrl
	@Column
	private String moreInfo;

	@Mandatory
	@Valid
	@Transient
	private Double monthsActive;

	@Mandatory
	@ValidNumber(min = 0.0)
	@Transient
	private Double effort;

	@Mandatory
	@Valid
	@Column
	private Boolean draftMode;

	@ManyToOne(optional = false)
	private Spokesperson spokesperson;

	@OneToMany(mappedBy = "campaign")
	private Collection<Milestone> milestones;
}
