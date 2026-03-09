
package acme.features.sponsorships.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Controller
public class SponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", SponsorshipListService.class);
		super.addBasicCommand("show", SponsorshipShowService.class);
		//	super.addBasicCommand("create", SponsorshipCreateService.class);
		//	super.addBasicCommand("update", SponsorshipUpdateService.class);
		//	super.addBasicCommand("delete", SponsorshipDeleteService.class);
	}

}
