package acme.features.manager.projectMember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectMemberListService extends AbstractService<Manager, UserAccount> {

	@Autowired
	private ManagerProjectMemberRepository repository;

	private Project project;
	private Collection<UserAccount> members;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndManagerUserAccountId(projectId, userAccountId);
		this.members = this.project == null ? java.util.List.of() : this.repository.findMembersByProjectId(projectId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null);
	}

	@Override
	public void unbind() {
		for (final UserAccount member : this.members) {
			final Tuple tuple = super.unbindObject(member, "username");
			tuple.put("name", member.getIdentity().getName());
			tuple.put("surname", member.getIdentity().getSurname());
			tuple.put("email", member.getIdentity().getEmail());
			tuple.put("projectId", this.project.getId());
		}

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectTitle", this.project.getTitle());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}
