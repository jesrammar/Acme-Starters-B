package acme.features.authenticated.projectMilestone;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.campaigns.Milestone;

@Controller
public class AuthenticatedProjectMilestoneController extends AbstractController<Authenticated, Milestone> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuthenticatedProjectMilestoneListService.class);
		super.addBasicCommand("show", AuthenticatedProjectMilestoneShowService.class);
	}

}
