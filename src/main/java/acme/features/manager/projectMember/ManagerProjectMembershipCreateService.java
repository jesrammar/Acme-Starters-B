package acme.features.manager.projectMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.UserAccount;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectMembershipCreateService extends AbstractService<Manager, UserAccount> {

	@Autowired
	private ManagerProjectMembershipRepository repository;

	private Project project;
	private UserAccount member;

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		final int userAccountId = super.getRequest().getPrincipal().getAccountId();

		this.project = this.repository.findProjectByIdAndManagerUserAccountId(projectId, userAccountId);
		this.member = this.newObject(UserAccount.class);
		this.member.setUsername("");
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.project != null && Boolean.TRUE.equals(this.project.getDraftMode()));
	}

	@Override
	public void bind() {
		super.bindObject(this.member, "username");
	}

	@Override
	public void validate() {
		final UserAccount persistedMember = this.repository.findUserAccountByUsername(this.member.getUsername());
		super.state(persistedMember != null, "username", "acme.validation.project.member-unknown.message");

		if (persistedMember != null) {
			final boolean hasEligibleRole = this.repository.hasSpokespersonRole(persistedMember.getId()) || this.repository.hasInventorRole(persistedMember.getId()) || this.repository.hasFundraiserRole(persistedMember.getId());
			super.state(hasEligibleRole, "username", "acme.validation.project.member-invalid-role.message");
			super.state(!this.project.getMembers().contains(persistedMember), "username", "acme.validation.project.member-already-linked.message");
			this.member = persistedMember;
		}
	}

	@Override
	public void execute() {
		this.project.getMembers().add(this.member);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.member, "username");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("projectTitle", this.project.getTitle());
	}

}
