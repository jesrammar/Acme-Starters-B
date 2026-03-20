
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.entities.inventions.PartKind;
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

		try {
			partId = super.getRequest().getData("id", int.class);
			this.part = this.repository.findPartById(partId);
		} catch (Throwable oops) {
			this.part = null;
		}

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null && this.part.getInvention().getDraftMode() && this.part.getInvention().getInventor().isPrincipal();

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
		Tuple unbindedPart;
		SelectChoices kindChoices;

		unbindedPart = super.unbindObject(this.part, "name", "description", "cost", "kind");
		kindChoices = SelectChoices.from(PartKind.class, this.part.getKind());

		unbindedPart.put("inventionId", this.part.getInvention().getId());
		unbindedPart.put("inventionDraftMode", this.part.getInvention().getDraftMode());
		unbindedPart.put("kindChoices", kindChoices);
	}

}
