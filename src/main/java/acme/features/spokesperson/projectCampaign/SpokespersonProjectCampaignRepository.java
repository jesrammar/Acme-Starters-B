package acme.features.spokesperson.projectCampaign;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Repository
public interface SpokespersonProjectCampaignRepository extends AbstractRepository {

	@Query("select distinct p from Project p left join fetch p.campaigns join p.members m where p.id = :projectId and m.id = :userAccountId")
	Project findProjectByIdAndMemberUserAccountId(int projectId, int userAccountId);

	@Query("select c from Campaign c where c.ticker = :ticker and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findOwnCampaignByTicker(String ticker, int userAccountId);

	@Query("select c from Campaign c where c.project.id = :projectId and c.id = :campaignId and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findOwnCampaignInProject(int projectId, int campaignId, int userAccountId);

}
