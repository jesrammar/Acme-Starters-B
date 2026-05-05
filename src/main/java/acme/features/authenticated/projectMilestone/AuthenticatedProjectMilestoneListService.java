package acme.features.authenticated.projectMilestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.projects.Project;

@Service
public class AuthenticatedProjectMilestoneListService extends AbstractService<Authenticated, Milestone> {

	@Autowired
	private AuthenticatedProjectMilestoneRepository repository;

	private Project project;
	private Campaign campaign;
	private Collection<Milestone> milestones;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int campaignId = super.getRequest().getData("campaignId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndMemberUserAccountId(projectId, userAccountId);
		this.campaign = this.project == null ? null : this.repository.findCampaignByProjectIdAndCampaignId(projectId, campaignId);
		this.milestones = this.campaign == null ? java.util.List.of() : this.repository.findMilestonesByCampaignId(campaignId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && this.campaign != null);
	}

	@Override
	public void unbind() {
		for (final Milestone milestone : this.milestones) {
			final Tuple tuple = super.unbindObject(milestone, "title", "achievements", "effort", "kind");
			tuple.put("campaignId", this.campaign.getId());
			tuple.put("projectId", this.project.getId());
		}

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("campaignId", this.campaign.getId());
	}

}
