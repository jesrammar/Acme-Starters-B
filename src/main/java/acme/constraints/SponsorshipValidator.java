
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.helpers.MomentHelper;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsorships.sponsorship.SponsorshipRepository;

public class SponsorshipValidator implements ConstraintValidator<ValidSponsorship, Sponsorship> {

	@Autowired
	private SponsorshipRepository repository;


	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {

		if (sponsorship == null)
			return true;

		boolean valid = true;
		context.disableDefaultConstraintViolation();

		if (Boolean.FALSE.equals(sponsorship.getDraftMode())) {
			if (sponsorship.getId() != 0) {
				long count = this.repository.countDonationsBySponsorshipId(sponsorship.getId());

				if (count == 0) {
					context.buildConstraintViolationWithTemplate("Sponsorship must have at least one donation").addPropertyNode("draftMode").addConstraintViolation();
					valid = false;
				}
			}

			if (!MomentHelper.isFuture(sponsorship.getStartMoment())) {
				context.buildConstraintViolationWithTemplate("startMoment must be in the future").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}

			if (!MomentHelper.isFuture(sponsorship.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("endMoment must be in the future").addPropertyNode("endMoment").addConstraintViolation();
				valid = false;
			}

			if (!MomentHelper.isBefore(sponsorship.getStartMoment(), sponsorship.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}
		}
		return valid;
	}
}
