
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Sponsorship						sponsorship;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		if (super.getRequest().hasData("id", int.class)) {
			int id = super.getRequest().getData("id", int.class);
			this.sponsorship = this.repository.findSponsorshipById(id);
		} else
			this.sponsorship = null;
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && //
			this.sponsorship.getSponsor().isPrincipal() && this.sponsorship.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		this.sponsorship.setDraftMode(false);
		super.validateObject(this.sponsorship);
		if (super.getErrors().hasErrors())
			this.sponsorship.setDraftMode(true);
	}

	@Override
	public void execute() {
		this.sponsorship.setDraftMode(false);
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
