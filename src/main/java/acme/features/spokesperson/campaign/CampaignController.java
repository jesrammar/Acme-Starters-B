package acme.features.spokesperson.campaign;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.campaigns.Campaign;
import acme.realms.Spokesperson;

@Controller
public class CampaignController extends AbstractController<Spokesperson, Campaign> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", CampaignListService.class);
		super.addBasicCommand("show", CampaignShowService.class);
		super.addBasicCommand("create", CampaignCreateService.class);
		super.addBasicCommand("update", CampaignUpdateService.class);
		super.addBasicCommand("delete", CampaignDeleteService.class);
		super.addCustomCommand("publish", "update", CampaignPublishService.class);
	}
}

