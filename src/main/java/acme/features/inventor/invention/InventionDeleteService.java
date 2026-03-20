
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.features.inventor.part.PartRepository;
import acme.realms.Inventor;

@Service
public class InventionDeleteService extends AbstractService<Inventor, Invention> {

	@Autowired
	private InventionRepository	repository;

	@Autowired
	private PartRepository		partRepository;

	private Invention			invention;


	@Override
	public void load() {
		int inventionId;

		inventionId = super.getRequest().getData("id", int.class);

		this.invention = this.repository.findInventionById(inventionId);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.invention != null && this.invention.getInventor().isPrincipal() && this.invention.getDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

	}

	@Override
	public void validate() {
		;
	}

	@Override
	public void execute() {
		this.repository.deleteAll(this.partRepository.findPartsByInventionId(this.invention.getId()));
		this.repository.delete(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
