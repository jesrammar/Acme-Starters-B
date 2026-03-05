
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.features.inventions.InventionRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	@Autowired
	private InventionRepository repository;


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {
		assert context != null;
		boolean isValid = true;

		if (invention == null)
			return true;

		// Validate if it's going to be published
		if (Boolean.FALSE.equals(invention.getDraftMode())) {
			// Check duplicated invention with equal ticker
			{
				boolean uniqueInvention;
				Invention existingInvention;

				existingInvention = this.repository.findInventionByTicker(invention.getTicker());
				uniqueInvention = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
			}
			// Check invention has more than one part
			{
				boolean hasInventionAtLeastOnePart;

				hasInventionAtLeastOnePart = this.repository.countPartsByInventionId(invention.getId()) >= 1;

				super.state(context, hasInventionAtLeastOnePart, "invention", "acme.validation.invention.parts.message");
			}
			// Check dates are future
			{
				boolean startMomentIsFuture;

				startMomentIsFuture = MomentHelper.isFuture(invention.getStartMoment());

				super.state(context, startMomentIsFuture, "startMoment", "acme.validation.invention.startMoment-NotFuture.message");
			}
			{
				boolean endMomentIsFuture;

				endMomentIsFuture = MomentHelper.isFuture(invention.getEndMoment());

				super.state(context, endMomentIsFuture, "endMoment", "acme.validation.invention.endMoment-NotFuture.message");
			}
			// Check startMoment is before endMoment
			{
				boolean startMomentIsPreviousToEndMoment;

				startMomentIsPreviousToEndMoment = MomentHelper.isBefore(invention.getStartMoment(), invention.getEndMoment());

				super.state(context, startMomentIsPreviousToEndMoment, "startMoment", "acme.validation.invention.startMoment-PostEndMoment.message");
			}
			isValid = !super.hasErrors(context);
		}

		return isValid;
	}

}
