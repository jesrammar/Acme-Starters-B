
package acme.features.any.inventor.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	// Internal state

	@Autowired
	private AnyInventionRepository	repository;

	private Collection<Invention>	inventions;

	// AbstractService interface


	@Override
	public void load() {
		this.inventions = this.repository.findPublishedInventions();
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (Invention invention : this.inventions) {
			Tuple unbindedInvention;

			unbindedInvention = super.unbindObject(invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

			unbindedInvention.put("monthsActive", invention.getMonthsActive());
			unbindedInvention.put("cost", invention.getCost());

			long partsCount = this.repository.countPartsByInventionId(invention.getId());
			unbindedInvention.put("partsCount", partsCount);
		}
	}

}
