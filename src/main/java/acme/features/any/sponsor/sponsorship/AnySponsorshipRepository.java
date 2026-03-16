
package acme.features.any.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select i from Sponsorship i where i.draftMode = false")
	Collection<Sponsorship> findPublishedSponsorships();

	@Query("select i from Sponsorship i where i.id = :id and i.draftMode = false")
	Sponsorship findPublishedSponsorshipById(int id);

	@Query("select count(d) from Donation d where d.sponsorship.id = :sponsorshipId")
	long countDonationsBySponsorshipId(int sponsorshipId);

	@Query("select sum(d.money.amount) from Donation d where d.sponsorship.id = :sponsorshipId")
	Double sumTotalMoneyBySponsorshipId(int sponsorshipId);
}
