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
public class MilestoneDeleteService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private MilestoneRepository	repository;

	private Milestone						milestone;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.milestone != null && this.milestone.getCampaign().getSpokesperson().isPrincipal() && Boolean.TRUE.equals(this.milestone.getCampaign().getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		// Nothing to bind on delete.
	}

	@Override
	public void validate() {
		// Nothing to validate on delete beyond authorisation.
	}

	@Override
	public void execute() {
		int campaignId;

		campaignId = this.milestone.getCampaign().getId();
		this.repository.delete(this.milestone);
		super.unbindGlobal("return_url", String.format("/spokesperson/milestone/list?campaignId=%d", campaignId));
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		tuple.put("kinds", choices);
		tuple.put("campaignId", this.milestone.getCampaign().getId());
		tuple.put("draftMode", this.milestone.getCampaign().getDraftMode());
	}

	@Override
	public void onSuccess() {
		// Navigation is decided before onSuccess() by the framework redirect handler.
	}
}

