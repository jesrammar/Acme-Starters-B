
package acme.entities.audit;

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
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MathHelper;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidAuditReport;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import acme.features.auditor.auditReport.AuditReportRepository;
import acme.realms.Auditor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAuditReport
public class AuditReport extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

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
	@ValidMoment
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date					startMoment;

	@Mandatory
	@ValidMoment
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

	// Derived attributes -----------------------------------------------------

	@Transient
	@Autowired
	private AuditReportRepository	repository;


	@Mandatory
	@Valid
	@Transient
	public Double getMonthsActive() {

		if (this.startMoment == null || this.endMoment == null || !MomentHelper.isAfter(this.endMoment, this.startMoment))
			return 0.0;

		double months = MomentHelper.computeDifference(this.startMoment, this.endMoment, ChronoUnit.MONTHS);

		months = MathHelper.roundOff(months, 2);

		return Math.max(0.0, months);
	}

	@Mandatory
	@ValidNumber(min = 0)
	@Transient
	public Integer getHours() {

		Integer result;

		result = this.repository.sumHoursByReportId(this.getId());

		return result == null ? 0 : result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Auditor auditor;

}
