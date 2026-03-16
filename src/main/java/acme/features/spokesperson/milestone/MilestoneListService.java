package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Service
public class MilestoneListService extends AbstractService<Spokesperson, Milestone> {

	@Autowired
	private MilestoneRepository	repository;

	private Collection<Milestone>			milestones;
	private Campaign						campaign;

	@Override
	public void load() {
		int campaignId;
		int userAccountId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.campaign = this.repository.findVisibleCampaignById(campaignId, userAccountId);
		this.milestones = this.campaign == null ? java.util.List.of() : this.repository.findVisibleMilestonesByCampaignId(campaignId, userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.campaign != null);
	}

	@Override
	public void unbind() {
		for (Milestone milestone : this.milestones) {
			Tuple tuple;

			tuple = super.unbindObject(milestone, "title", "achievements", "effort", "kind");
			tuple.put("campaignId", milestone.getCampaign().getId());
		}
	}
}

