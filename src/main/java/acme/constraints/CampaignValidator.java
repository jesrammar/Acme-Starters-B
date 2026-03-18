/*
 * CampaignValidator.java
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

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
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

		this.validateUniqueTicker(context, campaign);

		return !super.hasErrors(context);
	}

	

	private void validateUniqueTicker(final ConstraintValidatorContext context, final Campaign campaign) {
		assert context != null;
		assert campaign != null;

		if (StringHelper.isBlank(campaign.getTicker()))
			return;

		final Campaign existingCampaign = this.repository.findCampaignByTicker(campaign.getTicker());
		final boolean uniqueTicker = existingCampaign == null || existingCampaign.getId() == campaign.getId();

		super.state(context, uniqueTicker, "ticker", "acme.validation.campaign.duplicated-ticker.message");
	}

}
