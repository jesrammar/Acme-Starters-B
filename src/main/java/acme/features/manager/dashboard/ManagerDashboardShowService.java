package acme.features.manager.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.helpers.MathHelper;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerDashboardShowService extends AbstractService<Manager, Manager> {

	@Autowired
	private ManagerDashboardRepository repository;

	private Manager manager;
	private Collection<Project> ownProjects;
	private Collection<Project> othersProjects;

	@Override
	public void load() {
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.manager = this.repository.findManagerByUserAccountId(userAccountId);
		this.ownProjects = this.manager == null ? java.util.List.of() : this.repository.findProjectsByManagerUserAccountId(userAccountId);
		this.othersProjects = this.manager == null ? java.util.List.of() : this.repository.findProjectsManagedByOthers(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.manager != null);
	}

	@Override
	public void unbind() {
		final List<Double> ownEfforts = this.computeEfforts(this.ownProjects);
		final double ownProjectsCount = this.ownProjects.size();
		final double othersAverageProjects = this.computeAverageProjectCount(this.othersProjects);

		final Tuple tuple = super.unbindObject(this.manager, "position", "skills", "executive");
		tuple.put("totalProjects", (int) ownProjectsCount);
		tuple.put("projectsDeviation", MathHelper.roundOff(ownProjectsCount - othersAverageProjects, 2));
		tuple.put("effortMinimum", this.computeMinimum(ownEfforts));
		tuple.put("effortMaximum", this.computeMaximum(ownEfforts));
		tuple.put("effortAverage", this.computeAverage(ownEfforts));
		tuple.put("effortDeviation", this.computeDeviation(ownEfforts));
	}

	private List<Double> computeEfforts(final Collection<Project> projects) {
		final List<Double> result = new ArrayList<>();

		for (final Project project : projects) {
			final int people = Math.max(project.getMembers().size(), 1);
			double activeMonths = 0.0;

			for (final Campaign campaign : project.getCampaigns())
				activeMonths += campaign.getMonthsActive();

			result.add(MathHelper.roundOff(activeMonths / people, 2));
		}

		return result;
	}

	private double computeAverageProjectCount(final Collection<Project> projects) {
		if (projects.isEmpty())
			return 0.0;

		final java.util.Map<Integer, Integer> counts = new java.util.HashMap<>();
		for (final Project project : projects)
			counts.merge(project.getManager().getId(), 1, Integer::sum);

		double total = 0.0;
		for (final Integer count : counts.values())
			total += count;

		return total / counts.size();
	}

	private double computeMinimum(final List<Double> values) {
		return values.isEmpty() ? 0.0 : MathHelper.roundOff(values.stream().mapToDouble(Double::doubleValue).min().orElse(0.0), 2);
	}

	private double computeMaximum(final List<Double> values) {
		return values.isEmpty() ? 0.0 : MathHelper.roundOff(values.stream().mapToDouble(Double::doubleValue).max().orElse(0.0), 2);
	}

	private double computeAverage(final List<Double> values) {
		return values.isEmpty() ? 0.0 : MathHelper.roundOff(values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0), 2);
	}

	private double computeDeviation(final List<Double> values) {
		if (values.isEmpty())
			return 0.0;

		final double average = this.computeAverage(values);
		double total = 0.0;

		for (final Double value : values)
			total += Math.pow(value.doubleValue() - average, 2.0);

		return MathHelper.roundOff(Math.sqrt(total / values.size()), 2);
	}

}
