
package acme.features.inventor.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.entities.inventions.PartKind;
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
		boolean status;

		status = this.part != null;

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");

		super.unbindGlobal("inventionDraftMode", this.part.getInvention().getDraftMode());
		super.unbindGlobal("inventionId", this.part.getInvention().getId());

		SelectChoices kindChoices = SelectChoices.from(PartKind.class, this.part.getKind());
		super.unbindGlobal("kindChoices", kindChoices);
	}

}
