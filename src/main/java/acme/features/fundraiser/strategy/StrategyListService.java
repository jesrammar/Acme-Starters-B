
package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class StrategyListService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private StrategyRepository		repository;

	private Collection<Strategy>	strategies;


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.strategies = (Collection<Strategy>) this.repository.findStrategyByFundraiserId(userAccountId);

	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}
	@Override
	public void unbind() {

		for (Strategy strategy : this.strategies) {

			Tuple tuple;
			tuple = super.unbindObject(strategy, "ticker", "name", "startMoment", "endMoment", "draftMode");

			tuple.put("expectedPercentage", strategy.getExpectedPercentage());

			long tacticCount = this.repository.countTacticsByStrategyId(strategy.getId());

			tuple.put("tacticCount", tacticCount);

		}
	}

}
