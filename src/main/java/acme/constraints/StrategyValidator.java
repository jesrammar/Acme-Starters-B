
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.strategy.StrategyRepository;

public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {

		assert context != null;
		boolean valid = true;
		{
			if (strategy == null)
				return true;
		}
		{
			//El ticker debe ser único

			Strategy dbStrategy = this.repository.findStrategyByTicker(strategy.getTicker());
			Boolean isUnique = dbStrategy == null || dbStrategy.getId() == strategy.getId();
			super.state(context, isUnique, "*", "acme.validation.strategy.duplicatedTicker.message");
		}
		// Validaciones al publicar una strategy
		if (Boolean.FALSE.equals(strategy.getDraftMode())) {

			// Debe tener al menos una táctica
			long count = this.repository.countTacticsByStrategyId(strategy.getId());
			super.state(context, count >= 1, "*", "acme.validation.strategy.must-have-tactic.message");

			// Intervalo válido

			boolean startMomentIsBefore = MomentHelper.isBefore(strategy.getStartMoment(), strategy.getEndMoment());
			super.state(context, startMomentIsBefore, "startMoment", "acme.validation.strategy.startMoment-PostEndMoment.message");

		}
		valid = !super.hasErrors(context);

		return valid;
	}
}
