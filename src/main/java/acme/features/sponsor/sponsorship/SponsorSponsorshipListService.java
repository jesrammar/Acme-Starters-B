
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipListService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository	repository;

	private Collection<Sponsorship>	sponsorships;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.sponsorships = (Collection<Sponsorship>) this.repository.findSponsorshipsBySponsorId(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {

		for (Sponsorship sponsorship : this.sponsorships) {

			Tuple tuple;

			tuple = super.unbindObject(sponsorship, "ticker", "name", "startMoment", "endMoment", "draftMode");

			tuple.put("money", sponsorship.getTotalMoney());

			long donationCount = this.repository.countDonationsBySponsorshipId(sponsorship.getId());

			tuple.put("donationCount", donationCount);
		}
	}

}
