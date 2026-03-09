
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class TacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private TacticRepository	repository;

	private Collection<Tactic>	tactics;


	@Override
	public void load() {
		int strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = (Collection<Tactic>) this.repository.findTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (Tactic tactic : this.tactics) {
			Tuple tuple = super.unbindObject(tactic, "name", "notes", "expectedPercentage", "kind");
			tuple.put("strategyId", tactic.getStrategy().getId());
		}

	}
}
