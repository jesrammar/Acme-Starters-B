package acme.features.manager.project;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.UserAccount;
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

		this.project = new Project();
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
		super.bindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "memberUsernames");
	}

	@Override
	public void validate() {
		super.validateObject(this.project);
		this.validateMembers();
		this.validateMoments();
	}

	@Override
	public void execute() {
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keywords", "description", "kickOffMoment", "closeOutMoment", "draftMode", "memberUsernames");
	}

	private void validateMembers() {
		final Set<String> usernames = ManagerProjectHelper.parseUsernames(this.project.getMemberUsernames());
		final Set<UserAccount> members = new LinkedHashSet<>();
		final UserAccount managerAccount = this.project.getManager().getUserAccount();

		members.add(managerAccount);
		for (final String username : usernames) {
			final UserAccount userAccount = this.repository.findUserAccountByUsername(username);
			super.state(userAccount != null, "memberUsernames", "acme.validation.project.member-unknown.message");
			if (userAccount != null) {
				final boolean hasEligibleRole = this.repository.hasSpokespersonRole(userAccount.getId()) || this.repository.hasInventorRole(userAccount.getId()) || this.repository.hasFundraiserRole(userAccount.getId()) || this.repository.hasManagerRole(userAccount.getId());
				super.state(hasEligibleRole, "memberUsernames", "acme.validation.project.member-invalid-role.message");
			}
			if (userAccount != null)
				members.add(userAccount);
		}

		this.project.setMembers(members);
		this.project.setMemberUsernames(ManagerProjectHelper.formatMemberUsernames(this.project));
	}

	private void validateMoments() {
		if (this.project.getKickOffMoment() != null && this.project.getCloseOutMoment() != null)
			super.state(this.project.getKickOffMoment().before(this.project.getCloseOutMoment()), "kickOffMoment", "acme.validation.project.invalid-interval.message");
	}

}
