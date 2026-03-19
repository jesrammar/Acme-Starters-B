
package acme.features.inventor.invention;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Controller
public class InventionController extends AbstractController<Inventor, Invention> {

	// Constructors

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", InventionListService.class);
		super.addBasicCommand("show", InventionShowService.class);
		super.addBasicCommand("create", InventionCreateService.class);
		super.addBasicCommand("update", InventionUpdateService.class);
		super.addBasicCommand("delete", InventionDeleteService.class);
	}

}
