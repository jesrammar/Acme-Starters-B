
package acme.features.any.sponsor.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface AnyDonationRepository extends AbstractRepository {

	@Query("select i from Donation i where i.sponsorship.id = :sponsorshipId and i.sponsorship.draftMode = false")
	Collection<Donation> findPublishedDonationsBySponsorshipId(int sponsorshipId);

	@Query("select i from Donation i where i.id = :id and i.sponsorship.draftMode = false")
	Donation findPublishedDonationById(int id);

	@Query("select i from Sponsorship i where i.id = :id and i.draftMode = false")
	Sponsorship findPublishedSponsorshipById(int id);
}
