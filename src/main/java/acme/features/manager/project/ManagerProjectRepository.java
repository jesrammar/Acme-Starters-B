package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select m from Manager m where m.userAccount.id = :userAccountId")
	Manager findManagerByUserAccountId(int userAccountId);

	@Query("select p from Project p where p.manager.userAccount.id = :userAccountId")
	Collection<Project> findProjectsByManagerUserAccountId(int userAccountId);

	@Query("select distinct p from Project p left join fetch p.members left join fetch p.campaigns where p.id = :id and p.manager.userAccount.id = :userAccountId")
	Project findProjectByIdAndManagerUserAccountId(int id, int userAccountId);

	@Query("select ua from UserAccount ua where lower(ua.username) = lower(:username)")
	UserAccount findUserAccountByUsername(String username);

	@Query("select count(s) > 0 from Spokesperson s where s.userAccount.id = :userAccountId")
	boolean hasSpokespersonRole(int userAccountId);

	@Query("select count(i) > 0 from Inventor i where i.userAccount.id = :userAccountId")
	boolean hasInventorRole(int userAccountId);

	@Query("select count(f) > 0 from Fundraiser f where f.userAccount.id = :userAccountId")
	boolean hasFundraiserRole(int userAccountId);

	@Query("select count(m) > 0 from Manager m where m.userAccount.id = :userAccountId")
	boolean hasManagerRole(int userAccountId);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Long countMilestonesByCampaignId(int campaignId);

	@Query("select c from Project p join p.campaigns c where p.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

}
