
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationCreateService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private SponsorDonationRepository	repository;

	private Donation			donation;


	@Override
	public void load() {
		if (super.getRequest().hasData("sponsorshipId", int.class)) {
			int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
			Sponsorship sponsorship = this.repository.findSponsorshipById(sponsorshipId);

			this.donation = super.newObject(Donation.class);
			this.donation.setName("");
			this.donation.setNotes("");
			Money money = new Money();
			money.setAmount(0.0);
			money.setCurrency("EUR");
			this.donation.setMoney(money);
			this.donation.setKind(null);
			this.donation.setSponsorship(sponsorship);
		} else
			this.donation = null;
	}

	@Override
	public void authorise() {
		boolean status = false;
		if (super.getRequest().hasData("sponsorshipId", int.class)) {
			int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
			Sponsorship sponsorship = this.repository.findSponsorshipById(sponsorshipId);
			status = sponsorship != null && //
				sponsorship.getDraftMode() && sponsorship.getSponsor().isPrincipal();
		}
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		choices = SelectChoices.from(DonationKind.class, this.donation.getKind());
		Tuple tuple;

		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		if (super.getRequest().hasData("sponsorshipId", int.class))
			tuple.put("sponsorshipId", super.getRequest().getData("sponsorshipId", int.class));
		tuple.put("draftMode", this.donation.getSponsorship().getDraftMode());
		tuple.put("kinds", choices);
	}

}
