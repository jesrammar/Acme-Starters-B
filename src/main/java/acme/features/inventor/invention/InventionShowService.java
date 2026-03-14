
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventionShowService extends AbstractService<Inventor, Invention> {

	// Internal state

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;

	// AbstractService interface


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(inventionId);
	}

	@Override
	public void authorise() {
		// TODO
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
