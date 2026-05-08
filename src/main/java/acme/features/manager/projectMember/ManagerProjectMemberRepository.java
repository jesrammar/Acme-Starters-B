package acme.features.manager.projectMember;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface ManagerProjectMemberRepository extends AbstractRepository {

	@Query("select distinct p from Project p left join fetch p.members where p.id = :projectId and p.manager.userAccount.id = :userAccountId")
	Project findProjectByIdAndManagerUserAccountId(int projectId, int userAccountId);

	@Query("select m from Project p join p.members m where p.id = :projectId order by m.username")
	Collection<UserAccount> findMembersByProjectId(int projectId);

	@Query("select m from Project p join p.members m where p.id = :projectId and m.id = :memberId")
	UserAccount findMemberByProjectIdAndMemberId(int projectId, int memberId);

}
