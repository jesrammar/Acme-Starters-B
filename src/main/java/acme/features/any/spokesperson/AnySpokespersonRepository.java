package acme.features.any.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;

@Repository
public interface AnySpokespersonRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.id = :id and c.draftMode = false")
	Campaign findPublishedCampaignById(int id);
}
