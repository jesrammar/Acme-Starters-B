
package acme.entities;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoment.Constraint;
import acme.client.components.validation.ValidUrl;
import acme.roles.Fundraiser;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Strategy extends AbstractEntity {

	@Mandatory
	@Column(unique = true)
	//ValidTicker:
	@NotBlank
	@Pattern(regexp = "^[A-Z]{2}[0-9]{2}-\\\\w{5,10}$")
	String		ticker;

	@Mandatory
	//ValidHeader:
	@NotBlank
	@Length(min = 1, max = 75)
	String		name;

	@Mandatory
	//ValidText:
	@Length(min = 1, max = 255)
	@NotBlank
	String		description;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(value = TemporalType.TIMESTAMP)
	Date		startMoment;

	@Mandatory
	@ValidMoment(constraint = Constraint.ENFORCE_FUTURE)
	@Temporal(value = TemporalType.TIMESTAMP)
	Date		endMoment;

	@ValidUrl
	String		moreInfo;

	//Derivada
	@Mandatory
	Double		monthsActive;

	//Derivada
	@Mandatory
	Double		expectedPercentage;

	@Mandatory
	Boolean		draftMode;

	@Mandatory
	Fundraiser	fundraiser;


	@Transient
	public Double getMonthsActive() {
		if (this.startMoment == null || this.endMoment == null)
			return 0.0;
		long diffInMillies = Math.abs(this.endMoment.getTime() - this.startMoment.getTime());
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		// Aproximación simple de meses (30 días)
		return (double) Math.round(diff / 30.0 * 10) / 10;
	}


	List<Tactic> tactics;
}
