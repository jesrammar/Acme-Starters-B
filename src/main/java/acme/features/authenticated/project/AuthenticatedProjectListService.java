package acme.features.authenticated.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Authenticated;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;

@Service
public class AuthenticatedProjectListService extends AbstractService<Authenticated, Project> {

	@Autowired
	private AuthenticatedProjectRepository repository;

	private Collection<Project> projects;

	@Override
	public void load() {
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.projects = this.repository.findProjectsByMemberUserAccountId(userAccountId);
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
