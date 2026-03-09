
package acme.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.strategy.StrategyRepository;

public class StrategyValidator implements ConstraintValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		if (strategy == null)
			return true;

		boolean valid = true;
		context.disableDefaultConstraintViolation();

		// Ticker único
		if (this.repository.existsStrategyWithTicker(strategy.getTicker(), strategy.getId())) {
			context.buildConstraintViolationWithTemplate("{acme.validation.strategy.duplicatedTicker.message}").addPropertyNode("ticker").addConstraintViolation();
			valid = false;
		}

		// Validaciones al publicar una strategy
		if (Boolean.FALSE.equals(strategy.getDraftMode())) {

			// Debe tener al menos una táctica
			if (strategy.getId() != 0) {
				long count = this.repository.countTacticsByStrategyId(strategy.getId());

				if (count == 0) {
					context.buildConstraintViolationWithTemplate("{acme.validation.strategy.must-have-tactic.message}").addPropertyNode("draftMode").addConstraintViolation();
					valid = false;
				}
			}

			// Intervalo válido
			if (!MomentHelper.isBefore(strategy.getStartMoment(), strategy.getEndMoment())) {
				context.buildConstraintViolationWithTemplate("{acme.validation.strategy.startMoment-PostEndMoment.message}").addPropertyNode("startMoment").addConstraintViolation();
				valid = false;
			}
		}

		return valid;
	}
}
