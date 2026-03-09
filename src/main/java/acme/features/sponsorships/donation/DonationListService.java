
package acme.features.sponsorships.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.realms.Sponsor;

@Service
public class DonationListService extends AbstractService<Sponsor, Donation> {

	@Autowired
	private DonationRepository		repository;

	private Collection<Donation>	donations;


	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.donations = (Collection<Donation>) this.repository.findDonationsBySponsorshipId(sponsorshipId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (Donation donation : this.donations) {
			Tuple tuple = super.unbindObject(donation, "name", "notes", "money", "kind");
			tuple.put("sponsorshipId", donation.getSponsorship().getId());
		}
	}
}
