
package acme.features.spokesperson.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class CampaignCreateService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private CampaignRepository	repository;

	private Campaign			campaign;


	@Override
	public void load() {
		int userAccountId;
		Spokesperson spokesperson;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		spokesperson = this.repository.findSpokespersonByUserAccountId(userAccountId);

		this.campaign = new Campaign();
		this.campaign.setDraftMode(true);
		this.campaign.setSpokesperson(spokesperson);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson() != null;
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}
}
