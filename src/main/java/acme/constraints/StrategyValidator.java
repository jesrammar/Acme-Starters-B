
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.Validator;
import acme.entities.strategies.Strategy;

@Validator
public class StrategyValidator implements ConstraintValidator<ValidStrategy, Strategy> {

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		if (strategy == null)
			return true;

		boolean valid = true;

		// Comprobar que tenga al menos una sección
		if (strategy.getTactics() == null || strategy.getTactics().isEmpty()) {
			context.buildConstraintViolationWithTemplate("Strategy must have at least one tactic").addPropertyNode("tactics").addConstraintViolation();
			valid = false;
		}

		// Comprobar que startMoment < endMoment y ambos en el futuro
		Date now = new Date(); // momento actual
		Date start = strategy.getStartMoment();
		Date end = strategy.getEndMoment();

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
