
package acme.features.inventions;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventionListService extends AbstractService<Inventor, Invention> {

	// Internal state

	@Autowired
	private InventionRepository		repository;

	private Collection<Invention>	inventions;

	// AbstractService interface


	@Override
	public void load() {
		int principalId;

		principalId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.inventions = this.repository.findInventionsByInventorId(principalId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
