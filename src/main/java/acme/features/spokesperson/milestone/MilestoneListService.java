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

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findCampaignById(campaignId);
		this.milestones = this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal();
		super.setAuthorised(status);
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

