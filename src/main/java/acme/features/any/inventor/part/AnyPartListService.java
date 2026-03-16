
package acme.features.any.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Service
public class AnyPartListService extends AbstractService<Any, Part> {

	// Internal state

	@Autowired
	private AnyPartRepository	repository;

	private Collection<Part>	parts;
	private Invention			invention;

	// AbstractService interface


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);
		this.parts = this.repository.findPublishedPartsByInventionId(inventionId);
		this.invention = this.repository.findPublishedInventionById(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");
	}

}
