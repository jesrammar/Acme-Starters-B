
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsor.sponsorship.SponsorSponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship sponsorship, final ConstraintValidatorContext context) {

		assert context != null;
		boolean isValid = true;

		if (sponsorship == null)
			return true;

		// Validate if it is going to be published
		if (Boolean.FALSE.equals(sponsorship.getDraftMode())) {
			// Check duplicated sponsorship with equal ticker
			{
				boolean uniqueSponsorship;
				Sponsorship existingSponsorship;

				existingSponsorship = this.repository.findSponsorshipByTicker(sponsorship.getTicker());
				uniqueSponsorship = existingSponsorship == null || existingSponsorship.equals(sponsorship);

				super.state(context, uniqueSponsorship, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
			}
			// Check sponsorship has at least one donation
			{
				boolean hasSponsorshipAtLeastOneDonation;

				hasSponsorshipAtLeastOneDonation = this.repository.countDonationsBySponsorshipId(sponsorship.getId()) >= 1;

				super.state(context, hasSponsorshipAtLeastOneDonation, "*", "acme.validation.sponsorship.donations.message");
			}
			// Check dates are future
			{
				boolean startMomentIsFuture;

				startMomentIsFuture = MomentHelper.isFuture(sponsorship.getStartMoment());

				super.state(context, startMomentIsFuture, "startMoment", "acme.validation.sponsorship.startMoment-NotFuture.message");
			}
			{
				boolean endMomentIsFuture;

				endMomentIsFuture = MomentHelper.isFuture(sponsorship.getEndMoment());

				super.state(context, endMomentIsFuture, "endMoment", "acme.validation.sponsorship.endMoment-NotFuture.message");
			}
			// Check startMoment is before endMoment
			{
				boolean startMomentIsPreviousToEndMoment;

				startMomentIsPreviousToEndMoment = MomentHelper.isBefore(sponsorship.getStartMoment(), sponsorship.getEndMoment());

				super.state(context, startMomentIsPreviousToEndMoment, "startMoment", "acme.validation.sponsorship.startMoment-PostEndMoment.message");
			}
			isValid = !super.hasErrors(context);
		}

		return isValid;
	}

}
