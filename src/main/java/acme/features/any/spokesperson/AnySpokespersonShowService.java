package acme.features.any.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.client.components.principals.Any;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Service
public class AnySpokespersonShowService extends AbstractService<Any, Spokesperson> {

	@Autowired
	private AnySpokespersonRepository	repository;

	private Campaign					campaign;
	private Spokesperson				spokesperson;

	@Override
	public void load() {
		int campaignId;

		campaignId = super.getRequest().getData("campaignId", int.class);
		this.campaign = this.repository.findPublishedCampaignById(campaignId);
		this.spokesperson = this.campaign == null ? null : this.campaign.getSpokesperson();
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.spokesperson != null);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}
}
