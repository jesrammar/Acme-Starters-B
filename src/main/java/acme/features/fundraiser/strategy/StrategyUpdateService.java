
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class StrategyUpdateService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private StrategyRepository	repository;

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
		super.bindObject(this.strategy, "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.strategy);
	}

	@Override
	public void execute() {
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
