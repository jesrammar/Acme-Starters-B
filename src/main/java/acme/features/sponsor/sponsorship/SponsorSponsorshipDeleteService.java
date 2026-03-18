
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.features.sponsor.donation.SponsorDonationRepository;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository	repository;

	@Autowired
	private SponsorDonationRepository		donationRepository;

	private Sponsorship						sponsorship;


	@Override
	public void load() {
		if (super.getRequest().hasData("id", int.class)) {
			int id = super.getRequest().getData("id", int.class);
			this.sponsorship = this.repository.findSponsorshipById(id);
		} else
			this.sponsorship = null;
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.sponsorship != null && //
			this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.donationRepository.deleteAll(this.donationRepository.findDonationsBySponsorshipId(this.sponsorship.getId()));
		this.repository.delete(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
