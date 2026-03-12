
package acme.features.sponsorships.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface DonationRepository extends AbstractRepository {

	@Query("select i from Donation i where i.id = :id")
	Donation findDonationById(int id);

	@Query("select i from Donation i where i.sponsorship.id = :sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("select i from Sponsorship i where i.id = :id")
	Sponsorship findSponsorshipById(int id);
}
