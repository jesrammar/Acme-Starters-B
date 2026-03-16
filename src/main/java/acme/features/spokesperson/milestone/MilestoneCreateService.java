package acme.features.spokesperson.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.campaigns.MilestoneKind;
import acme.realms.Spokesperson;

@Service
public class MilestoneCreateService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private MilestoneRepository	repository;

	private Milestone						milestone;
	private Campaign						campaign;

	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);

		this.milestone = new Milestone();
		this.milestone.setCampaign(this.campaign);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal() && Boolean.TRUE.equals(this.campaign.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.milestone, "title", "achievements", "effort", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.milestone);
	}

	@Override
	public void execute() {
		this.repository.save(this.milestone);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		SelectChoices choices;

		choices = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		tuple.put("kinds", choices);
		tuple.put("campaignId", this.campaign == null ? null : this.campaign.getId());
	}
}


