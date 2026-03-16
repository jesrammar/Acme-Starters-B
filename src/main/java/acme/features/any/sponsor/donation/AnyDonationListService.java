
package acme.features.any.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnyDonationListService extends AbstractService<Any, Donation> {

	@Autowired
	private AnyDonationRepository	repository;

	private Collection<Donation>	donations;
	private Sponsorship				sponsorship;


	@Override
	public void load() {
		if (super.getRequest().hasData("sponsorshipId", int.class)) {
			int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
			this.donations = this.repository.findPublishedDonationsBySponsorshipId(sponsorshipId);
			this.sponsorship = this.repository.findPublishedSponsorshipById(sponsorshipId);
		} else {
			this.donations = null;
			this.sponsorship = null;
		}
	}

	@Override
	public void authorise() {
		boolean status = this.sponsorship != null && !this.sponsorship.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		for (Donation donation : this.donations) {
			Tuple tuple = super.unbindObject(donation, "name", "notes", "money", "kind");
			tuple.put("sponsorshipId", donation.getSponsorship().getId());
		}
	}
}
