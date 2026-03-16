package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Milestone;
import acme.entities.campaigns.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class MilestoneShowService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private MilestoneRepository	repository;

	private Milestone						milestone;

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.milestone = this.repository.findVisibleMilestoneById(id, userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.milestone != null);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		tuple.put("kinds", SelectChoices.from(MilestoneKind.class, this.milestone.getKind()));
		tuple.put("campaignId", this.milestone.getCampaign().getId());
		tuple.put("draftMode", this.milestone.getCampaign().getDraftMode());
	}
}


