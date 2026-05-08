package acme.features.authenticated.projectMilestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.projects.Project;

@Repository
public interface AuthenticatedProjectMilestoneRepository extends AbstractRepository {

	@Query("select distinct p from Project p join p.members m where p.id = :projectId and m.id = :userAccountId")
	Project findProjectByIdAndMemberUserAccountId(int projectId, int userAccountId);

	@Query("select c from Campaign c where c.project.id = :projectId and c.id = :campaignId")
	Campaign findCampaignByProjectIdAndCampaignId(int projectId, int campaignId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :id and m.campaign.id = :campaignId")
	Milestone findMilestoneByIdAndCampaignId(int id, int campaignId);

}
