package acme.features.manager.projectMember;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ManagerProjectMembershipRepository extends AbstractRepository {

	@Query("select distinct p from Project p left join fetch p.members where p.id = :projectId and p.manager.userAccount.id = :userAccountId")
	Project findProjectByIdAndManagerUserAccountId(int projectId, int userAccountId);

	@Query("select m from Project p join p.members m where p.id = :projectId and m.id = :memberId")
	UserAccount findMemberByProjectIdAndMemberId(int projectId, int memberId);

	@Query("select ua from UserAccount ua where lower(ua.username) = lower(:username)")
	UserAccount findUserAccountByUsername(String username);

	@Query("select count(s) > 0 from Spokesperson s where s.userAccount.id = :userAccountId")
	boolean hasSpokespersonRole(int userAccountId);

	@Query("select count(i) > 0 from Inventor i where i.userAccount.id = :userAccountId")
	boolean hasInventorRole(int userAccountId);

	@Query("select count(f) > 0 from Fundraiser f where f.userAccount.id = :userAccountId")
	boolean hasFundraiserRole(int userAccountId);

}
