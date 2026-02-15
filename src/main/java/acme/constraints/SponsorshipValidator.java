
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.entities.sponsorships.Sponsorship;

public class SponsorshipValidator implements ConstraintValidator<ValidSponsorship, Sponsorship> {

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {

		if (sponsorship == null)
			return true;

		boolean valid = true;

		if (sponsorship.getDonations() == null || sponsorship.getDonations().isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Sponsorship must have at least one sponsor").addPropertyNode("donations").addConstraintViolation();
			valid = false;
		}

		Date now = new Date();
		Date start = sponsorship.getStartMoment();
		Date end = sponsorship.getEndMoment();

		if (start != null && start.before(now)) {
			context.buildConstraintViolationWithTemplate("startMoment must be in the future").addPropertyNode("startMoment").addConstraintViolation();
			valid = false;
		}

		if (end != null && end.before(now)) {
			context.buildConstraintViolationWithTemplate("endMoment must be in the future").addPropertyNode("endMoment").addConstraintViolation();
			valid = false;
		}

		// start < end
		if (start != null && end != null && start.after(end)) {
			context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("startMoment").addConstraintViolation();
			valid = false;
		}

		return valid;
	}

}
