
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class PartUpdateService extends AbstractService<Inventor, Part> {

	// Internal state

	@Autowired
	private PartRepository	repository;

	private Part			part;

	// AbstractService interface


	@Override
	public void load() {
		int partId;

		partId = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(partId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null && (this.part.getInvention().getDraftMode() || this.part.getInvention().getInventor().isPrincipal());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.part);
	}

	@Override
	public void execute() {
		this.repository.save(this.part);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
	}

}
