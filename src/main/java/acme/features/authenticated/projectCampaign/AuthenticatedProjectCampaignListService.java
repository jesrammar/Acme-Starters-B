package acme.features.authenticated.projectCampaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Service
public class AuthenticatedProjectCampaignListService extends AbstractService<Authenticated, Campaign> {

	@Autowired
	private AuthenticatedProjectCampaignRepository repository;

	private Project project;
	private Collection<Campaign> campaigns;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndMemberUserAccountId(projectId, userAccountId);
		this.campaigns = this.project == null ? java.util.List.of() : this.repository.findCampaignsByProjectId(projectId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null);
	}

	@Override
	public void unbind() {
		//super.unbindObjects(campaigns, null)
		for (final Campaign campaign : this.campaigns) {
			final Tuple tuple = super.unbindObject(campaign, "ticker", "name", "startMoment", "endMoment", "draftMode");
			tuple.put("draftModeVisual", Boolean.TRUE.equals(campaign.getDraftMode()) ? "✔" : "✖");
			tuple.put("monthsActive", campaign.getMonthsActive());
			tuple.put("effort", campaign.getEffort());
			tuple.put("projectId", this.project.getId());
		}

		super.unbindGlobal("projectId", this.project.getId());
	}

}
