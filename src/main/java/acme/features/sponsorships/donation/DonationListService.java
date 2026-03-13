
package acme.features.sponsorships.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if (super.getRequest().hasData("sponsorshipId", int.class)) {
			int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
			this.donations = this.repository.findDonationsBySponsorshipId(sponsorshipId);
			this.sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		} else
			this.sponsorship = null;

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
		boolean showCreate;
		super.unbindObjects(this.donations, "name", "notes", "money", "kind");

		showCreate = this.sponsorship.getDraftMode() && this.sponsorship.getSponsor().isPrincipal();
		super.unbindGlobal("sponsorshipId", this.sponsorship.getId());
		super.unbindGlobal("showCreate", showCreate);
	}
}
