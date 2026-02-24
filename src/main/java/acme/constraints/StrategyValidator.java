
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.features.strategies.StrategyRepository;

public class StrategyValidator implements ConstraintValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		if (strategy == null)
			return true;

		boolean valid = true;
		context.disableDefaultConstraintViolation();

		// Solo validar si se publica
		if (Boolean.FALSE.equals(strategy.getDraftMode())) {

			// 1️: Debe tener al menos una sección
			if (strategy.getId() != 0) {
				long count = this.repository.countTacticsByStrategyId(strategy.getId());

				if (count == 0) {
					context.buildConstraintViolationWithTemplate("A strategy must have at least one tactic").addPropertyNode("draftMode").addConstraintViolation();
					valid = false;
				}
			}

			// 2️: Fechas futuras
			if (!MomentHelper.isFuture(strategy.getStartMoment())) {
				context.buildConstraintViolationWithTemplate("startMoment must be in the future").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}

			if (!MomentHelper.isFuture(strategy.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("endMoment must be in the future").addPropertyNode("endMoment").addConstraintViolation();
				valid = false;
			}

			// 3️: start < end
			if (!MomentHelper.isBefore(strategy.getStartMoment(), strategy.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("startMoment must be before endMoment").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}
		}

		return valid;
	}
}
