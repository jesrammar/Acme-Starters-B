
package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	@Autowired
	private AnyFundraiserRepository	repository;

	private Fundraiser				fundraiser;


	@Override
	public void authorise() {
		super.setAuthorised(this.fundraiser != null);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		this.fundraiser = this.repository.findFundraiserById(id);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.fundraiser, "bank", "statement", "agent");
	}
}
