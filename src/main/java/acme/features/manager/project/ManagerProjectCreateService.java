package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository repository;

	private Project project;

	@Override
	public void load() {
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();
		final Manager manager = this.repository.findManagerByUserAccountId(userAccountId);

		this.project = this.newObject(Project.class);
		this.project.setManager(manager);
		this.project.setDraftMode(true);
		if (manager != null) {
			this.project.getMembers().add(manager.getUserAccount());
			this.project.setMemberUsernames(manager.getUserAccount().getUsername());
		} else
			this.project.setMemberUsernames("");
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && this.project.getManager() != null);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode");
	}

}
