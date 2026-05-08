package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m where m.userAccount.id = :userAccountId and m.id = (select min(m2.id) from Manager m2 where m2.userAccount.id = :userAccountId)")
	Manager findManagerByUserAccountId(int userAccountId);

	@Query("select distinct p from Project p left join fetch p.members left join fetch p.campaigns where p.manager.userAccount.id = :userAccountId")
	Collection<Project> findProjectsByManagerUserAccountId(int userAccountId);

	@Query("select distinct p from Project p left join fetch p.members left join fetch p.campaigns where p.manager.userAccount.id <> :userAccountId")
	Collection<Project> findProjectsManagedByOthers(int userAccountId);

}
