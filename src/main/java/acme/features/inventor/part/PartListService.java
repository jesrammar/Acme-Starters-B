
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class PartListService extends AbstractService<Inventor, Part> {

	// Internal state

	@Autowired
	private PartRepository		repository;

	private Collection<Part>	parts;
	private Invention			invention;

	// AbstractService interface


	@Override
	public void load() {
		int inventionId;

		try {
			inventionId = super.getRequest().getData("inventionId", int.class);

			this.parts = this.repository.findPartsByInventionId(inventionId);
			this.invention = this.repository.findInventionById(inventionId);
		} catch (final Throwable oops) {
			this.invention = null;
			this.parts = null;
		}

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.parts, "name", "description", "cost", "kind");

		super.unbindGlobal("inventionId", super.getRequest().getData("inventionId", int.class));
		super.unbindGlobal("inventionDraftMode", this.invention.getDraftMode());
	}

}
