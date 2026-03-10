
package acme.features.any.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {

	@Autowired
	private AnyTacticRepository	repository;

	private Iterable<Tactic>	tactics;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {

		int strategyId = super.getRequest().getData("strategyId", int.class);

		this.tactics = this.repository.findPublishedTacticsByStrategyId(strategyId);
	}

	@Override
	public void unbind() {
		for (Tactic tactic : this.tactics) {

			Tuple tuple = super.unbindObject(tactic, "name", "notes", "expectedPercentage", "kind");

			tuple.put("strategyId", tactic.getStrategy().getId());
		}
	}
}
