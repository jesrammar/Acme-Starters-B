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
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		if (super.getRequest().hasData("campaignId", int.class)) {
			int campaignId;

			campaignId = super.getRequest().getData("campaignId", int.class);
			this.campaign = this.repository.findCampaignByIdAndSpokespersonUserAccountId(campaignId, userAccountId);
			this.milestones = this.campaign == null ? java.util.List.of() : this.repository.findMilestonesByCampaignIdAndSpokespersonUserAccountId(campaignId, userAccountId);
		} else {
			this.campaign = null;
			this.milestones = java.util.List.of();
		}
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

