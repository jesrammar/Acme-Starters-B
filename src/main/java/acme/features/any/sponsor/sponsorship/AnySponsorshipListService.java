
package acme.features.any.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.features.any.project.AnyProjectRepository;
import acme.realms.Sponsor;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	@Autowired
	private AnySponsorshipRepository	repository;

	private Iterable<Sponsorship>		sponsorships;

	private Project						project;

	@Autowired
	private AnyProjectRepository		projectRepository;


	@Override
	public void authorise() {
		boolean status = true;
		if (super.getRequest().hasData("projectId") && this.project == null)
			status = false;
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		Integer projectId = super.getRequest().getData("projectId", Integer.class);
		this.sponsorships = this.repository.findPublishedSponsorships();
		this.project = this.projectRepository.findProjectById(projectId);
	}

	@Override
	public void unbind() {

		for (Sponsorship sponsorship : this.sponsorships) {

			Tuple tuple;

			tuple = super.unbindObject(sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");

			tuple.put("money", sponsorship.getTotalMoney());

			tuple.put("activeMonths", sponsorship.getMonthsActive());

			long donationCount = this.repository.countDonationsBySponsorshipId(sponsorship.getId());

			tuple.put("donationCount", donationCount);
		}

		boolean isSponsor = super.getRequest().getPrincipal().getRealms().stream().anyMatch(Sponsor.class::isInstance);
		if (super.getRequest().hasData("projectId")) {
			super.unbindGlobal("isSponsor", isSponsor);
			super.unbindGlobal("projectId", this.project.getId());
		}
	}

}
