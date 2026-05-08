package acme.features.manager.projectMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectMembershipDeleteService extends AbstractService<Manager, UserAccount> {

	@Autowired
	private ManagerProjectMembershipRepository repository;

	private Project project;
	private UserAccount member;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int memberId = super.getRequest().getData("memberId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndManagerUserAccountId(projectId, userAccountId);
		this.member = this.project == null ? null : this.repository.findMemberByProjectIdAndMemberId(projectId, memberId);
	}

	@Override
	public void authorise() {
		final boolean removingManager = this.project != null && this.member != null && this.member.getId() == this.project.getManager().getUserAccount().getId();
		super.setAuthorised(this.project != null && this.member != null && Boolean.TRUE.equals(this.project.getDraftMode()) && !removingManager);
	}

	@Override
	public void execute() {
		this.project.getMembers().remove(this.member);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.member, "username");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectTitle", this.project.getTitle());
	}

}
