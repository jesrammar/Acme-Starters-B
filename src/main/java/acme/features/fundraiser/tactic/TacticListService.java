
package acme.features.fundraiser.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class TacticListService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private TacticRepository	repository;

	private Collection<Tactic>	tactics;

	private Strategy			strategy;


	@Override
	public void load() {
		int strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repository.findTacticsByStrategyId(strategyId);
		int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategy = this.repository.findStrategyByIdAndFundraiserId(strategyId, fundraiserId);

	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "kind", "notes", "expectedPercentage");
		super.unbindGlobal("strategyId", super.getRequest().getData("strategyId", int.class));
		super.unbindGlobal("strategyDraftMode", this.strategy.getDraftMode());

	}
}
