
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class TacticDeleteService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private TacticRepository	repository;

	private Tactic				tactic;


	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.tactic = this.repository.findTacticByIdAndFundraiserId(id, fundraiserId);
	}

	@Override
	public void authorise() {
		boolean status = this.tactic != null && this.tactic.getStrategy().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");

	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.repository.delete(this.tactic);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");

		tuple.put("strategyId", this.tactic.getStrategy().getId());

		tuple.put("strategyDraftMode", this.tactic.getStrategy().getDraftMode());
	}

}
