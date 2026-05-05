package acme.features.authenticated.projectMilestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.projects.Project;

@Service
public class AuthenticatedProjectMilestoneShowService extends AbstractService<Authenticated, Milestone> {

	@Autowired
	private AuthenticatedProjectMilestoneRepository repository;

	private Project project;
	private Campaign campaign;
	private Milestone milestone;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int campaignId = super.getRequest().getData("campaignId", int.class);
		final int id = super.getRequest().getData("id", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndMemberUserAccountId(projectId, userAccountId);
		this.campaign = this.project == null ? null : this.repository.findCampaignByProjectIdAndCampaignId(projectId, campaignId);
		this.milestone = this.campaign == null ? null : this.repository.findMilestoneByIdAndCampaignId(id, campaignId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && this.campaign != null && this.milestone != null);
	}

	@Override
	public void unbind() {
		final Tuple tuple = super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		super.unbindGlobal("campaignId", this.campaign.getId());
		super.unbindGlobal("projectId", this.project.getId());
	}

}
