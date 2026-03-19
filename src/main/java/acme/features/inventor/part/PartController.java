
package acme.features.inventor.part;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Controller
public class PartController extends AbstractController<Inventor, Part> {

	// Constructors

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", PartListService.class);
		super.addBasicCommand("show", PartShowService.class);
		super.addBasicCommand("create", PartCreateService.class);
		super.addBasicCommand("update", PartUpdateService.class);
	}

}
