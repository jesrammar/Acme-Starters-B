package acme.features.spokesperson.campaign;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class CampaignPublishService extends AbstractService<Spokesperson, Campaign> {

	@Autowired
	private CampaignRepository repository;

	private Campaign campaign;

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.campaign != null && this.campaign.getSpokesperson().isPrincipal() && Boolean.TRUE.equals(this.campaign.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		this.campaign.setDraftMode(false);
	}

	@Override
	public void validate() {
		super.validateObject(this.campaign);

		if (!super.getErrors().hasErrors("draftMode")) {
			final Long milestoneCount = this.repository.countMilestonesByCampaignId(this.campaign.getId());
			final boolean hasMilestones = milestoneCount != null && milestoneCount > 0;
			super.state(hasMilestones, "draftMode", "acme.validation.campaign.must-have-milestone.message");
		}

		final Date startMoment = this.campaign.getStartMoment();
		final Date endMoment = this.campaign.getEndMoment();

		if (!super.getErrors().hasErrors("startMoment")) {
			final boolean startInFuture = startMoment != null && MomentHelper.isFuture(startMoment);
			super.state(startInFuture, "startMoment", "acme.validation.campaign.moments.must-be-future.message");
		}

		if (!super.getErrors().hasErrors("endMoment")) {
			final boolean endInFuture = endMoment != null && MomentHelper.isFuture(endMoment);
			super.state(endInFuture, "endMoment", "acme.validation.campaign.moments.must-be-future.message");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.campaign);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		Double effort;

		tuple = new Tuple();
		tuple.put("id", this.campaign.getId());
		tuple.put("version", this.campaign.getVersion());
		tuple.put("ticker", this.campaign.getTicker());
		tuple.put("name", this.campaign.getName());
		tuple.put("description", this.campaign.getDescription());
		tuple.put("startMoment", this.campaign.getStartMoment());
		tuple.put("endMoment", this.campaign.getEndMoment());
		tuple.put("moreInfo", this.campaign.getMoreInfo());
		tuple.put("draftMode", this.campaign.getDraftMode());
		tuple.put("monthsActive", this.campaign.getMonthsActive());
		effort = this.repository.computeCampaignEffort(this.campaign.getId());
		tuple.put("effort", effort == null ? 0.0 : effort);
		super.getResponse().addData(tuple);
	}
}
