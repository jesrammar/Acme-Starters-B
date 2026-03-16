
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class StrategyCreateService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private StrategyRepository	repository;

	private Strategy			strategy;


	@Override
	public void load() {
		Fundraiser fundraiser = (Fundraiser) super.getRequest().getPrincipal().getActiveRealm();
		this.strategy = super.newObject(Strategy.class);
		this.strategy.setFundraiser(fundraiser);
		this.strategy.setDraftMode(true);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {

		if (!super.getErrors().hasErrors()) {
			Strategy existing = this.repository.findStrategyByTicker(this.strategy.getTicker());

			super.state(existing == null, "*", "acme.validation.strategy.duplicatedTicker.message");
		}
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
