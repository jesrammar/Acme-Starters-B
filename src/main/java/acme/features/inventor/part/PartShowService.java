
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class PartShowService extends AbstractService<Inventor, Part> {

	// Internal state

	@Autowired
	private PartRepository	repository;

	private Part			part;

	// AbstractService interface


	@Override
	public void load() {
		int partId, inventorId;

		partId = super.getRequest().getData("id", int.class);
		inventorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.part = this.repository.findPartByIdAndInventorId(partId, inventorId);
	}

	@Override
	public void authorise() {
		// TODO
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
	}

}
