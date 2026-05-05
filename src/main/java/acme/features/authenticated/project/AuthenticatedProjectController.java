package acme.features.authenticated.project;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Authenticated;
import acme.client.controllers.AbstractController;
import acme.entities.projects.Project;

@Controller
public class AuthenticatedProjectController extends AbstractController<Authenticated, Project> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AuthenticatedProjectListService.class);
		super.addBasicCommand("show", AuthenticatedProjectShowService.class);
	}

}
