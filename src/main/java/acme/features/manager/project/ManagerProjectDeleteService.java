package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Project project;

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.project = this.repository.findProjectByIdAndManagerUserAccountId(id, userAccountId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && Boolean.TRUE.equals(this.project.getDraftMode()));
	}

	@Override
	public void bind() {
	}

	@Override
	public void validate() {
	}

	@Override
	public void execute() {
		this.repository.delete(this.project);
	}

	@Override
	public void unbind() {
		this.project.setMemberUsernames(ManagerProjectHelper.formatMemberUsernames(this.project));
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "memberUsernames");
	}

}
