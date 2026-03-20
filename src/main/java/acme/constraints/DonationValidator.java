
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorships.Donation;

@Validator
public class DonationValidator extends AbstractValidator<ValidDonation, Donation> {

	@Override
	protected void initialise(final ValidDonation annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Donation donation, final ConstraintValidatorContext context) {
		assert context != null;

		if (donation == null)
			return true;

		if (donation.getMoney() != null) {

			boolean isEurCurrency = "EUR".equals(donation.getMoney().getCurrency());

			super.state(context, isEurCurrency, "money", "acme.validation.donation.eurCurrency.message");
		}

		return !super.hasErrors(context);
	}

}
