
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.inventions.Part;

@Validator
public class PartValidator extends AbstractValidator<ValidPart, Part> {

	@Override
	protected void initialise(final ValidPart annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Part part, final ConstraintValidatorContext context) {
		assert context != null;

		if (part == null)
			return true;

		if (part.getCost() != null) {

			boolean isEurCurrency = "EUR".equals(part.getCost().getCurrency());

			super.state(context, isEurCurrency, "cost", "acme.validation.part.eurCurrency.message");
		}

		return !super.hasErrors(context);
	}

}
