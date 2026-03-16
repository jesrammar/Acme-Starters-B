package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.spokesperson.campaign.CampaignRepository;
import acme.realms.Spokesperson;

@Service
public class CampaignListService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private CampaignRepository		repository;

	private Collection<Campaign>	campaigns;

	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.campaigns = this.repository.findVisibleCampaignsBySpokespersonUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (Campaign campaign : this.campaigns) {
			Tuple tuple;

			tuple = super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment", "draftMode");
			tuple.put("monthsActive", campaign.getMonthsActive());
			tuple.put("effort", campaign.getEffort());
		}
	}
}


