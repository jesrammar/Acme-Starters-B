package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Service
public class AnyMilestoneListService extends AbstractService<Any, Milestone> {

	@Autowired
	private AnyMilestoneRepository	repository;

	private Campaign				campaign;
	private Collection<Milestone>	milestones;

	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findPublishedCampaignById(campaignId);
		this.milestones = this.campaign == null ? java.util.List.of() : this.repository.findPublishedMilestonesByCampaignId(campaignId);
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
