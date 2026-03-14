
package acme.features.any.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	// Internal state

	@Autowired
	private AnyPartRepository	repository;

	private Collection<Part>	parts;

	// AbstractService interface


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.parts = this.repository.findPublishedPartsByInventionId(inventionId);
	}

	@Override
	public void authorise() {
		// TODO
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

}
