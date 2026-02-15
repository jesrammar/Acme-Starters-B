package acme.entities;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

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
	@ValidNumber(min = 0.01)
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

	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null) {
			return 0.0;
		}

		long diffMillis = Math.abs(this.endMoment.getTime() - this.startMoment.getTime());
		long diffDays = TimeUnit.MILLISECONDS.toDays(diffMillis);
		double months = diffDays / 30.0;

		return Math.round(months * 10.0) / 10.0;
	}

	@Transient
	public Double getEffort() {
		if (this.milestones == null || this.milestones.isEmpty()) {
			return 0.0;
		}

		double total = 0.0;
		for (Milestone milestone : this.milestones) {
			if (milestone != null && milestone.getEffort() != null) {
				total += milestone.getEffort();
			}
		}

		return total;
	}
}
