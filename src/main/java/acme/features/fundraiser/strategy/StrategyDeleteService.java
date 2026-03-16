
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.features.fundraiser.tactic.TacticRepository;
import acme.realms.Fundraiser;

@Service
public class StrategyDeleteService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private StrategyRepository	repository;

	@Autowired
	private TacticRepository	tacticRepository;
	private Strategy			strategy;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategy = this.repository.findOneByIdAndFundraiserId(id, fundraiserId);
	}

	@Override
	public void authorise() {
		boolean status = this.strategy != null && this.strategy.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.tacticRepository.deleteAll(this.tacticRepository.findTacticsByStrategyId(this.strategy.getId()));
		this.repository.delete(this.strategy);
	}

	@Override
	public void unbind() {
	}

}
