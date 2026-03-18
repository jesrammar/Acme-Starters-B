package acme.features.spokesperson.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;

@Repository
public interface MilestoneRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.id = :id and c.spokesperson.userAccount.id = :userAccountId")
	Campaign findCampaignByIdAndSpokespersonUserAccountId(int id, int userAccountId);

	@Query("select m from Milestone m where m.id = :id")
	Milestone findMilestoneById(int id);

	@Query("select m from Milestone m where m.id = :id and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Milestone findMilestoneByIdAndSpokespersonUserAccountId(int id, int userAccountId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId and m.campaign.spokesperson.userAccount.id = :userAccountId")
	Collection<Milestone> findMilestonesByCampaignIdAndSpokespersonUserAccountId(int campaignId, int userAccountId);
}


