
package acme.features.any.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	@Autowired
	private AnySponsorshipRepository	repository;

	private Sponsorship					sponsorship;


	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null;

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findPublishedSponsorshipById(id);
	}

	@Override
	public void unbind() {

		Tuple tuple;

		tuple = super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

		tuple.put("money", this.sponsorship.getTotalMoney());
		tuple.put("activeMonths", this.sponsorship.getMonthsActive());
		long donationCount = this.repository.countDonationsBySponsorshipId(this.sponsorship.getId());
		tuple.put("donationCount", donationCount);
		tuple.put("sponsorId", this.sponsorship.getSponsor().getId());
	}
}
