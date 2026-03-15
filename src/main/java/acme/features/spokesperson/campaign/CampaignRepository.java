/*
 * CampaignRepository.java
 *
 * Copyright (C) 2012-2026 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.spokesperson.campaign;

import java.util.Collection;
import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.realms.Spokesperson;

@Repository
public interface CampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.draftMode = false")
	Collection<Campaign> findPublishedCampaigns();

	@Query("select c from Campaign c where c.id = :id and c.draftMode = false")
	Campaign findPublishedCampaignById(int id);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.spokesperson.userAccount.id = :userAccountId")
	Collection<Campaign> findCampaignsBySpokespersonUserAccountId(int userAccountId);

	@Query("select s from Spokesperson s where s.userAccount.id = :userAccountId")
	Spokesperson findSpokespersonByUserAccountId(int userAccountId);

	@Query("select c from Campaign c where c.ticker = :ticker")
	Campaign findCampaignByTicker(String ticker);

	@Query("select count(m) from Milestone m where m.campaign.id = :campaignId")
	Long countMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> findMilestonesByCampaignId(int campaignId);

	@Query("select sum(m.effort) from Milestone m where m.campaign.id = :campaignId")
	Double computeCampaignEffort(int campaignId);
}

