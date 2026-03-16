/*
 * CampaignPublishableValidator.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.entities.campaigns.Campaign;
import acme.features.spokesperson.campaign.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	

	@Autowired
	private CampaignRepository repository;

	

	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
		assert context != null;

		if (campaign == null)
			return true;

		final Date start = campaign.getStartMoment();
		final Date end = campaign.getEndMoment();

		this.validateUniqueTicker(context, campaign);
		this.validateInterval(context, start, end);

		if (this.isPublishing(campaign))
			this.validatePublishRules(context, campaign, start, end);

		return !super.hasErrors(context);
	}

	private void validateUniqueTicker(final ConstraintValidatorContext context, final Campaign campaign) {
		assert context != null;
		assert campaign != null;

		if (StringHelper.isBlank(campaign.getTicker()))
			return;

		final Campaign existingCampaign = this.repository.findCampaignByTicker(campaign.getTicker());
		final boolean uniqueTicker = existingCampaign == null || existingCampaign.equals(campaign);

		super.state(context, uniqueTicker, "ticker", "acme.validation.campaign.duplicated-ticker.message");
	}

	private boolean isPublishing(final Campaign campaign) {
		assert campaign != null;

		if (!Boolean.FALSE.equals(campaign.getDraftMode()))
			return false;

		if (campaign.getId() <= 0)
			return true;

		final Campaign persisted = this.repository.findCampaignById(campaign.getId());

		return persisted != null && Boolean.TRUE.equals(persisted.getDraftMode());
	}

	private void validateInterval(final ConstraintValidatorContext context, final Date start, final Date end) {
		if (start != null && end != null) {
			final boolean correctInterval = MomentHelper.isBefore(start, end);
			super.state(context, correctInterval, "startMoment", "acme.validation.campaign.moments.invalid-interval.message");
		}
	}

	private void validatePublishRules(final ConstraintValidatorContext context, final Campaign campaign, final Date start, final Date end) {
		final Long count = campaign.getId() == 0 ? 0L : this.repository.countMilestonesByCampaignId(campaign.getId());
		final boolean hasMilestones = count != null && count > 0;
		super.state(context, hasMilestones, "draftMode", "acme.validation.campaign.must-have-milestone.message");

		final boolean startInFuture = start != null && MomentHelper.isFuture(start);
		final boolean endInFuture = end != null && MomentHelper.isFuture(end);
		super.state(context, startInFuture, "startMoment", "acme.validation.campaign.moments.must-be-future.message");
		super.state(context, endInFuture, "endMoment", "acme.validation.campaign.moments.must-be-future.message");
	}


}
