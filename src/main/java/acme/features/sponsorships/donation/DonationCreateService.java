
package acme.features.sponsorships.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class DonationCreateService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private DonationRepository	repository;

	private Donation			donation;


	@Override
	public void load() {
		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorship_id", int.class);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		this.donation = super.newObject(Donation.class);
		this.donation.setName("");
		this.donation.setNotes("");
		Money money = new Money();
		money.setAmount(0.0);
		money.setCurrency("EUR");
		this.donation.setMoney(money);
		this.donation.setKind(null);
		this.donation.setSponsorship(sponsorship);
	}

	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorship_id", int.class);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		status = sponsorship != null && //
			this.donation.getSponsorship().getDraftMode() && this.donation.getSponsorship().getSponsor().isPrincipal();

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
		Tuple tuple;

		tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", super.getRequest().getData("sponsorship_id", int.class));
		tuple.put("draftMode", this.donation.getSponsorship().getDraftMode());
	}

}
