
package acme.features.fundraiser.strategy;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.strategies.Strategy;
import acme.realms.Fundraiser;

@Controller
public class StrategyController extends AbstractController<Fundraiser, Strategy> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", StrategyListService.class);
		super.addBasicCommand("show", StrategyShowService.class);
		super.addBasicCommand("create", StrategyCreateService.class);
		super.addBasicCommand("update", StrategyUpdateService.class);
		super.addBasicCommand("delete", StrategyDeleteService.class);

	}

}
