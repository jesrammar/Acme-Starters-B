package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.CampaignRepository;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	@Autowired
	private CampaignRepository		repository;

	private Collection<Campaign>	campaigns;

	@Override
	public void load() {
		this.campaigns = this.repository.findPublishedCampaigns();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (Campaign campaign : this.campaigns) {
			Tuple tuple;

			tuple = super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment", "moreInfo");
			tuple.put("monthsActive", campaign.getMonthsActive());
			Double effort = this.repository.computeCampaignEffort(campaign.getId());
			tuple.put("effort", effort == null ? 0.0 : effort);
		}
	}
}
