
package acme.features.any.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;

@Service
public class AnyTacticShowService extends AbstractService<Any, Tactic> {

	@Autowired
	private AnyTacticRepository	repository;

	private Tactic				tactic;


	@Override
	public void authorise() {
		boolean status = this.tactic != null && !this.tactic.getStrategy().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findPublishedTacticById(id);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
	}
}
