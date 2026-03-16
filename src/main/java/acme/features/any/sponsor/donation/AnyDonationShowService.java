
package acme.features.any.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;

@Service
public class AnyDonationShowService extends AbstractService<Any, Donation> {

	@Autowired
	private AnyDonationRepository	repository;

	private Donation				donation;


	@Override
	public void load() {
		if (super.getRequest().hasData("id", int.class)) {
			int id = super.getRequest().getData("id", int.class);
			this.donation = this.repository.findPublishedDonationById(id);
		} else
			this.donation = null;
	}

	@Override
	public void authorise() {
		boolean status = this.donation != null && !this.donation.getSponsorship().getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", this.donation.getSponsorship().getId());
	}
}
