
package acme.features.inventor.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Inventor;

@Service
public class InventionPublishService extends AbstractService<Inventor, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventionRepository	repository;

	private Invention			invention;

	// AbstractService interface ----------------------------------------------


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
		super.validateObject(this.invention);

		if (!super.getErrors().hasErrors("*")) {
			Invention existingTicker = this.repository.findInventionByTicker(this.invention.getTicker());
			boolean uniqueTicker = existingTicker == null || existingTicker.getId() == this.invention.getId();
			super.state(uniqueTicker, "ticker", "acme.validation.invention.duplicated-ticker.message");

			boolean hasParts = this.repository.countPartsByInventionId(this.invention.getId()) > 0;
			super.state(hasParts, "*", "acme.validation.invention.parts.message");

			if (this.invention.getStartMoment() != null && this.invention.getEndMoment() != null) {

				boolean startMomentIsFuture = MomentHelper.isFuture(this.invention.getStartMoment());
				super.state(startMomentIsFuture, "startMoment", "acme.validation.invention.startMoment-NotFuture.message");

				boolean endMomentIsFuture = MomentHelper.isFuture(this.invention.getEndMoment());
				super.state(endMomentIsFuture, "endMoment", "acme.validation.invention.endMoment-NotFuture.message");

				boolean validMomentInterval = MomentHelper.isBefore(this.invention.getStartMoment(), this.invention.getEndMoment());
				super.state(validMomentInterval, "startMoment", "acme.validation.invention.invalidMomentInterval.message");

			}

		}

	}

	@Override
	public void execute() {
		this.invention.setDraftMode(false);
		this.repository.save(this.invention);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
