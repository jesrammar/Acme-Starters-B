package acme.features.authenticated.projectCampaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Service
public class AuthenticatedProjectCampaignShowService extends AbstractService<Authenticated, Campaign> {

	@Autowired
	private AuthenticatedProjectCampaignRepository repository;

	private Project project;
	private Campaign campaign;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int id = super.getRequest().getData("id", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndMemberUserAccountId(projectId, userAccountId);
		this.campaign = this.project == null ? null : this.repository.findCampaignByProjectIdAndCampaignId(projectId, id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && this.campaign != null);
	}

	@Override
	public void unbind() {
		final Tuple tuple = super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		tuple.put("effort", this.campaign.getEffort());

		super.unbindGlobal("projectId", this.project.getId());
	}

}
