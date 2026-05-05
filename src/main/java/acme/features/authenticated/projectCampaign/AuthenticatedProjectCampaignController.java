package acme.features.authenticated.projectCampaign;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.campaigns.Campaign;

@Controller
public class AuthenticatedProjectCampaignController extends AbstractController<Authenticated, Campaign> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuthenticatedProjectCampaignListService.class);
		super.addBasicCommand("show", AuthenticatedProjectCampaignShowService.class);
	}

}
