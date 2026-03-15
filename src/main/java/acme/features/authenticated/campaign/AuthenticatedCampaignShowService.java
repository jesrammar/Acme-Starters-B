package acme.features.authenticated.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AuthenticatedCampaignShowService extends AbstractService<Authenticated, Campaign> {

	@Autowired
	private AuthenticatedCampaignRepository	repository;

	private Campaign				campaign;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findPublishedCampaignById(id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.campaign != null);
	}

	@Override
	public void unbind() {
		Tuple tuple;

		tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		Double effort = this.repository.computeCampaignEffort(this.campaign.getId());
		tuple.put("effort", effort == null ? 0.0 : effort);
	}
}
