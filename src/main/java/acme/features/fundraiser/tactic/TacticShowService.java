
package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Service
public class TacticShowService extends AbstractService<Fundraiser, Tactic> {

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
		boolean status = this.tactic != null && this.tactic.getStrategy().getFundraiser().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
	}

}
