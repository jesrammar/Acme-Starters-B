
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.inventions.Invention;
import acme.features.inventor.invention.InventionRepository;

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

		// Check duplicated invention with equal ticker
		{
			boolean uniqueInvention;
			Invention existingInvention;

			existingInvention = this.repository.findInventionByTicker(invention.getTicker());
			uniqueInvention = existingInvention == null || existingInvention.getId() == invention.getId();

			super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
		}

		// For the already published inventions
		if (Boolean.FALSE.equals(invention.getDraftMode())) {
			// Check invention has more than one part
			{
				boolean hasInventionAtLeastOnePart;

				hasInventionAtLeastOnePart = this.repository.countPartsByInventionId(invention.getId()) >= 1;

				super.state(context, hasInventionAtLeastOnePart, "*", "acme.validation.invention.parts.message");
			}
			// Check startMoment is before endMoment
			{
				boolean startMomentIsPreviousToEndMoment;

				startMomentIsPreviousToEndMoment = MomentHelper.isBefore(invention.getStartMoment(), invention.getEndMoment());

				super.state(context, startMomentIsPreviousToEndMoment, "startMoment", "acme.validation.invention.invalidMomentInterval.message");
			}
			isValid = !super.hasErrors(context);
		}

		return isValid;
	}

}
