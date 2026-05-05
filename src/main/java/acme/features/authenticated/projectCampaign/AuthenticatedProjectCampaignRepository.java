package acme.features.authenticated.projectCampaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Repository
public interface AuthenticatedProjectCampaignRepository extends AbstractRepository {

	@Query("select distinct p from Project p left join fetch p.campaigns join p.members m where p.id = :projectId and m.id = :userAccountId")
	Project findProjectByIdAndMemberUserAccountId(int projectId, int userAccountId);

	@Query("select c from Project p join p.campaigns c where p.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select c from Project p join p.campaigns c where p.id = :projectId and c.id = :campaignId")
	Campaign findCampaignByProjectIdAndCampaignId(int projectId, int campaignId);

}
