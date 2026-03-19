
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.entities.inventions.PartKind;
import acme.realms.Inventor;

@Service
public class PartCreateService extends AbstractService<Inventor, Part> {

	@Autowired
	private PartRepository	repository;

	private Part			part;
	private Invention		invention;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("inventionId", int.class);

		this.invention = this.repository.findInventionById(inventionId);
		this.part = super.newObject(Part.class);
		this.part.setInvention(this.invention);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal() && this.invention.getDraftMode();

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
		SelectChoices kindChoices;
		kindChoices = SelectChoices.from(PartKind.class, this.part.getKind());

		Tuple unbindedPart = super.unbindObject(this.part, "name", "description", "cost", "kind");
		unbindedPart.put("inventionId", this.part.getInvention().getId());
		unbindedPart.put("inventionDraftMode", this.part.getInvention().getDraftMode());
		unbindedPart.put("kindChoices", kindChoices);

	}

}
