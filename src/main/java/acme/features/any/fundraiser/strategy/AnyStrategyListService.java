
package acme.features.any.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {

	@Autowired
	private AnyStrategyRepository	repository;

	private Iterable<Strategy>		strategies;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.strategies = this.repository.findPublishedStrategies();
	}

	@Override
	public void unbind() {

		for (Strategy strategy : this.strategies) {

			Tuple tuple;

			tuple = super.unbindObject(strategy, "ticker", "name", "startMoment", "endMoment");

			tuple.put("expectedPercentage", strategy.getExpectedPercentage());

			long tacticCount = this.repository.countTacticsByStrategyId(strategy.getId());

			tuple.put("tacticCount", tacticCount);
		}
	}

}
