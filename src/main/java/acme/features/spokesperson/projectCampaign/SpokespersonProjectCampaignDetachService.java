package acme.features.spokesperson.projectCampaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonProjectCampaignDetachService extends AbstractService<Spokesperson, Project> {

	@Autowired
	private SpokespersonProjectCampaignRepository repository;

	private Project project;
	private Campaign campaign;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int campaignId = super.getRequest().getData("campaignId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndMemberUserAccountId(projectId, userAccountId);
		this.campaign = this.project == null ? null : this.repository.findOwnCampaignInProject(projectId, campaignId, userAccountId);

		if (this.project != null && this.campaign != null)
			this.project.setCampaignTicker(this.campaign.getTicker());
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && this.campaign != null && Boolean.TRUE.equals(this.project.getDraftMode()));
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.project.getCampaigns().remove(this.campaign);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "campaignTicker");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectTitle", this.project.getTitle());
	}

}
