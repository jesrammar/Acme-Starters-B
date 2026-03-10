
package acme.features.sponsorships.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class DonationListService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private DonationRepository		repository;

	private Collection<Donation>	donations;
	private Sponsorship				sponsorship;


	@Override
	public void load() {
		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.donations = this.repository.findDonationsBySponsorshipId(sponsorshipId);
		this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);
	}

	@Override
	public void authorise() {
		boolean status = false;
		status = this.sponsorship != null && //
			(this.sponsorship.getSponsor().isPrincipal() || !this.sponsorship.getDraftMode());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple;
		for (Donation donation : this.donations) {
			tuple = super.unbindObject(donation, "name", "notes", "money", "kind");
			tuple.put("sponsorshipId", donation.getSponsorship().getId());
		}
	}
}
