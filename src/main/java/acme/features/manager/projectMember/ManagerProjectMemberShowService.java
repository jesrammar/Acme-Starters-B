package acme.features.manager.projectMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectMemberShowService extends AbstractService<Manager, UserAccount> {

	@Autowired
	private ManagerProjectMemberRepository repository;

	private Project project;
	private UserAccount member;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int id = super.getRequest().getData("id", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndManagerUserAccountId(projectId, userAccountId);
		this.member = this.project == null ? null : this.repository.findMemberByProjectIdAndMemberId(projectId, id);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && this.member != null);
	}

	@Override
	public void unbind() {
		final Tuple tuple = super.unbindObject(this.member, "username");
		tuple.put("name", this.member.getIdentity().getName());
		tuple.put("surname", this.member.getIdentity().getSurname());
		tuple.put("email", this.member.getIdentity().getEmail());
		tuple.put("isManagerAccount", this.member.getId() == this.project.getManager().getUserAccount().getId());

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectTitle", this.project.getTitle());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
	}

}
