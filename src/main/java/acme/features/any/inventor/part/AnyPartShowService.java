
package acme.features.any.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;

@Service
public class AnyPartShowService extends AbstractService<Any, Part> {

	// Internal state

	@Autowired
	private AnyPartRepository	repository;

	private Part				part;

	// AbstractService interface


	@Override
	public void load() {
		int partId;

		partId = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPublishedPartById(partId);
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
