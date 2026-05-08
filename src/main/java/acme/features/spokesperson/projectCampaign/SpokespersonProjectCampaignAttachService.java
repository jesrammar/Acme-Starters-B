package acme.features.spokesperson.projectCampaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Service
public class SpokespersonProjectCampaignAttachService extends AbstractService<Spokesperson, Project> {

	@Autowired
	private SpokespersonProjectCampaignRepository repository;

	private Project project;
	private Campaign campaign;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndMemberUserAccountId(projectId, userAccountId);
		if (this.project != null && this.project.getCampaignTicker() == null)
			this.project.setCampaignTicker("");
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && Boolean.TRUE.equals(this.project.getDraftMode()));
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "campaignTicker");
	}

	@Override
	public void validate() {
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.campaign = this.repository.findOwnCampaignByTicker(this.project.getCampaignTicker(), userAccountId);
		super.state(this.campaign != null, "campaignTicker", "acme.validation.project.campaign-not-found.message");

		if (this.campaign != null)
			super.state(this.campaign.getProject() == null, "campaignTicker", "acme.validation.project.campaign-already-linked.message");
	}

	@Override
	public void execute() {
		this.campaign.setProject(this.project);
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "campaignTicker");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectTitle", this.project.getTitle());
	}

}
