package acme.features.spokesperson.projectCampaign;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Controller
public class SpokespersonProjectCampaignController extends AbstractController<Spokesperson, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("create", SpokespersonProjectCampaignAttachService.class);
		super.addBasicCommand("delete", SpokespersonProjectCampaignDetachService.class);
	}

}
