
package acme.features.sponsorships.donation;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Donation;
import acme.realms.Sponsor;

@Controller
public class DonationController extends AbstractController<Sponsor, Donation> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", DonationListService.class);
		super.addBasicCommand("show", DonationShowService.class);
		super.addBasicCommand("create", DonationCreateService.class);
		super.addBasicCommand("update", DonationUpdateService.class);
		super.addBasicCommand("delete", DonationDeleteService.class);
	}

}
