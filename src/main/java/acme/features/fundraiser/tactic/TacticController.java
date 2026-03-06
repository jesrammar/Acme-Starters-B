
package acme.features.fundraiser.tactic;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Tactic;
import acme.realms.Fundraiser;

@Controller
public class TacticController extends AbstractController<Fundraiser, Tactic> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", TacticListService.class);
		super.addBasicCommand("show", TacticShowService.class);

	}
}
