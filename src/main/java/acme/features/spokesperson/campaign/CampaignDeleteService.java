package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.features.spokesperson.campaign.CampaignRepository;
import acme.realms.Spokesperson;

@Service
public class CampaignDeleteService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private CampaignRepository	repository;

	private Campaign			campaign;

	@Override
	public void load() {
		int id;
		int userAccountId;

		id = super.getRequest().getData("id", int.class);
		userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.campaign = this.repository.findCampaignByIdAndSpokespersonUserAccountId(id, userAccountId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal() && Boolean.TRUE.equals(this.campaign.getDraftMode());
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
		Collection<Milestone> milestones;

		milestones = this.repository.findMilestonesByCampaignId(this.campaign.getId());
		this.repository.deleteAll(milestones);
		this.repository.delete(this.campaign);
		super.unbindGlobal("return_url", "/spokesperson/campaign/list");
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

	@Override
	public void onSuccess() {
		// Navigation is decided before onSuccess() by the framework redirect handler.
	}
}

