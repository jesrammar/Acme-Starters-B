package acme.features.manager.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Project project;

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.project = this.repository.findProjectByIdAndManagerUserAccountId(id, userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && Boolean.TRUE.equals(this.project.getDraftMode()));
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
		final Date kickOffMoment = this.project.getKickOffMoment();
		final Date closeOutMoment = this.project.getCloseOutMoment();

		if (kickOffMoment != null && closeOutMoment != null)
			super.state(MomentHelper.isBefore(kickOffMoment, closeOutMoment), "kickOffMoment", "acme.validation.project.invalid-interval.message");

		if (kickOffMoment != null)
			super.state(MomentHelper.isFuture(kickOffMoment), "kickOffMoment", "acme.validation.project.kick-off-not-future.message");

		if (closeOutMoment != null)
			super.state(MomentHelper.isFuture(closeOutMoment), "closeOutMoment", "acme.validation.project.close-out-not-future.message");

		super.state(!this.project.getCampaigns().isEmpty(), "*", "acme.validation.project.must-have-campaign.message");

		for (final Campaign campaign : this.project.getCampaigns()) {
			final Long milestones = this.repository.countMilestonesByCampaignId(campaign.getId());
			final boolean publishable = milestones != null && milestones.longValue() > 0 && campaign.getStartMoment() != null && campaign.getEndMoment() != null && MomentHelper.isBefore(campaign.getStartMoment(), campaign.getEndMoment()) && MomentHelper.isFuture(campaign.getStartMoment()) && MomentHelper.isFuture(campaign.getEndMoment());
			super.state(publishable, "*", "acme.validation.project.campaign-not-publishable.message");
		}
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);
		for (final Campaign campaign : this.project.getCampaigns()) {
			campaign.setDraftMode(false);
			this.repository.save(campaign);
		}
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		this.project.setMemberUsernames(ManagerProjectHelper.formatMemberUsernames(this.project));
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "memberUsernames");
	}

}
