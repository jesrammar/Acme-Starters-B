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
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.CampaignRepository;

@Validator
public class CampaignPublishableValidator extends AbstractValidator<ValidCampaignPublishable, Campaign> {

	

	@Autowired
	private CampaignRepository repository;

	

	@Override
	protected void initialise(final ValidCampaignPublishable annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {
	
		assert context != null;

		boolean result;

		if (campaign == null)
			result = true;
		else {
			Date start;
			Date end;
			boolean correctInterval;

			start = campaign.getStartMoment();
			end = campaign.getEndMoment();

			if (start != null && end != null) {
				correctInterval = MomentHelper.isBefore(start, end);
				super.state(context, correctInterval, "startMoment", "acme.validation.campaign.moments.invalid-interval.message");
			}

			if (Boolean.FALSE.equals(campaign.getDraftMode())) {
				boolean hasMilestones;
				int campaignId;
				Long count;
				boolean startInFuture;
				boolean endInFuture;

				campaignId = campaign.getId();
				if (campaignId == 0)
					count = 0L;
				else
					count = this.repository.countMilestonesByCampaignId(campaignId);

				hasMilestones = count != null && count > 0;
				super.state(context, hasMilestones, "draftMode", "acme.validation.campaign.must-have-milestone.message");

				startInFuture = start != null && MomentHelper.isFuture(start);
				endInFuture = end != null && MomentHelper.isFuture(end);

				super.state(context, startInFuture, "startMoment", "acme.validation.campaign.moments.must-be-future.message");
				super.state(context, endInFuture, "endMoment", "acme.validation.campaign.moments.must-be-future.message");
			}

			result = !super.hasErrors(context);
		}

		return result;
	}

}

