
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
public class PartDeleteService extends AbstractService<Inventor, Part> {

	@Autowired
	private PartRepository	repository;

	private Part			part;


	@Override
	public void load() {
		int partId;

		partId = super.getRequest().getData("id", int.class);

		this.part = this.repository.findPartById(partId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.part != null && this.part.getInvention().getInventor().isPrincipal() && this.part.getInvention().getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.part, "name", "description", "cost", "kind");
	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.delete(this.part);
	}

	@Override
	public void unbind() {
		Tuple unbindedPart;

		unbindedPart = super.unbindObject(this.part, "name", "description", "cost", "kind");

		unbindedPart.put("partId", this.part.getInvention().getId());
		unbindedPart.put("inventionDraftMode", this.part.getInvention().getDraftMode());
		SelectChoices kindChoices = SelectChoices.from(PartKind.class, this.part.getKind());
		unbindedPart.put("kindChoices", kindChoices);
	}

}
