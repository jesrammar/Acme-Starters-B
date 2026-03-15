
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
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
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple unbindedObject;

		unbindedObject = super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

		unbindedObject.put("monthsActive", this.invention.getMonthsActive());
		unbindedObject.put("cost", this.invention.getCost());

	}

}
