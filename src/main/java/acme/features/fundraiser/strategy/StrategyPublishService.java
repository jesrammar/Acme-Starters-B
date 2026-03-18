
package acme.features.fundraiser.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Service
public class StrategyPublishService extends AbstractService<Fundraiser, Strategy> {

	@Autowired
	private StrategyRepository	repository;

	private Strategy			strategy;


	@Override
	public void load() {
		int id;
		int auditorId;
		id = super.getRequest().getData("id", int.class);
		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategy = this.repository.findStrategyByIdAndFundraiserId(id, auditorId);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.strategy != null && this.strategy.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		this.strategy.setDraftMode(false);

		if (!super.getErrors().hasErrors()) {
			boolean isValidStart = this.strategy.getStartMoment() != null && MomentHelper.isFuture(this.strategy.getStartMoment());
			boolean isValidEnd = this.strategy.getEndMoment() != null && MomentHelper.isFuture(this.strategy.getEndMoment());

			super.state(isValidStart, "startMoment", "acme.validation.strategy.startMoment-notFuture.message");
			super.state(isValidEnd, "endMoment", "acme.validation.strategy.endMoment-notFuture.message");

		}

		super.validateObject(this.strategy);

		if (super.getErrors().hasErrors())
			this.strategy.setDraftMode(true);
	}

	@Override
	public void execute() {

		this.strategy.setDraftMode(false);
		this.repository.save(this.strategy);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
