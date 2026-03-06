package acme.entities.campaigns;

import java.time.LocalDate;
import java.time.ZoneId;
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
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.client.helpers.MomentHelper;
import acme.constraints.ValidCampaignPublishable;
import acme.constraints.ValidHeader;
import acme.constraints.ValidText;
import acme.constraints.ValidTicker;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidCampaignPublishable
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
	@ValidMoment
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date startMoment;

	@Mandatory
	@ValidMoment
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date endMoment;

	@Optional
	@ValidUrl
	@Column
	private String moreInfo;

	@Mandatory
	@Valid
	@Column
	private Boolean draftMode;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Spokesperson spokesperson;



	@Transient
	@Autowired
	private CampaignRepository repository;

	
	// Mandatory
	// ValidNumber(min = 0.0)
	@Transient
	public Double getMonthsActive() {
//		if (this.startMoment == null || this.endMoment == null || MomentHelper.isAfter(this.startMoment, this.endMoment)) {
//			return 0.0;
//		}
//
//		ZoneId zoneId = ZoneId.systemDefault();
//		LocalDate startDate = this.startMoment.toInstant().atZone(zoneId).toLocalDate();
//		LocalDate endDate = this.endMoment.toInstant().atZone(zoneId).toLocalDate();
//
//		if (endDate.isBefore(startDate)) {
//			return 0.0;
//		}
//
//		LocalDate cursor = startDate;
//		int fullMonths = 0;
//		while (!cursor.plusMonths(1).isAfter(endDate)) {
//			cursor = cursor.plusMonths(1);
//			fullMonths++;
//		}
//
//		long remainingDays = ChronoUnit.DAYS.between(cursor, endDate);
//		int monthLength = Math.max(1, cursor.lengthOfMonth());
//		double fraction = remainingDays / (double) monthLength;
//		double months = fullMonths + fraction;

		return 0.0;
	}
	
	// Mandatory
	// ValidNumber(min = 0.0)
	@Transient
	private Double getEffort() {
		Double wrapper = this.repository.computeCampaignEffort(this.getId());
		return wrapper == null ? 0.0 : wrapper.doubleValue();
	}
}
