package acme.features.authenticated.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

@Repository
public interface AuthenticatedProjectRepository extends AbstractRepository {

	@Query("select distinct p from Project p join p.members m where m.id = :userAccountId")
	Collection<Project> findProjectsByMemberUserAccountId(int userAccountId);

	@Query("select distinct p from Project p left join fetch p.members left join fetch p.campaigns join p.members m where p.id = :id and m.id = :userAccountId")
	Project findProjectByIdAndMemberUserAccountId(int id, int userAccountId);

}
