package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectListService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void load() {
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findProjectsByManagerUserAccountId(userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		for (final Project project : this.projects) {
			final Tuple tuple = super.unbindObject(project, "title", "kickOffMoment", "closeOutMoment", "draftMode");
			tuple.put("draftModeVisual", Boolean.TRUE.equals(project.getDraftMode()) ? "✔" : "✖");
		}
	}

}
