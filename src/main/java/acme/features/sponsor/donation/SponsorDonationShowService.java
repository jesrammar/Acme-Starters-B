
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.realms.Sponsor;

@Service
public class SponsorDonationShowService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation					donation;


	@Override
	public void load() {
		if (super.getRequest().hasData("id", int.class)) {
			int id = super.getRequest().getData("id", int.class);
			this.donation = this.repository.findDonationById(id);
		} else
			this.donation = null;
	}

	@Override
	public void authorise() {
		boolean status = false;
		status = this.donation != null && this.donation.getSponsorship().getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());
		Tuple tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", this.donation.getSponsorship().getId());
		tuple.put("draftMode", this.donation.getSponsorship().getDraftMode());
		tuple.put("kinds", choices);
	}
}
