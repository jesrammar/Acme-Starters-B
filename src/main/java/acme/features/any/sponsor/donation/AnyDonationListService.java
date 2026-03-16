
package acme.features.any.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;

@Service
public class AnyDonationListService extends AbstractService<Any, Donation> {

	@Autowired
	private AnyDonationRepository	repository;

	private Collection<Donation>	donations;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.donations = this.repository.findPublishedDonationsBySponsorshipId(sponsorshipId);
	}

	@Override
	public void unbind() {
		for (Donation donation : this.donations) {
			Tuple tuple = super.unbindObject(donation, "name", "notes", "money", "kind");
			tuple.put("sponsorshipId", donation.getSponsorship().getId());
		}
	}
}
