
package acme.features.any.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	@Autowired
	private AnySponsorshipRepository	repository;

	private Iterable<Sponsorship>		sponsorships;


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		this.sponsorships = this.repository.findPublishedSponsorships();
	}

	@Override
	public void unbind() {

		for (Sponsorship sponsorship : this.sponsorships) {

			Tuple tuple;

			tuple = super.unbindObject(sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

			tuple.put("money", sponsorship.getTotalMoney());

			tuple.put("activeMonths", sponsorship.getMonthsActive());

			long donationCount = this.repository.countDonationsBySponsorshipId(sponsorship.getId());

			tuple.put("donationCount", donationCount);
		}
	}

}
