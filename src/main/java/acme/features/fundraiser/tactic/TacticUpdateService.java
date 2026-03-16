
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.entities.strategies.TacticKind;
import acme.realms.Fundraiser;

@Service
public class TacticUpdateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private TacticRepository	repository;

	private Tactic				tactic;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		this.tactic = this.repository.findTacticById(id);
	}

	@Override
	public void authorise() {
		int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean status = this.tactic != null && this.tactic.getStrategy().getDraftMode() && this.tactic.getStrategy().getFundraiser().getId() == fundraiserId;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "notes", "expectedPercentage");
		super.unbindGlobal("strategyDraftMode", this.tactic.getStrategy().getDraftMode());
		super.unbindGlobal("strategyId", this.tactic.getStrategy().getId());
		SelectChoices options = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		super.unbindGlobal("kind", options);
	}
}
