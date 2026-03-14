
package acme.features.inventor.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Part;
import acme.realms.Inventor;

@Service
public class PartListService extends AbstractService<Inventor, Part> {

	// Internal state

	@Autowired
	private PartRepository		repository;

	private Collection<Part>	parts;

	// AbstractService interface


	@Override
	public void load() {
		int inventionId, principalId;

		principalId = super.getRequest().getPrincipal().getActiveRealm().getId();
		inventionId = super.getRequest().getData("inventionId", int.class);

		this.parts = this.repository.findPartsByInventionIdAndInventorId(inventionId, principalId);
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
